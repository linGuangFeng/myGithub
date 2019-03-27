package com.ec.service.item;

import com.alibaba.dubbo.config.annotation.Service;
import com.ec.dao.item.ItemCatDao;
import com.ec.pojo.item.ItemCat;
import com.ec.pojo.item.ItemCatQuery;
import com.ec.pojo.page.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ItemServiceICatmpl implements IItemCatService{

    @Resource
    ItemCatDao itemCatDao;

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
//        设置查询条件
        ItemCatQuery itemCatQuery = new ItemCatQuery();
        if ( parentId != null) {
            itemCatQuery.createCriteria().andParentIdEqualTo(parentId);
        }
        //查询并返回结果
        return itemCatDao.selectByExample(itemCatQuery);
    }
}
