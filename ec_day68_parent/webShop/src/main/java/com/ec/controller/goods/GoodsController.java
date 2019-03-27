package com.ec.controller.goods;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ec.pojo.good.Goods;
import com.ec.pojo.page.PageResult;
import com.ec.pojo.resutl.Result;
import com.ec.pojo.vo.GoodsVO;
import com.ec.service.goods.IGoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("goods")
public class GoodsController {


    @Reference
    IGoodsService goodsService;

    /**
     * 添加商品
     * @param goodsVO
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestBody GoodsVO goodsVO){
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();//登录用户名就是商家id
            goodsVO.getGoods().setSellerId(username);
            goodsService.add(goodsVO);
            return new Result(true,"新增商品成功！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"新增商品失败！");
        }
    }

    @RequestMapping("search")
    public PageResult search(int page, int rows, @RequestBody Goods goods){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();//登录用户名就是商家id
        goods.setSellerId(username);
        return goodsService.search(page,rows,goods);
    }


    /**
     * 更新商品
     * @param goodsVO
     * @return
     */
    @RequestMapping("update")
    public Result update(@RequestBody GoodsVO goodsVO){
        try{
            goodsService.update(goodsVO);
            return new Result(true,"更新商品成功！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"更新商品失败！");
        }
    }



    /**
     * 根据id查询
     * @param id
     * @return GoodsVO
     */
    @RequestMapping("findOne")
    public GoodsVO findOne(Long id){
        return goodsService.findOne(id);
    }



}
