package com.ec.service.template;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ec.dao.specification.SpecificationOptionDao;
import com.ec.dao.template.TypeTemplateDao;
import com.ec.pojo.page.PageResult;
import com.ec.pojo.specification.SpecificationOption;
import com.ec.pojo.specification.SpecificationOptionQuery;
import com.ec.pojo.template.TypeTemplate;
import com.ec.pojo.template.TypeTemplateQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TypeTempServiceImpl implements ITypeTempService {


    @Resource
    TypeTemplateDao typeTemplateDao;

    @Resource
    SpecificationOptionDao specificationOptionDao;

    @Resource
    RedisTemplate redisTemplate;


    /**
     * 查询所有
     * @return
     */
    @Override
    public List<TypeTemplate> findAll() {
        return typeTemplateDao.selectByExample(null);
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @Override
    public PageResult<TypeTemplate> findPage(int page, int rows) {
      return search(page,rows,null);
    }

    /**
     * 查询实体
     * @param id
     * @return
     */
    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateDao.selectByPrimaryKey(id);
    }

    /**
     * 添加信息
     * @param typeTemplate
     */
    @Transactional
    @Override
    public void add(TypeTemplate typeTemplate) {
        typeTemplateDao.insertSelective(typeTemplate);
    }


    /**
     * 更新
     * @param typeTemplate
     */
    @Transactional
    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateDao.updateByPrimaryKeySelective(typeTemplate);
    }

    /**
     * 删除
     * @param ids
     */
    @Transactional
    @Override
    public void delete(Long[] ids) {
        if (ids != null && ids.length != 0 ) {
            typeTemplateDao.deleteByPrimaryKeys(ids);
        }
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @Override
    public PageResult<TypeTemplate> search(int page, int rows, TypeTemplate typeTemplate) {

        dataToRedis();
        /*  步骤：
        1、设置分页信息
        2、创建查询的类
        3、创建查询条件对象
        4、设置倒序查询
        5、设置按名称模糊查询
        6、查询所有结果
        7、将页面需要的结果剔出来
        8、返回
        */
        PageHelper.startPage(page,rows);
        TypeTemplateQuery typeTemplateQuery = new TypeTemplateQuery();
        TypeTemplateQuery.Criteria criteria = typeTemplateQuery.createCriteria();
        typeTemplateQuery.setOrderByClause("id desc");
        if(typeTemplate.getName() != null && !"".equals(typeTemplate.getName().trim())){
            criteria.andNameLike("%" + typeTemplate.getName() +"%");
        }
        Page<TypeTemplate> p = (Page) typeTemplateDao.selectByExample(typeTemplateQuery);
        PageResult<TypeTemplate> pageResult = new PageResult<>(p.getTotal(),p.getResult());
        return pageResult;
    }

    // 将模板的数据写到缓存中
    public void dataToRedis(){
        List<TypeTemplate> typeTemplateList = typeTemplateDao.selectByExample(null);
        if(typeTemplateList != null && typeTemplateList.size() > 0){
            for (TypeTemplate template : typeTemplateList) {
                // 例如：[{"id":60,"text":"奔驰"},{"id":62,"text":"奥拓"}]
                String brandIds = template.getBrandIds();
                List<Map> brandList = JSON.parseArray(brandIds, Map.class);
                // 品牌结果集写入缓存
                redisTemplate.boundHashOps("brandList").put(template.getId(), brandList);
                // 规格结果集写入缓存（规格与规格选项）
                List<Map> specList = findBySpecList(template.getId());
                redisTemplate.boundHashOps("specList").put(template.getId(), specList);
            }
        }

    }



    /**
     * 查询模板类型及规格项[{text:规格名,options:{对象1,对象2,对象3}]
     * @param id
     * @return
     */
    @Override
    public List<Map> findBySpecList(Long id) {
        /**
         * 查询模板类型，可得到此模板有哪些规格
         * 通过模板的规格可查出规格包括哪些规格项
         * 页面最终要用到的数据类型是：[{text:规格名,options:{对象1,对象2,对象3}]
         */

        //查询模板类型，获取模板规格
        TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
        String specIds = typeTemplate.getSpecIds();//[{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        List<Map> maps = JSON.parseArray(specIds, Map.class);//将json数据装华为指定对象，更好遍历
        if (maps != null && maps.size() != 0) {
            //通过模板的规格查询出规格项，并设置到要返回的list集合
            for(Map map : maps){
//                id = (Long)map.get("id");//获取到的是一个对象，是一个地址
                id = Long.parseLong(map.get("id").toString());
                SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
                specificationOptionQuery.createCriteria().andSpecIdEqualTo(id);
                List<SpecificationOption> options = specificationOptionDao.selectByExample(specificationOptionQuery);
//                [{"id":27,"text":"网络","options":[{optionName:xxx}]},{}]
                map.put("options",options);
            }
        }
        return maps;
    }
}
