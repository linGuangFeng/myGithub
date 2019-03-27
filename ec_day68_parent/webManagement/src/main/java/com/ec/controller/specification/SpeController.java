package com.ec.controller.specification;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ec.pojo.page.PageResult;
import com.ec.pojo.resutl.Result;
import com.ec.pojo.specification.Specification;
import com.ec.pojo.specification.SpecificationVO;
import com.ec.service.specification.ISpeService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("specification")
public class SpeController {

    @Reference
    ISpeService speService;

    /**
     * 查询所有
     */
    @RequestMapping("findAll")
    public List<Specification> findAll() {
        return speService.findAll();
    }


    /**
     * 分页查询
     */
    @RequestMapping("findPage")
    public PageResult<Specification> findPage(int page, int rows) {
        return speService.findPage(page, rows);
    }

    /**
     * 查询实体
     */
    @RequestMapping("findOne")
    public SpecificationVO findOne(Long id) {
        return speService.findOne(id);
    }

    /**
     * 添加
     */
    @RequestMapping("add")
    public Result add(@RequestBody SpecificationVO specificationVO) {
        try {
            speService.add(specificationVO);
            return new Result(true, "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败！");
        }
    }

    /**
     * 修改
     */
    @RequestMapping("update")
    public Result update(@RequestBody SpecificationVO specificationVO) {
        try {
            speService.update(specificationVO);
            return new Result(true, "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败！");
        }
    }

    /**
     * 修改
     */
    @RequestMapping("delete")
    public Result delete(Long[] ids) {
        try {
            speService.delete(ids);
            return new Result(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败！");
        }
    }

    /**
     * 搜索
     */
    @RequestMapping("search")
    public PageResult search(int page, int rows, @RequestBody Specification specification) {
        return speService.search(page, rows, specification);
    }

    /**
     * 下拉多选框
     */
    @RequestMapping("selectOptionList")
    public List<Map<String, Object>> selectOptionList() {
        return speService.selectOptionList();
    }
}
