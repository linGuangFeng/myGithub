package com.ec.service.item;

import com.alibaba.dubbo.config.annotation.Service;
import com.ec.dao.item.ItemDao;
import com.ec.pojo.item.Item;
import com.ec.pojo.page.PageResult;
import com.github.pagehelper.PageHelper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ItemServiceImpl implements IItemService {

    @Resource
    ItemDao itemDao;

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
}
