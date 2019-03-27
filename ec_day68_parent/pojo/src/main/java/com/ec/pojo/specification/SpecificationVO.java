package com.ec.pojo.specification;

import java.io.Serializable;
import java.util.List;

public class SpecificationVO implements Serializable {

    Specification specification;
    List<SpecificationOption> specificationOptionList;

    public SpecificationVO() {
    }

    public SpecificationVO(Specification specification, List<SpecificationOption> specificationOptionList) {
        this.specification = specification;
        this.specificationOptionList = specificationOptionList;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public List<SpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<SpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }
}
