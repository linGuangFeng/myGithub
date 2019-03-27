package com.ec.service.goods;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ec.dao.good.BrandDao;
import com.ec.dao.good.GoodsDao;
import com.ec.dao.good.GoodsDescDao;
import com.ec.dao.item.ItemCatDao;
import com.ec.dao.item.ItemDao;
import com.ec.dao.seller.SellerDao;
import com.ec.pojo.good.Goods;
import com.ec.pojo.good.GoodsDesc;
import com.ec.pojo.good.GoodsQuery;
import com.ec.pojo.item.Item;
import com.ec.pojo.item.ItemQuery;
import com.ec.pojo.page.PageResult;
import com.ec.pojo.vo.GoodsVO;
import com.ec.service.item.ItemServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class GoodsServiceImpl implements IGoodsService {

    @Resource
    private SolrTemplate solrTemplate;

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private GoodsDescDao goodsDescDao;

    @Resource
    private ItemDao itemDao;

    @Resource
    private ItemCatDao itemCatDao;

    @Resource
    private SellerDao sellerDao;

    @Resource
    private BrandDao brandDao;


    /**
     * 新增商品
     *
     * @param goodsVO
     */
    @Transactional
    @Override
    public void add(GoodsVO goodsVO) {
        Goods goods = goodsVO.getGoods();
        GoodsDesc goodsDesc = goodsVO.getGoodsDesc();
        List<Item> itemList = goodsVO.getItemList();

        goods.setAuditStatus("0");//商品待审核
        goodsDao.insertSelective(goods);//自增的主键id会设置到goods

        goodsDesc.setGoodsId(goods.getId());//一张表一份为二，主键id相等
        goodsDescDao.insertSelective(goodsDesc);//新增详情

        /**
         * 库存表包含的字段：id/title/sell_point/price/stock_count/num/barcode/image/categoryId/status/create_time/create_time/update_time/item_sn/cost_pirce/market_price/is_default/goods_id/seller_id/cart_thumbanil/category/brand/spec/seller
         *其中要自己处理或设置的字段：title/image/category_id/status/create_time/update_time/goods_id/seller_id/category/brand/seller
         */

        String goodsName = goods.getGoodsName();//spu名称
        String caption = goods.getCaption();//spu副标题
        if ("1".equals(goods.getIsEnableSpec())) {//启用规格

            /**
             * titile = spu名称+spu副标题+规格（库存的spec）
             * spec：{"网络":"移动3G","机身内存":"32G"},需要背后的值
             */
            //遍历itemList获取spec
            for (Item item : itemList) {
                String title = goodsName + " " + caption;
                String spec = item.getSpec();
                //将所有规格的值从map集合中取出
                Map<String, String> map = JSON.parseObject(spec, Map.class);
                Set<Map.Entry<String, String>> entrys = map.entrySet();
                for (Map.Entry<String, String> entry : entrys) {
                    title += " " + entry.getValue();
                }
                //todo title设置
                item.setTitle(title);
                //设置库存属性
                setAttributeForItem(goods, goodsDesc, item);
                itemDao.insertSelective(item);//添加
            }
        } else {
            // 未启用规格
            Item item = new Item();
            item.setTitle(goods.getGoodsName() + " " + goods.getCaption());
            item.setPrice(goods.getPrice());
            item.setNum(9999);
            item.setIsDefault("1");
            item.setSpec("{}");
            setAttributeForItem(goods, goodsDesc, item);
            itemDao.insertSelective(item);//添加
        }
    }

    /**
     * 修改回显商品数据
     * 涉及到数据库的表有：goods,goods_desc,item
     */
    @Override
    public GoodsVO findOne(Long id) {
        GoodsVO goodsVO = new GoodsVO();
        goodsVO.setGoods(goodsDao.selectByPrimaryKey(id));
        goodsVO.setGoodsDesc(goodsDescDao.selectByPrimaryKey(id));

        //库存查询(根据商品id)
        ItemQuery itemQuery = new ItemQuery();
        itemQuery.createCriteria().andGoodsIdEqualTo(id);
        goodsVO.setItemList(itemDao.selectByExample(itemQuery));

        return goodsVO;
    }

    /**
     * 根据商品名称及审核状态搜索
     * 隐藏条件，商家名称
     *
     * @param page
     * @param rows
     * @param goods
     */
    @Override
    public PageResult search(int page, int rows, Goods goods) {
        PageHelper.startPage(page, rows);
        GoodsQuery goodsQuery = new GoodsQuery();
        GoodsQuery.Criteria criteria = goodsQuery.createCriteria();

        //商品名称
        if (goods.getGoodsName() != null && !"".equals(goods.getGoodsName().trim())) {
            criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
        }
        try {//审核状态搜索
            Integer.valueOf(goods.getAuditStatus());//如果不报错，设置商品审核状态
            criteria.andAuditStatusEqualTo(goods.getAuditStatus());
        } catch (Exception e) {
        }
        //隐藏条件，商家名称
        if (goods.getSellerId() != null && !"".equals(goods.getSellerId())) {
            criteria.andSellerIdEqualTo(goods.getSellerId());
        }

        goodsQuery.setOrderByClause("id desc");//倒序
        //查询并包装成前台要的数据格式
        Page p = (Page) goodsDao.selectByExample(goodsQuery);
        return new PageResult(p.getTotal(), p.getResult());


    }


    /**
     * 运营商根据商品名称搜索商品（定死条件：待审核，产品未删除）
     *
     * @param page
     * @param rows
     * @param goods
     * @return
     */
    @Override
    public PageResult searchForManager(int page, int rows, Goods goods) {
        new ItemServiceImpl().itemImportSolr(itemDao,solrTemplate);//将已上架的库存写入solr服务器

        PageHelper.startPage(page, rows);
//        查询条件设置
        GoodsQuery goodsQuery = new GoodsQuery();
        GoodsQuery.Criteria criteria = goodsQuery.createCriteria();
        //设置商品名称的查询条件
        if (goods.getGoodsName() != null && goods.getGoodsName().trim().length() != 0) {
            criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
        }

        goodsQuery.setOrderByClause("id desc");//倒序
        //定死条件：待审核(0)，产品未删除(控制代表未删除)
        criteria.andAuditStatusEqualTo("0").andIsDeleteIsNull();

        Page p = (Page) goodsDao.selectByExample(goodsQuery);
        return new PageResult(p.getTotal(), p.getResult());
    }

    /**
     * 删除商品，逻辑性删除
     * 将字段is_delete设置为1
     */
    @Transactional
    @Override
    public void delete(Long[] ids) {
        Goods goods = new Goods();
        goods.setIsDelete("1");
        for (Long id : ids) {
            goods.setId(id);
            goodsDao.updateByPrimaryKeySelective(goods);
        }
    }

    /**
     * 更新商品，隐藏条件（审核条件置为0（待审核））
     *
     * @param goodsVO
     */
    @Transactional
    @Override
    public void update(GoodsVO goodsVO) {
        //三个实体已经有商品id
        Goods goods = goodsVO.getGoods();
        GoodsDesc goodsDesc = goodsVO.getGoodsDesc();
        List<Item> itemList = goodsVO.getItemList();

        goods.setAuditStatus("0");//审核置为0
        goodsDao.updateByPrimaryKeySelective(goods);
        goodsDescDao.updateByPrimaryKeySelective(goodsDesc);


//         * 商品库存先删除在插入（如果直接根据坤村的id更新，无法更新已删除的库存）
//         * 根据商品id删除

        ItemQuery itemQuery = new ItemQuery();
        itemQuery.createCriteria().andGoodsIdEqualTo(goods.getId());
        itemDao.deleteByExample(itemQuery);//删除
        //图片url处理（goodsDesc,数据格式）
//        [{"color":"粉色","url":"http://192.168.25.133/group1/M00/00/00/wKgZhVmOXq2AFIs5AAgawLS1G5Y004.jpg"},
//        {"color":"黑色","url":"http://192.168.25.133/group1/M00/00/00/wKgZhVmOXrWAcIsOAAETwD7A1Is874.jpg"}]
        if ("1".equals(goods.getIsEnableSpec())) {
            //启用规格

            //获取第一张图片
            String url = goodsDesc.getItemImages();
            List<Map> maps = JSON.parseArray(url, Map.class);
            String image = maps.get(0).get("url").toString();


            //* titile = spu名称+spu副标题+规格（库存的spec）
            //* spec：{"网络":"移动3G","机身内存":"32G"},需要背后的值
            String title = goods.getGoodsName() + " " + goods.getCaption();

            for (Item item : itemList) {
                item.setImage(image);//将第一张图片url存入库存

                //标题设置:spec：{"网络":"移动3G","机身内存":"32G"},需要背后的值
                Map spec = JSON.parseObject(item.getSpec(), Map.class);
                Set<Map.Entry<String, String>> entrys = spec.entrySet();
                for (Map.Entry<String, String> entry : entrys) {
                    title += " " + entry.getValue();
                }
                item.setTitle(title);
                setAttributeForItem(goods, goodsDesc, item);//库存设置
                itemDao.insertSelective(item);//插入
            }
        } else {
            // 未启用规格
            Item item = new Item();
            item.setTitle(goods.getGoodsName() + " " + goods.getCaption());
            item.setPrice(goods.getPrice());
            item.setNum(9999);
            item.setIsDefault("1");
            item.setSpec("{}");
            setAttributeForItem(goods, goodsDesc, item);
            itemDao.insertSelective(item);//添加
        }
    }

    /**
     * 更新商品状态
     *
     * @param ids
     * @param status
     */
    @Override
    public void updateStatus(Long[] ids, String status) {
        Goods goods = new Goods();
        goods.setAuditStatus(status);
        for (Long id : ids) {
            goods.setId(id);
            goodsDao.updateByPrimaryKeySelective(goods);
//            将商品的索引库存加入添至索引库
            itemToSolr(id);
        }
    }

    /**
     * 商品库存属性设置
     *
     * @param goods
     * @param goodsDesc
     * @param item
     */
    public void setAttributeForItem(Goods goods, GoodsDesc goodsDesc, Item item) {
        //todo image/category_id/status/create_time/update_time/goods_id/seller_id/category/brand/seller

        /**:image
         * 获取到图片的格式：
         *  [{"color":"粉色","url":"http://192.168.25.133/group1/M00/00/00/wKgZhVmOXq2AFIs5AAgawLS1G5Y004.jpg"},
         *   {"color":"黑色","url":"http://192.168.25.133/group1/M00/00/00/wKgZhVmOXrWAcIsOAAETwD7A1Is874.jpg"}]
         *将第一张图片的url设置到库存中
         */
        String itemImages = goodsDesc.getItemImages();
        List<Map> jsonArray = JSON.parseArray(itemImages, Map.class);
        if (jsonArray != null && jsonArray.size() > 0) {
            String url = jsonArray.get(0).get("url").toString();
            item.setImage(url);//
        }
        item.setCategoryid(goods.getCategory3Id());//category_id:也就是goods的三级分类
        item.setStatus("1");//status：库存商品的状态
        item.setCreateTime(new Date());//create_time:商品创建时间
        item.setUpdateTime(new Date());//update_time:商品更新时间
        item.setGoodsId(goods.getId());//goods_id:商品的id
        item.setSeller(goods.getSellerId());//seller_id:商家id
        item.setCategory(itemCatDao.selectByPrimaryKey(goods.getCategory3Id()).getName()); //category:三级分类名称（三级的分类名称的id是分类表的主键）
        item.setBrand(brandDao.selectByPrimaryKey(goods.getBrandId()).getName());//brand:品牌名称，根据匹配id查找品牌然后在获取其名称
        item.setSeller(sellerDao.selectByPrimaryKey(goods.getSellerId()).getNickName());//seller:根据商家id查找商家然后获取其名称
//        item.setSeller(sellerDao.selectByPrimaryKey("admin2").getNickName());
    }


    /**
     * 商品的库存添至solr索引
     */
    public void itemToSolr(Long goodsId) {

        ItemQuery itemQuery = new ItemQuery();
        if (goodsId == null) return;
        itemQuery.createCriteria().andGoodsIdEqualTo(goodsId);
        List<Item> itemList = itemDao.selectByExample(itemQuery);//查询出库存

        for (Item item : itemList) {
            solrTemplate.saveBean(item);
        }
    }
}