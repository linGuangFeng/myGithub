package entity.dataInto;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.scope.Scope;

import javax.annotation.Resource;

@Repository
public class DataInto04 {

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

    @Value("注解注入值")
    String haveData;
    public DataInto04() {
    }

    public DataInto04(String haveData) {
        this.haveData = haveData;
    }

    public String getHaveData() {
        return haveData;
    }


    public void HaveData( String haveData ,String str2) {
        this.haveData = haveData;
//        this.haveData = String.valueOf(haveData.equalsIgnoreCase(str2));
    }
}
