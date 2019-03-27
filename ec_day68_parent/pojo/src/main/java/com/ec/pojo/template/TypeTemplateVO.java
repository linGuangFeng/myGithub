package com.ec.pojo.template;

import java.io.Serializable;

public class TypeTemplateVO implements Serializable {
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 关联规格
     */
    private String specIds;

    /**
     * 关联品牌
     */
    private String brandIds;

    /**
     * 自定义属性
     */
    private String customAttributeItems;

    private static final long serialVersionUID = 1L;



}
