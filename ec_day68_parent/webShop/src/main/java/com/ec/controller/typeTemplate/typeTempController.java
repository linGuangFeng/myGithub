package com.ec.controller.typeTemplate;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ec.pojo.template.TypeTemplate;
import com.ec.service.template.ITypeTempService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("typeTemplate")
public class typeTempController {


    @Reference
    ITypeTempService typeTempService;
    /**
     * 查询实体()
     */
    @RequestMapping("findOne")
    public TypeTemplate findOne(Long id){
        return typeTempService.findOne(id);
    }


    //{text:name,options:[option1,option2]}]
    @RequestMapping("findBySpecList")
    public List<Map> findBySpecList(Long id){
        return typeTempService.findBySpecList(id);
    }
}
