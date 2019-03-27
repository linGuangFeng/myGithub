package com.ec.service.item;

import com.alibaba.dubbo.config.annotation.Service;
import com.ec.dao.item.ItemCatDao;
import com.ec.pojo.item.ItemCat;
import com.ec.pojo.item.ItemCatQuery;
import com.ec.pojo.page.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ItemServiceICatmpl implements IItemCatService{

    @Resource
    ItemCatDao itemCatDao;

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public List<ItemCat> findAll() {
        return itemCatDao.selectByExample(null);
    }

    @Override
    public PageResult findPage(int page, int rows) {
        return search(page,rows,null);
    }

    @Override
    public ItemCat findOne(Long id) {
        return itemCatDao.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void add(ItemCat itemCat) {
        itemCatDao.insertSelective(itemCat);

    }

    @Transactional

    @Override
    public void update(ItemCat itemCat) {
        itemCatDao.updateByPrimaryKey(itemCat);
    }

    @Transactional
    @Override
    public void delete(Long[] ids) {

    }


    @Override
    public PageResult search(int page, int rows, ItemCat itemCat) {
        return null;
    }

    /**
     * 根据parentId查询
     * @param parentId
     * @return
     */
    @Override
    public List findByParentId(Long parentId) {

        dataToRedis();//放入缓存

//        设置查询条件
        ItemCatQuery itemCatQuery = new ItemCatQuery();
        if ( parentId != null) {
            itemCatQuery.createCriteria().andParentIdEqualTo(parentId);
        }
        //查询并返回结果
        return itemCatDao.selectByExample(itemCatQuery);
    }

    // 将分类的数据写到缓存中
    // 保存的数据：key(分类名称)-value（模板id）--- 采用的数据结构hash
    // hash:hset key filed(分类名称) value（模板id）
    public void dataToRedis(){
        List<ItemCat> itemCats = itemCatDao.selectByExample(null);
        if(itemCats != null && itemCats.size()>0) {
            for (ItemCat itemCat : itemCats) {
                redisTemplate.boundHashOps("itemCat").put(itemCat.getName(), itemCat.getTypeId());
            }
        }
    }

}
