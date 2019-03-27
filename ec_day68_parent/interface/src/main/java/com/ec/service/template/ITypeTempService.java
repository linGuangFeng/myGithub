package com.ec.service.template;

import com.ec.pojo.page.PageResult;
import com.ec.pojo.template.TypeTemplate;

import java.util.List;
import java.util.Map;

public interface ITypeTempService{

    //todo 获取所有模板数据
    List<TypeTemplate> findAll();

    //todo 分页查询
    PageResult<TypeTemplate> findPage(int page, int rows);

    //todo 查询实体(回显：需查到模板，铭牌，规格)
    TypeTemplate findOne(Long id);

    //todo 新增
    void add(TypeTemplate typeTemplate);

    //todo 更新
    void update(TypeTemplate typeTemplate);

    //todo 删除
    void delete(Long[] ids);

    //todo 搜索查询
    PageResult<TypeTemplate> search(int page, int rows, TypeTemplate typeTemplate);

//    查询规格及规格项
    List<Map> findBySpecList(Long id);
}
