package com.ec.controller.content;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ec.pojo.ad.Content;
import com.ec.service.content.ContentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    @RequestMapping("/findAll.do")
    public List<Content> findAll(){
        return contentService.findAll();
    }

    /**
     * 首页大广告轮播
     * @param categoryId
     * @return
     */
    @RequestMapping("/findByCategoryId.do")
    public List<Content> findByCategoryId(Long categoryId){
        return contentService.findByCategoryId(categoryId);
    }
}
