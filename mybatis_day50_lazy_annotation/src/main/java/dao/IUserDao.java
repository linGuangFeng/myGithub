package dao;



import entity.FindUserByCondition;
import entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface IUserDao<T> extends Serializable {


    /***
     * 延时加载练习
     * @return
     */
    List<T> findAll();
    User findById(int id);


    /**
     *注解练习
     * 注：必须继承Serializable
     */

    @Select("select * from user")
    //查找所有用户,不能使用泛型，
    // 使用配置文件的方法之所以可以所有泛型，是因为在配置文件已指定了数据类型
    List<User> findAllAnno();

    //三种获取参数的方法，也适用于配置文件
    @Insert("insert into user (username,birthday,sex,address) values(#{username},#{birthday},#{sex},#{address})")
    Boolean insertAnno_01(User user);
    @Insert("insert into user (username,birthday,sex,address) values(#{arg0},#{arg1},#{arg2},#{arg3})")
    Boolean insertAnno_02(String username, Date birthday, String sex, String address);
    @Select("select * from user limit #{start},#{count}")
    List<User> findUserLimitAnno(@Param("start") int start,@Param("count") int count);

    /***
     * 注解的动态sql语句
     */
    //根据用户名，生日范围查询用户，也有可能没条件
    @Select("<script>" +
            "select * from user" +
    "<where>\n" +
            "    <if test=\"user != null and user.username != ''\">\n" +
            "        and username like #{user.username}\n" +
            "    </if>\n" +
            "    <if test=\"start != null\">\n" +
            "        and birthday >= #{start}\n" +
            "    </if>\n" +
            "    <if test=\"end != null\">\n" +
            "        and birthday &lt;= #{end}\n" +
            "    </if>\n" +
            "</where>\n" +
            "</script>"
    )
    List<User> findUserByConditionAnno(FindUserByCondition findUserByCondition);


    /***
     * 使用注解延时加载
     * 一对多
     * @param i
     */

    @Select("select * from user")
    @Results({
            @Result ( id = true , property = "id" , column = "id"),
            @Result ( property = "id" , column = "id"),
            @Result ( property = "birthday" , column = "birthday"),
            @Result ( property = "sex" , column = "sex"),
            @Result ( property = "address" , column = "address"),

            //原理与配置文件一样
            @Result ( property = "accounts", column = "id" ,javaType = List.class,
                        many = @Many(
                                select = "dao.IAccountDao.findById",
                                fetchType = FetchType.LAZY
                        )
            )
    })
    List<User> findAllAnno_01(int i);
    User findByIdAnno(int id);



}
