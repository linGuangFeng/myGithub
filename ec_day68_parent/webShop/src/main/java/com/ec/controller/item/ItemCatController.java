package com.ec.controller.item;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ec.pojo.item.ItemCat;
import com.ec.pojo.page.PageResult;
import com.ec.service.item.IItemCatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("itemCat")
@RestController
public class ItemCatController {

    @Reference
    IItemCatService iItemCatService;

    @RequestMapping("search")
    public PageResult search(int page, int rows, ItemCat itemCat) {
        return iItemCatService.search(page, rows, itemCat);
    }

    @RequestMapping("findAll")
    public List findAll() {
        return iItemCatService.findAll();
    }


    /**
     * 根据parentId查询
     *
     * @param parentId
     * @return
     */
    @RequestMapping("findByParentId")
    public List findByParentId(Long parentId) {
        return iItemCatService.findByParentId(parentId);
    }

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public ItemCat findOne(Long id){
        return iItemCatService.findOne(id);
    }
}