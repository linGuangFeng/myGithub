package com.ec.service.brand;

import com.alibaba.dubbo.config.annotation.Service;

import com.ec.dao.good.BrandDao;
import com.ec.pojo.good.Brand;
import com.ec.pojo.good.BrandQuery;
import com.ec.pojo.page.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;


import javax.annotation.Resource;
import javax.naming.ldap.PagedResultsControl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements IBrandService {
    @Resource
    BrandDao brandDao;


    /**
     * 查询品牌
     * @param currentPage
     * @param itemsPerPage
     * @return
     */
    @Override
    public PageResult findAll(int currentPage, int itemsPerPage) {
//        分页数据设置
        PageHelper.startPage(currentPage,itemsPerPage);
//        查询数据
        Page<Brand> page = (Page)brandDao.selectByExample(null);

//        将数据序列化
        PageResult<Brand> pageResult = new PageResult(page.getTotal(),page.getResult());

        return pageResult;
    }

    /**
     * 条件查询
     * @param currentPage
     * @param itemsPerPage
     * @return
     */
    @Override
    public PageResult search(int currentPage, int itemsPerPage, String name , String firstChar) {
//        分页数据设置
        PageHelper.startPage(currentPage,itemsPerPage);
//        查询数据
//        Page<Brand> page = (Page)brandDao.selectByExample(null);

//        查询条件对象创建--确认是哪个类
        BrandQuery brandQuery = new BrandQuery();
//        封装具体查询条件对象
        BrandQuery.Criteria criteria = brandQuery.createCriteria();

//        设置查询条件
        if(!StringUtils.isNullOrEmpty(name)){
            name = name.trim();
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isNullOrEmpty(firstChar)){
            criteria.andFirstCharEqualTo(firstChar);
        }
        brandQuery.setOrderByClause("id desc");

//        查询数据
        Page page = (Page) brandDao.selectByExample(brandQuery);

//        将数据序列化
        PageResult<Brand> pageResult = new PageResult(page.getTotal(),page.getResult());

        return pageResult;
    }

    /**
     * 插入产品
     * @return
     */
    @Override
    public void save(Brand brand) {
        //insert()方法没判是否为空值
        brandDao.insertSelective(brand);
    }

    /**
     * 根据id查询铭牌
     * @param id
     * @return
     */
    @Override
    public Brand findById(long id){
        return brandDao.selectByPrimaryKey(id);
    }

    /**
     * 更新名牌
     * @param brand
     * @return
     */
    @Override
    public void update(Brand brand) {
        brandDao.updateByPrimaryKeySelective(brand);
    }

    /**
     * 删除铭牌
     * @param ids
     */
    @Override
    public void deleteByIds(Long[] ids) {
        if (ids != null && ids.length != 0 ) {
            brandDao.deleteByPrimaryKeys(ids);
        }
    }


    /**
     * 下拉多选框
     * 页面需要这种类型的值：[{id:1,text:'bug'},{id:2,text:'duplicate'}]
     * @return
     */
    @Override
    public List<Map<String,Object>> selectOptionList(){
        /**
         * 1，从数据库获取所有数据
         * 2，创建list集合
         * 3，遍历从数据库获取到的数据
         *  3.1 将从数据库的id与name分别作为Map集合的总键id与text存放
         *  3.2 将map集合添加至list集合
         * 4，遍历完成后，返回list集合
         */

        List<Brand> brands = brandDao.selectByExample(null);
        List<Map<String,Object>> list = new ArrayList<>();
        for (Brand brand : brands){
            Map<String,Object> map = new HashMap<>();
            map.put("id",brand.getId());
            map.put("text",brand.getName());
            list.add(map);
        }
        return list;
    }
}