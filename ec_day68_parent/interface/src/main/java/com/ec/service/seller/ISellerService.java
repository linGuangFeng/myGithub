package com.ec.service.seller;

import com.ec.pojo.page.PageResult;
import com.ec.pojo.seller.Seller;

import java.util.List;

public interface ISellerService {

    //todo 获取所有
    List<Seller> findAll();

    //todo 分页查询
    PageResult<Seller> findPage(int page, int rows);

    //todo 查询实体(回显)
    Seller findOne(String id);

    //todo 新增
    void add(Seller seller);

    //todo 更新
    void update(Seller seller);

    //todo 删除
    void delete(String[] ids);

    //todo 搜索查询
    PageResult<Seller> search(int page, int rows, Seller seller);

    //todo 审核状态更新
    void updateStatus(String sellerId, String status);


}
