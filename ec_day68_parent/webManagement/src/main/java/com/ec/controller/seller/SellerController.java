package com.ec.controller.seller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ec.pojo.page.PageResult;
import com.ec.pojo.resutl.Result;
import com.ec.pojo.seller.Seller;
import com.ec.service.seller.ISellerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//com.ec.controller.seller
@RestController
@RequestMapping("seller")
public class SellerController {

    @Reference
    private ISellerService sellerService;

    /**
     * 查询所有商家审核
     * @return
     */
    @RequestMapping("findAll")
    public List<Seller> findAll(){
        return sellerService.findAll();
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("findPage")
    public PageResult<Seller> findPage(int page, int rows){
        return sellerService.findPage(page,rows);
    }


    /**
     * 查询实体
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public Seller findOne(String id){
        return sellerService.findOne(id);
    }

  /*  *//**
     * 新增
     * @param seller
     *//*
    @RequestMapping("add")
    public Result add(@RequestBody Seller seller){
        try{
            sellerService.add(seller);
            return new Result(true,"添加成功！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true,"添加失败！");
        }
    }*/

    /**
     * 更新
     * @param seller
     */
    @RequestMapping("update")
    public Result update(@RequestBody Seller seller){
        try{
            sellerService.update(seller);
            return new Result(true,"更新成功！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true,"更新失败！");
        }
    }

    /**
     * 删除
     * @param ids
     */
    @RequestMapping("delete")
    public Result delete(String[] ids){
        try{
            sellerService.delete(ids);
            return new Result(true,"删除成功！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true,"删除失败！");
        }
    }

    /**
     * 分页搜索（公司名称与店铺名称）
     * @param page
     * @param rows
     * @param seller
     * @return
     */
    @RequestMapping("search")
    public PageResult<Seller> search(int page, int rows,@RequestBody Seller seller){
        return sellerService.search(page,rows,seller);
    }

    /**
     * 审核状态更新（更据主键，更新状态）
     * @param sellerId
     * @param status
     */
    @RequestMapping("updateStatus")
    public Result updateStatus(String sellerId, String status){
        try{
            sellerService.updateStatus(sellerId,status);
            return new Result(true,"操作成功！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true,"操作失败！");
        }
    }




}
