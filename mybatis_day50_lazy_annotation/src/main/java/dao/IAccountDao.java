package dao;



import entity.Account;
import entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface IAccountDao<T> {


    /***
     * 延时加载练习
     * @return
     */
    List<T> findAll();
    List<T> findById(int uid);


    /***
     * 使用注解延时加载
     * 一对多
     * @param
     */

    @Select("select * from account")
    @Results({
            @Result( id = true , property = "accountId" , column = "accountId"),

            //原理与配置文件一样
            @Result ( property = "user", column = "uid" ,javaType = User.class,
                    one = @One(
                            select = "dao.IUserDao.findById",
                            fetchType = FetchType.LAZY
                    )
            )
    })
    List<Account> findAllAnno_01();
    Account findByIdAnno(int id);






}
