package com.ec.service.item;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ec.dao.item.ItemDao;
import com.ec.pojo.item.Item;
import com.ec.pojo.item.ItemQuery;
import com.ec.pojo.page.PageResult;
import com.github.pagehelper.PageHelper;
import org.springframework.data.solr.core.SolrTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements IItemService {

    @Resource
    ItemDao itemDao;

    @Resource
    SolrTemplate solrTemplate;

    @Override
    public List<Item> findAll() {
        return itemDao.selectByExample(null);
    }

    @Override
    public PageResult findPage(int page, int rows) {
        return null;
    }

    @Override
    public Item findOne(Long id) {
        return itemDao.selectByPrimaryKey(id);
    }

    @Override
    public void add(Item item) {

    }

    @Override
    public void update(Item item) {

    }

    @Override
    public void delete(Long[] ids) {

    }

    /**
     * 根据parent_id查询
     * @param page
     * @param rows
     * @param item
     * @return
     */
    @Override
    public PageResult search(int page, int rows, Item item) {
//        分页设置
        PageHelper.startPage(page,rows);



        return null;
    }


    /**
     * 将所有以上架的库存加入索引库
     */
    public void itemImportSolr(ItemDao itemDao, SolrTemplate solrTemplate){
        ItemQuery itemQuery = new ItemQuery();
        itemQuery.createCriteria().andStatusEqualTo("1");
        List<Item> itemList = itemDao.selectByExample(itemQuery);

        if(itemList != null && itemList.size() > 0) {

            for (Item item : itemList) {
                String spec = item.getSpec();//{"机身内存":"16G","网络":"联通3G"}
                Map map = JSON.parseObject(spec, Map.class);
                item.setSpecMap(map);
            }
            solrTemplate.saveBeans(itemList);
            solrTemplate.commit();
        }
    }
}
