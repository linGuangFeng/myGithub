package com.ec.service.seller;

import com.alibaba.dubbo.config.annotation.Service;
import com.ec.dao.seller.SellerDao;
import com.ec.pojo.page.PageResult;
import com.ec.pojo.seller.Seller;
import com.ec.pojo.seller.SellerQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SellerServiceImpl implements ISellerService {

    @Resource
    SellerDao sellerDao;


    /**
     * 查询所有商家审核
     * @return
     */
    @Override
    public List<Seller> findAll() {
        return sellerDao.selectByExample(null);
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @Override
    public PageResult<Seller> findPage(int page, int rows) {
        return search(page,rows,null);
    }

    /**
     * 查询实体
     * @param id
     * @return
     */
    @Override
    public Seller findOne(String id) {
        return sellerDao.selectByPrimaryKey(id);
    }

    /**
     * 新增
     * @param seller
     */
    @Transactional
    @Override
    public void add(Seller seller) {
        if (seller != null) {
//            初始化审核状态
            seller.setStatus("0");
            //密码加密,spring_security框架自带的加密加随即盐方法
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            seller.setPassword(bCryptPasswordEncoder.encode(seller.getPassword()));
            //添加商家
            sellerDao.insertSelective(seller);
        }

    }


    /**
     * 更新
     * @param seller
     */
    @Transactional
    @Override
    public void update(Seller seller) {
        if (seller != null && seller.getSellerId() != null && seller.getSellerId().trim().length() != 0) {
            sellerDao.updateByPrimaryKeySelective(seller);
        }

    }

    /**
     * 删除
     * @param ids
     */
    @Transactional
    @Override
    public void delete(String[] ids) {
        if(ids != null && ids.length != 0) {
            sellerDao.deleteByPrimaryKeys(ids);
        }

    }

    /**
     * 分页搜索（公司名称与店铺名称）
     * @param page
     * @param rows
     * @param seller
     * @return
     */
    @Override
    public PageResult<Seller> search(int page, int rows, Seller seller) {
        /**
         * 步骤：
         * 1、设置分页数据，当前页与页大小
         * 2、创建搜索对象
         * 3、从搜索对象获取搜索条件对象
         * 4、判断封装的搜索信息是否为空
         *  4.1、空：查询所有
         *  4.2、返回
         * 5、判断搜索信息的公司名称是否为空
         *  5.1、不为空：将公司名称按模糊查寻设置到搜索条件
         * 6、判断搜索信息的店铺名称是否为空
         *  6.1、不为空：将店铺名称按模糊查寻设置到搜索条件
         * 7、判断审核状态是否为空
         *  7.1、不为空，将审核状态设置到模糊查询
         * 8、查询
         * 9、返回
         */
        PageHelper.startPage(page,rows);
        SellerQuery sellerQuery = new SellerQuery();
        SellerQuery.Criteria criteria = sellerQuery.createCriteria();

        //todo 查询所有
        if (seller == null){
            Page p = (Page)sellerDao.selectByExample(null);
            return new PageResult<>(p.getTotal(),p.getResult());
        }

        //todo 条件查询
        //公司名称条件设置
        if (seller.getName() != null && seller.getName().trim() != "" ) {
            criteria.andNameLike("%" + seller.getName() + "%");
        }
        //店铺名称条件就设置
        if (seller.getNickName() != null && seller.getNickName().trim() != ""){
            criteria.andNickNameLike("%" + seller.getNickName() + "%");
        }
        //审核状态条件设置
        if (seller.getStatus() != null && seller.getStatus().trim() != ""){
            criteria.andStatusEqualTo(seller.getStatus());
        }
        Page p = (Page)sellerDao.selectByExample(sellerQuery);
        return new PageResult<>(p.getTotal(),p.getResult());
    }

    /**
     * 审核状态更新（更据主键，更新状态）
     * @param sellerId
     * @param status
     */
    @Override
    public void updateStatus(String sellerId, String status) {
        //todo 如果主键不存在，直接异常，如果不做处理，可能会全部更新
        if(sellerId != null && sellerId.trim() != "") {
            Seller seller = new Seller();
            seller.setSellerId(sellerId);
            seller.setStatus(status);
            sellerDao.updateByPrimaryKeySelective(seller);
            return;
        }
        throw new RuntimeException();

    }
}
