package com.ssm.dao.admin;

import com.ssm.pojo.AdminUserTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminUserDao {
    List<AdminUserTO> getUserList(AdminUserTO criteria);

    AdminUserTO getUserByEmail(String email);

    void addAdminUser(AdminUserTO adminUserTO);

    void updateAdminUser(AdminUserTO adminUserTO);

    AdminUserTO getUserByEmailAndPwd(@Param(value = "email") String email,
                                     @Param(value = "password") String password);


    void deleteUserAdmin(Integer id);
}
