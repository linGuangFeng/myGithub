package com.ec.controller.brand;



import com.alibaba.dubbo.config.annotation.Reference;

import com.ec.pojo.good.Brand;

import com.ec.pojo.page.PageResult;
import com.ec.pojo.resutl.Result;
import com.ec.service.brand.IBrandService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("brand")
public class BrandController {

//    @Resource
    @Reference
    IBrandService brandService;


    /**
     * 查询品牌
     * @param currentPage
     * @param itemsPerPage
     * @return
     */
    @RequestMapping("findAll")
    public PageResult<Brand> findAll(@RequestParam("pageNum") Integer currentPage,
                                     @RequestParam("pageSize") Integer itemsPerPage){
        return brandService.findAll(currentPage,itemsPerPage);
    }
    @RequestMapping("findPage")
    public PageResult<Brand> findPage(@RequestParam("pageNum") Integer currentPage,
                                      @RequestParam("pageSize") Integer itemsPerPage){
        return brandService.findAll(currentPage,itemsPerPage);
    }

    /**
     * 条件查询品牌
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search")
    public PageResult<Brand> search(int pageNum, int pageSize, @RequestBody Brand brand){
        String name = brand.getName();
        String firstChar = brand.getFirstChar();
        PageResult<Brand> pageResult = brandService.search(pageNum,  pageSize,  name ,  firstChar);
        return pageResult;
    }


    /**
     * 添加品牌
     */
    @RequestMapping("add")
    public Result add(@RequestBody Brand brand){//将请求体的json数据转化程成对象
        try {
            brandService.save(brand);
            return new Result(true,"添加成功!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"添加失败!");
        }
    }

    /**
     * 根据id查询铭牌
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public Brand findOne(Long id){
        return brandService.findById(id);
    }


    /**
     * 更新品牌
     */
    @RequestMapping("update")
    public Result update(@RequestBody Brand brand){//将请求体的json数据转化程成对象
        try {
            brandService.update(brand);
            return new Result(true,"保存成功!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"保存失败!");
        }
    }

    /**
     * 删除品牌
     */
    @RequestMapping("delete")
    public Result delete(Long[] ids){//将请求体的json数据转化程成对象
        try {
            brandService.deleteByIds(ids);
            return new Result(true,"删除成功!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true,"删除失败!");
        }
    }

    /**
     * 下拉多选框
     */
    @RequestMapping("selectOptionList")
    public List<Map<String,Object>> selectOptionList(){
        return brandService.selectOptionList();
    }


}
