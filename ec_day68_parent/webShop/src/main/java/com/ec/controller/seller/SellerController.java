package com.ec.controller.seller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ec.pojo.resutl.Result;
import com.ec.pojo.seller.Seller;
import com.ec.service.seller.ISellerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("seller")
public class SellerController {

    @Reference
    private ISellerService sellerService;

    /**
     * 新增
     * @param seller
     */
    @RequestMapping("add")
    public Result add(@RequestBody Seller seller){
        try{
            sellerService.add(seller);
            return new Result(true,"添加成功！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true,"添加失败！");
        }
    }
}
