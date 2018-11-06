package com.ssm.service;

import com.github.pagehelper.PageInfo;
import com.ssm.pojo.AdminUserTO;

import java.util.List;

public interface AdminUserService {
    PageInfo<AdminUserTO> getUserList(AdminUserTO criteria, Integer pageNum, Integer pageSize);

    List<Integer> getUserRoleByUserId(Integer userId);

    void addPermission(Integer userId, Integer[] roleIds);

    void deleteUserAdmin(Integer id);

    void addAdminUser(AdminUserTO adminUserTO);

    void updateAdminUser(AdminUserTO adminUserTO);
}
