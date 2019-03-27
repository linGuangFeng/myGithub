package com.ec.controller.item;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ec.pojo.item.ItemCat;
import com.ec.pojo.page.PageResult;
import com.ec.pojo.resutl.Result;
import com.ec.service.item.IItemCatService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("itemCat")
@RestController
public class ItemCatController {

    @Reference
    IItemCatService iItemCatService;

    @RequestMapping("search")
    public PageResult search(int page, int rows, ItemCat itemCat){
            return iItemCatService.search(page,rows,itemCat);
    }

    @RequestMapping("findAll")
    public List findAll() {
        return iItemCatService.findAll();
    }

    /**
     * 根据parentId查询
     * @param parentId
     * @return
     */
    @RequestMapping("findByParentId")
    public List findByParentId(Long parentId){
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

    /**
     * 保存
     */
    @RequestMapping("add")
    public Result add(@RequestBody ItemCat itemCat){
        try {
            iItemCatService.add(itemCat);
            return new Result(true,"，保存成功！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"保存失败！");
        }
    }

}
