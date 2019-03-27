package com.ec.service.item;


import com.ec.pojo.item.ItemCat;
import com.ec.pojo.page.PageResult;

import java.util.List;

public interface IItemCatService {

    //todo 查询所有
    List findAll();

    //todo 分页查询
    PageResult findPage(int page, int rows);

    //todo 查询实体
    ItemCat findOne(Long id);

    //todo 新增
    void add(ItemCat itemCat);

    //todo 修改
    void update(ItemCat itemCat);

    //todo 删除
    void delete(Long[] ids);

    //todo 搜索
    PageResult search(int page, int rows, ItemCat itemCat);

    //todo 分类查询
    List findByParentId(Long parentId);



}
