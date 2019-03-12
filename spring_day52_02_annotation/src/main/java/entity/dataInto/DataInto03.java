package entity.dataInto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DataInto03 {

    @Override
    public String toString() {
        return "DataInto01{" +
                "nonData='" + haveData + '\'' +
                '}';
    }

    /**
     * @Resource
     * 1. 所属包：javax.annotation.Resource;
     * 2. 根据名称或类型注入
     * 3. 如果指定名称，就只根据名称注入。
     * 4. 如果找不到会报错
     */

    @Resource(name = "resource")
    String haveData;

    public DataInto03() {
    }

    public DataInto03(String haveData) {
        this.haveData = haveData;
    }

    public String getHaveData() {
        return haveData;
    }

    public void setHaveData(String haveData) {
        this.haveData = haveData;
    }
}
