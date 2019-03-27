package com.ec.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ec.pojo.good.Goods;
import com.ec.pojo.page.PageResult;
import com.ec.pojo.resutl.Result;
import com.ec.service.goods.IGoodsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("goods")
public class GoodsController {


    @Reference
    IGoodsService goodsService;

    /**
     * 运营商根据商品名称搜索商品（定死条件：待审核，产品未删除）
     */
    @RequestMapping("search")
    public PageResult search(int page, int rows, @RequestBody Goods goods){
        PageResult pageResult = goodsService.searchForManager(page, rows, goods);
        return pageResult;
    }

    /**
     * 删除商品，逻辑性删除
     * 将字段is_delete设置为1
     */
    @RequestMapping("delete")
    public Result delete(Long[] ids){
        try{
            goodsService.delete(ids);
            return new Result(true,"删除成功!");
        }catch (Exception e){
            return new Result(false,"删除失败!");
        }
    }


    /**
     * 更新商品状态
     */
    @RequestMapping("updateStatus")
    public Result updateStatus(Long[] ids ,String status){
        try{
            goodsService.updateStatus(ids,status);
            return new Result(true,"操作成功!");
        }catch (Exception e){
            return new Result(false,"操作失败!");
        }
    }




}
