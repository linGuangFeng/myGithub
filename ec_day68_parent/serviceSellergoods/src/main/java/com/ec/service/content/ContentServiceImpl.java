package com.ec.service.content;


import com.alibaba.dubbo.config.annotation.Service;
import com.ec.dao.ad.ContentDao;
import com.ec.pojo.ad.Content;
import com.ec.pojo.ad.ContentQuery;
import com.ec.pojo.page.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ContentServiceImpl implements ContentService {

	@Resource
	private ContentDao contentDao;

    @Resource
	private RedisTemplate<String, Map> redisTemplate;

	@Override
	public List<Content> findAll() {
		List<Content> list = contentDao.selectByExample(null);
		return list;
	}

	@Override
	public PageResult findPage(Content content, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<Content> page = (Page<Content>)contentDao.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

    /**
     * 新增广告图片隐藏任务（删除缓存的图片，然后在存入图片）
     * @param content
     */
	@Override
	public void add(Content content) {
        updateCache(content);
		contentDao.insertSelective(content);
	}

    /**
     * 更新广告图片隐藏任务（删除缓存的图片，然后在存入图片）
     * @param content
     */
	@Override
	public void edit(Content content) {
        updateCache(content);//必须在这之前，更新商品可能连分类的被更新

    }

	@Override
	public Content findOne(Long id) {
		Content content = contentDao.selectByPrimaryKey(id);
		return content;
	}

    /**
     * 更新广告图片隐藏任务（删除缓存的图片片）
     * @param ids
     */
	@Override
	public void delAll(Long[] ids) {
		if(ids != null){
			for(Long id : ids){
				contentDao.deleteByPrimaryKey(id);
				clearCache(id);
			}
		}
	}

	/**
	 * 首页大广告的轮播图
	 * @param categoryId
	 * @return
	 */
    @Override
    public List<Content> findByCategoryId(Long categoryId) {
    	/*// 设置条件：根据广告分类查询，并且是可用的，
		ContentQuery query = new ContentQuery();
		query.createCriteria().andCategoryIdEqualTo(categoryId).
				andStatusEqualTo("1");
		query.setOrderByClause("sort_order desc");
		List<Content> list = contentDao.selectByExample(query);
		return list;*/
    	return findByCategoryIdCache(categoryId);
    }

    /**
     * 以分类id搜索广告数据（因此缓冲的value值是多个Content对象）
     *
     *因为所有线程都是使用同一个实例方法调用，因此可以使用synchronized方法来同步
     * @param categoryId
     * @return
     */
    public synchronized List<Content> findByCategoryIdCache(Long categoryId) {
/*
        采用Redis的哪种数据结构：
        String set key value  key:categoryId value:数据
        Redis优化之一：减少与redis客户端交互的次数（n个广告分类，交互n次）
        优化：hash  hset key (map)filed value  交互一次。
        首先判断缓存中是否有数据
 */

        List<Content> list = (List<Content>) redisTemplate.boundHashOps("content").get(categoryId);

        if(list == null){//缓冲中没有数据
            // 设置条件：根据广告分类查询，并且是可用的，
            ContentQuery query = new ContentQuery();
            query.createCriteria().andCategoryIdEqualTo(categoryId).
                    andStatusEqualTo("1");
            query.setOrderByClause("sort_order desc");
            list = contentDao.selectByExample(query);
           // 将数据设置到缓存      以categoryId作为字段名存放value值
            redisTemplate.boundHashOps("content").put(categoryId,list);
        }

        return list;
    }

    /**
     * 更新图片的缓存
     * @param content
     */
    public void updateCache(Content content){
        // 需要判断广告的分类是否发生改变发（以分类的CategoryId作为字段名）
        // 如果分类发生改变：删除之前数据、删除现在更新的分类数据
        Long newCategoryId = contentDao.selectByPrimaryKey(content.getId()).getCategoryId();//新数据的id
        Long oldCategoryId = content.getCategoryId();//旧数据的id

        if (!newCategoryId.equals(oldCategoryId)){//字段名发生改变，
            clearCache(newCategoryId);//删除现在更新的分类数据
        }
        clearCache(oldCategoryId);//删除旧缓存

        contentDao.updateByPrimaryKeySelective(content);//更新广告

        //根据分类id查询数据库的广告并存入缓存
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.createCriteria().andCategoryIdEqualTo(newCategoryId);
        List<Content> list = contentDao.selectByExample(contentQuery);
        redisTemplate.boundHashOps("content").put(newCategoryId,list);
    }

    /**
     * 清除缓存
     */
    public void clearCache(Long id){
        redisTemplate.boundHashOps("content").delete(id);
    }



}
