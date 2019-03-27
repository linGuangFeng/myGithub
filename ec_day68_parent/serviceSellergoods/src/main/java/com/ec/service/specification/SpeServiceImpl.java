package com.ec.service.specification;

import com.alibaba.dubbo.config.annotation.Service;
import com.ec.dao.specification.SpecificationDao;
import com.ec.dao.specification.SpecificationOptionDao;
import com.ec.pojo.good.Brand;
import com.ec.pojo.page.PageResult;
import com.ec.pojo.specification.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpeServiceImpl implements ISpeService{

    @Resource
    SpecificationDao specificationDao;
    @Resource
    SpecificationOptionDao specificationOptionDao;

    /**
     * 查询所有
     */
    @Override
    public List<Specification> findAll() {
        return specificationDao.selectByExample(null);
    }

    /**
     * 分页查询
     */
    @Override
    public PageResult<Specification> findPage(int page, int rows) {
        return search(page,rows,null);
    }

    /**
     * 查询实体
     */
    @Override
    public SpecificationVO findOne(Long id) {
        SpecificationVO specificationVO = new SpecificationVO();
//        查询规格并设置到页面想要的实体类
        specificationVO.setSpecification(specificationDao.selectByPrimaryKey(id));
        //设置查询规格项的条件
        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
        criteria.andSpecIdEqualTo(id);
//        查询规格项
        List<SpecificationOption> specificationOptions = specificationOptionDao.selectByExample(specificationOptionQuery);
//        将查询到的规格项设置到页面想要的实体类并返回
        specificationVO.setSpecificationOptionList(specificationOptions);
        return specificationVO;
    }

    /**
     * 保存
     */
    @Transactional
    @Override
    public void add(SpecificationVO specificationVO) {
        //保存规格
        Specification specification = specificationVO.getSpecification();
        specificationDao.insertSelective(specification);
        //保存规格选项
        List<SpecificationOption> specificationOptions = specificationVO.getSpecificationOptionList();
        if (specificationOptions != null && specificationOptions.size() >0){
            for (SpecificationOption specificationOption : specificationOptions){
//                设置外键
                specificationOption.setSpecId(specification.getId());
//                specificationOptionDao.insertSelective(specificationOption);//一条一条插入方法
            }
            specificationOptionDao.inserts(specificationOptions);//插入多条
        }


    }

    /**
     * 更新
     */
    @Transactional
    @Override
    public void update(SpecificationVO specificationVO) {
        //保存规格
        Specification specification = specificationVO.getSpecification();
        specificationDao.updateByPrimaryKeySelective(specification);

        //保存规格选项,先删除规格选项，在插入
        Long id = specification.getId();
        Long[] ids = {id};
        specificationOptionDao.deleteByReferences(ids);
        List<SpecificationOption> specificationOptionList = specificationVO.getSpecificationOptionList();
        for (SpecificationOption specificationOption : specificationOptionList){
            //设置外键
            specificationOption.setSpecId(specification.getId());
//            specificationOptionDao.updateByPrimaryKey(specificationOption);
        }
        //id被刷新
        specificationOptionDao.inserts(specificationOptionList);//插入多条
    }

    /**
     * 批量删除
     */
    @Transactional
    @Override
    public void delete(Long[] ids) {
        if (ids != null && ids.length != 0 ) {
//        必须先删除外键，在删除主键
            specificationOptionDao.deleteByReferences(ids);
            specificationDao.deleteByPrimaryKeys(ids);
        }
    }

    /**
     * 搜索
     */
    @Override
    public PageResult<Specification> search(int pageNum, int pageSize, Specification specification) {
        //页面设置
        PageHelper.startPage(pageNum,pageSize);
//        确定查询的类→设置内部类的参数（设置查询条件）
        SpecificationQuery specificationQuery = new SpecificationQuery();
        SpecificationQuery.Criteria criteria = specificationQuery.createCriteria();
        if (specification != null && specification.getSpecName() != null && specification.getSpecName().trim().length() > 0){
            criteria.andSpecNameLike("%" + specification.getSpecName() + "%" );
        }

//        倒序查询
        specificationQuery.setOrderByClause("id desc");
        Page page = (Page) specificationDao.selectByExample(specificationQuery);

        //将结果设置到序列化的类中，提高效率
        return new PageResult<Specification>(page.getTotal(),page.getResult());
    }

    /**
     * 下拉多选框
     * 页面需要这种类型的值：[{id:1,text:'bug'},{id:2,text:'duplicate'}]
     * @return
     */
    public List<Map<String,Object>> selectOptionList(){
        /**
         * 1，从数据库获取所有数据
         * 2，创建list集合
         * 3，遍历从数据库获取到的数据
         *  3.1 将从数据库的id与spec_name分别作为Map集合的总键id与text存放
         *  3.2 将map集合添加至list集合
         * 4，遍历完成后，返回list集合
         */
        List<Specification> specifications = specificationDao.selectByExample(null);
        List<Map<String,Object>> list = new ArrayList<>();
        for (Specification specification : specifications){
            Map<String,Object> map = new HashMap<>();
            map.put("id",specification.getId());
            map.put("text",specification.getSpecName());
            list.add(map);
        }
        return list;
    }
}
