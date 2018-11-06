package com.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.dao.AdminUserDao;
import com.ssm.dao.PermissionDao;
import com.ssm.pojo.AdminUserTO;
import com.ssm.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public PageInfo<AdminUserTO> getUserList(AdminUserTO criteria, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(adminUserDao.getUserList(criteria));
    }

    @Override
    public List<Integer> getUserRoleByUserId(Integer userId) {
        return permissionDao.getUserRoleByUserId(userId);
    }

    @Override
    public void addPermission(Integer userId, Integer[] roleIds) {
        permissionDao.deleteUserRoleByUserId(userId);
        if (roleIds != null) {
            for (Integer roleId : roleIds) {
                permissionDao.addUserRole(userId, roleId);
            }
        }
    }

    @Override
    public void deleteUserAdmin(Integer id) {
        //todo ...
    }

    @Override
    public void addAdminUser(AdminUserTO adminUserTO) {
        //todo ...
    }

    @Override
    public void updateAdminUser(AdminUserTO adminUserTO) {
        //todo ...
    }
}
