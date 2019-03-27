package com.ec.service.content;


import com.ec.pojo.ad.Content;
import com.ec.pojo.page.PageResult;

import java.util.List;

public interface ContentService {

	public List<Content> findAll();

	public PageResult findPage(Content content, Integer pageNum, Integer pageSize);

	public void add(Content content);

	public void edit(Content content);

	public Content findOne(Long id);

	public void delAll(Long[] ids);

	/**
	 * 首页大广告的轮播图
	 * @param categoryId
	 * @return
	 */
	public List<Content> findByCategoryId(Long categoryId);

}
