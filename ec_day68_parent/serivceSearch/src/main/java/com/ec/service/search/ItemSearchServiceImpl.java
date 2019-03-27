package com.ec.service.search;


import com.alibaba.dubbo.config.annotation.Service;
import com.ec.pojo.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.GroupEntry;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.GroupResult;
import org.springframework.data.solr.core.query.result.ScoredPage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Resource
    SolrTemplate solrTemplate;

    @Resource
    RedisTemplate redisTemplate;

    /**
     * 根据条件分页搜索库存、品牌、分类
     * 前端需要的键分别rows，brandList，categoryList,specList
     * 还需要库存的分页信息需要：total,totalPages
     * @param searchMap
     * @return
     */
    @Override
    public Map<String, Object> search(Map<String, String> searchMap) {
        Map<String, Object> resultMap = new HashMap<>();

        //获取rows，total,totalPages
        Map<String, Object> rows = searchForHighLightPage(searchMap);

        //获取分类信息并根据分类的第一个名称获取所有的specList，brandList
        Map<String, Object> categoryList = searchCategory(searchMap);

        //将页面所有需要的数据封装至map
        resultMap.putAll(rows);
        resultMap.putAll(categoryList);

        return resultMap;
    }

    //分装库存rows,total,totalPages,关键字高亮
    public Map<String,Object> searchForHighLightPage(Map<String,String> searchMap){
        int pageSize = Integer.valueOf(searchMap.get("pageSize"));
        int pageNo = Integer.valueOf(searchMap.get("pageNO"));
        int start = (pageNo - 1) * pageSize;

        //检索条件设置
        Criteria criteria = new Criteria("item_keywords");//检索的字段
        String keywords = searchMap.get("keywords");
        if(keywords != null && keywords.length() > 0){
            criteria.is(keywords);
        }
        SimpleHighlightQuery simpleHighlightQuery = new SimpleHighlightQuery(criteria);
        simpleHighlightQuery.setOffset(start);
        simpleHighlightQuery.setRows(pageSize);


        //获取数据并封装前端需要的字段及数据
        ScoredPage<Item> scoredPage = solrTemplate.queryForPage(simpleHighlightQuery, Item.class);
        Map<String, Object> map = new HashMap();
        map.put("totalPage",scoredPage.getTotalPages());
        map.put("total",scoredPage.getSize());
        map.put("rows",scoredPage);
        return map;
    }

    //封装库存rows，total,totalPages
    public Map<String, Object> searchForPage(Map<String, String> searchMap) {
//        分页信息
        int pageSize = Integer.valueOf(searchMap.get("pageSize"));
        int pageNo = Integer.valueOf(searchMap.get("pageNo"));
        int start = (pageNo - 1) * pageSize;

//        检索条件设置
        Criteria criteria = new Criteria("item_keywords");//检索的字段
        String keywords = searchMap.get("keywords");
        if (keywords != null && keywords.trim().length() > 0){
            criteria.is(keywords);//检索字段的值（模糊）
        }
        Query query = new SimpleQuery(criteria);
        query.setRows(pageSize);//查询每页大小
        query.setOffset(start);//第几条开始查询
        ScoredPage<Item> scoredPage = solrTemplate.queryForPage(query, Item.class);

        //封装前端需要的字段及数据
        Map<String, Object> map = new HashMap();
        map.put("rows",scoredPage);
        map.put("total",scoredPage.getSize());
        map.put("totalPage",scoredPage.getTotalPages());
        return map;
    }

    //封装分类名称:categoryList
    public Map<String,Object> searchCategory(Map<String, String> searchMap){


        //需要条件对象创建
        Criteria criteria = new Criteria("item_keywords");
        GroupOptions groupOptions = new GroupOptions();

        //需要的条件设置
        String keywords = searchMap.get("keywords");
        if (keywords != null && keywords.length() > 0){
            criteria.is(keywords);
        }
        groupOptions.addGroupByField("item_category");

        //查询对象
        Query query = new SimpleQuery(criteria);
        query.setGroupOptions(groupOptions);
        GroupPage<Item> items = solrTemplate.queryForGroupPage(query, Item.class);
        GroupResult<Item> groupResult = items.getGroupResult("item_category");

        //获取分组名称
        Page<GroupEntry<Item>> categoryList = groupResult.getGroupEntries();
        List<String> list = new ArrayList();
        for(GroupEntry<Item> category : categoryList) {
            String categoryName = category.getGroupValue();
            if(categoryName != null && categoryName.trim().length() > 0){
                list.add(categoryName);
            }
        }

        if (list.size() == 0) return null;
        Map<String, Object> spec_brand_List = searchSpecBrandList(list.get(0)); //封装specList,brandList

        //返回
        Map<String, Object> map = new HashMap<>();
        map.put("categoryList",list);
        map.putAll(spec_brand_List);
        return map;
    }

    //封装specList,brandList
    public Map<String,Object> searchSpecBrandList(String categoryName){
        //根据分类的名称从缓存中取出模板id
        //根据模板id取出品牌的结果集


        Map<String,Object> map = new HashMap<>();
        // 通过分类名称获取获取模板id
        Object typeId = redisTemplate.boundHashOps("itemCatList").get(categoryName);
        // 通过模板id获取品牌结果集
        List<Map> brandList = (List<Map>) redisTemplate.boundHashOps("brandList").get(typeId);
        map.put("brandList", brandList);
        // 通过模板id获取品牌规格集
        List<Map> specList = (List<Map>) redisTemplate.boundHashOps("specList").get(typeId);
        map.put("specList", specList);
        return map;
    }



}
