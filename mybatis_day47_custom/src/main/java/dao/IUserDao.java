package dao;

import java.util.List;

public interface IUserDao<T> {
    /**
     * 查询全部用户
     */
    List<T> findAll();
}
