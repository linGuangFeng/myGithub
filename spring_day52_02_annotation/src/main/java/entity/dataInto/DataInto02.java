package entity.dataInto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DataInto02 {

    @Override
    public String toString() {
        return "DataInto01{" +
                "nonData='" + nonData + '\'' +
                '}';
    }

    /**
     * @Autowired修饰在方法上
     * 1. 先根据方法参数类型注入
     * 2. 如果类型有多个，根据方法形参名称去ioc容器找该名称对应的对象注入。如果根据名称没有找到，报错。
     *      —— 个人补充，上述的话是：根据成员变量名称去ioc容器找该名称对应的对象注入。如果根据名称没有找到，报错。
     * 3. 注解属性
     *    required
     *      true 表示必须去容器中找到对象注入，否则报错。[默认值，推荐]
     *      false 注入失败，即去容器中没有找到对象注入，不会报错。找到多个仍会报错
     *
     */

    /**
     * @Qualifier("str1")
     * 1. 只会去容器中根据名称是str1，找对应的对象注入。
     * 2. 找不到不会根据类型注入。
     */

    @Autowired(required = false)
    @Qualifier("nonData")//
    String nonData;

    public DataInto02() {
    }

    public DataInto02(String nonData) {
        this.nonData = nonData;
    }

    public String getNonData() {
        return nonData;
    }

    public void setNonData(String nonData) {
        this.nonData = nonData;
    }
}
