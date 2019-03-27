package com.ec.service.specification;

import com.ec.pojo.page.PageResult;
import com.ec.pojo.specification.Specification;
import com.ec.pojo.specification.SpecificationVO;

import java.util.List;
import java.util.Map;

public interface ISpeService {
    /**
     * 查询所有
     */
    List<Specification> findAll();

    /**
     * 分页查询
     */
    PageResult<Specification> findPage(int page, int rows);

    /**
     * 查询实体
     */
    SpecificationVO findOne(Long id);

    /**
     * 保存
     */
    void add(SpecificationVO specificationVO);

    /**
     * 更新
     */
    void update(SpecificationVO specificationVO);

    /**
     * 批量删除
     */
    void delete(Long[] ids);

    /**
     * 搜索
     */
    PageResult<Specification> search(int pageNum, int pageSize, Specification specification);


    /**
     * 下拉多选框
     */
    List<Map<String,Object>> selectOptionList();

}
