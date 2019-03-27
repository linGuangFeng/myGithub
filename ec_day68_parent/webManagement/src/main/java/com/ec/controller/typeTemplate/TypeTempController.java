package com.ec.controller.typeTemplate;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ec.pojo.page.PageResult;
import com.ec.pojo.resutl.Result;
import com.ec.pojo.template.TypeTemplate;
import com.ec.service.template.ITypeTempService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("typeTemplate")
public class TypeTempController {
    @Reference
    ITypeTempService typeTempService;


    /**
     * 查询所有
     */
    @RequestMapping("findAll")
    public List<TypeTemplate> findAll(){
        return typeTempService.findAll();
    }

    /**
     * 分页查询
     */
    @RequestMapping("findPage")
    public PageResult findPage(int page , int rows){
        return typeTempService.findPage(page,rows);
    }

    /**
     * 查询实体(回显：需查到模板，铭牌，规格)
     */
    @RequestMapping("findOne")
    public TypeTemplate findOne(Long id){
        return typeTempService.findOne(id);
    }


    /**
     * 增加
     */
    @RequestMapping("add")
    public Result add(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTempService.add(typeTemplate);
            return new Result(true,"添加成功！");
        }catch (Exception e){
            return new Result(false,"添加失败！");
        }
    }

    /**
     * 修改
     */
    @RequestMapping("update")
    public Result update(@RequestBody TypeTemplate typeTemplate){
        try{
            typeTempService.update(typeTemplate);
            return new Result(true,"修改成功！");
        }catch (Exception e){
            return new Result(false,"修改失败！");
        }
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public Result delete(Long[] ids){
        try{
            typeTempService.delete(ids);
            return new Result(true,"删除成功！");
        }catch (Exception e){
            return new Result(false,"删除失败！");
        }
    }

    /**
     * 搜索
     */
    @RequestMapping("search")
    public PageResult search(int page, int rows, @RequestBody TypeTemplate typeTemplate){
        return typeTempService.search(page,rows,typeTemplate);
    }




}
