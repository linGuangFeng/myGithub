package com.factory;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

/**
 * 配置数据库连接/操作数据库相关的对象
 * @PropertySource
 * 1. 加载类路径下的配置文件
 * 2. 相当于:<context:property-placeholder location="classpath:jdbc.properties"/>
 * 3. 注意:不要写classpath. 一般在注解中加载配置文件都不需要写classpath
 */
@PropertySource("jdbc.properties")//加载配置文件
public class ConfigInfo {


    @Value("${jdbc.driver}")
    private String driver ;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;



    @Bean(name = "dataSource1")
    public DataSource createDataSource(){
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(this.driver);
        ds.setUrl(this.url);
        ds.setUsername(this.username);
        ds.setPassword(this.password);
        return ds;
    }
    /**
     * 创建jdbcTemplate,自动把方法返回的对象加入ioc容器.
     * 方法参数dataSource:
     * 1. 会自动去容器找该参数类型对应的对象,自动注入到方法中.
     * 2. 参数的类型在容器中有多个对象,会自动根据方法形参名称去容器找该名称对应的对象注入.
     *    如果没有找到就报错.
     * 3.@Qualifier("dataSource1")
     *   A. 目前@Qualifier独立使用
     *   B. 修饰在方法参数上
     *   C. 表示根据指定的名称,去容器找该名称对应的对象注入.
     *
     */
    @Bean("jdbcTemplate")
    public JdbcTemplate setJdbcTemplate(DataSource dataSource){
        System.out.println(dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

}
