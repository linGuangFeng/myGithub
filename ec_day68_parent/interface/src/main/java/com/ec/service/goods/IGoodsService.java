package com.ec.service.goods;

import com.ec.pojo.good.Goods;
import com.ec.pojo.page.PageResult;
import com.ec.pojo.vo.GoodsVO;

public interface IGoodsService {
    //添加商品
    void add(GoodsVO goodsVO);

    //商家搜索商品
    PageResult search(int page, int rows, Goods goods);

    //修改数据回显
    GoodsVO findOne(Long id);

    //运营商搜索待审核商品
    PageResult searchForManager(int page, int rows, Goods goods);

    //删除商品，逻辑性删除
    void delete(Long[] ids);

    //更新商品
    void update(GoodsVO goodsVO);

    //更新商品状态
    void updateStatus(Long[] ids , String status);

}
