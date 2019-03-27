package com.ec.service.item;

import com.ec.pojo.item.Item;
import com.ec.pojo.page.PageResult;

import java.util.List;

public interface IItemService {

    //todo 查询所有
    List<Item> findAll();

    //todo 分页查询
    PageResult findPage(int page, int rows);

    //todo 查询实体
    Item findOne(Long id);

    //todo 新增
    void add(Item item);

    //todo 修改
    void update(Item item);

    //todo 删除
    void delete(Long[] ids);

    //todo 搜索
    PageResult search(int page, int rows, Item item);


}
