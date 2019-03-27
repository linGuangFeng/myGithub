package com.ec.service.brand;

import com.ec.pojo.good.Brand;
import com.ec.pojo.page.PageResult;

import java.util.List;
import java.util.Map;


public interface IBrandService {
    /**
     * 查询品牌
     * @return
     */
    PageResult findAll(int currentPage, int itemsPerPage);

    /**
     * 插入产品
     * @return
     */
    void save(Brand brand);

    /**
     * 条件查询品牌
     * @return
     */
    PageResult search(int currentPage, int itemsPerPage, String name , String firstChar);

    /**
     * 根据id查询品牌
     * @return
     */
    Brand findById(long id);

    /**
     * 更新名牌
     * @return
     */
    void update(Brand brand);

    /**
     * 删除铭牌
     * @param ids
     */
    void deleteByIds(Long[] ids);


    /**
     * 下拉多选框
     */
    List<Map<String,Object>> selectOptionList();
}
