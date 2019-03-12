package com.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * @Transactional
 * 1. 作用
 *    使用spring的声明式事务注解。
 * 2. 范围
 *    定义在类上，表示当前类的所有方法都应用事务。
 *    定义在方法上，表示当前方法应用事务
 *    定义在接口上，表示所有实现该接口的实现类实现了接口的方法，这些方法都会应用事务。
 *    定义在接口方法上，所有实现了此方法的实现类运行方法会自动应用事务。
 * 3. 注解属性
 *      propagation  指定传播行为，默认值为REQUIRED
 *      readOnly     默认值为false，表示支持读写操作
 *      noRollbackFor 遇到指定的异常不回滚
 */
@Service
@Transactional(
        readOnly = false,
        propagation = Propagation.REQUIRED
)
public interface IUserDao {

    void insert();
}
