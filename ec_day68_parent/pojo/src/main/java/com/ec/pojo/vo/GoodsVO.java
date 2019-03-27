package com.ec.pojo.vo;

import com.ec.pojo.good.Goods;
import com.ec.pojo.good.GoodsDesc;
import com.ec.pojo.item.Item;

import java.io.Serializable;
import java.util.List;

public class GoodsVO implements Serializable {
    private Goods goods;
    private GoodsDesc goodsDesc;
    private List<Item>itemList;

    public GoodsVO() {
    }

    public Goods getGoods() {
        return goods;
    }

    public GoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public void setGoodsDesc(GoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }


}
