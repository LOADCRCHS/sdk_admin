package com.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.dao.admin.AdminUserDao;
import com.ssm.dao.admin.PermissionDao;
import com.ssm.pojo.AdminUserTO;
import com.ssm.sdk.common.util.DigestUtils;
import com.ssm.service.AdminUserService;
import com.ssm.util.AdminConstants;
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
        adminUserDao.deleteUserAdmin(id);
        //todo ...
    }

    @Override
    public void addAdminUser(AdminUserTO adminUserTO) {
        String password_md5 = DigestUtils.getMD5(adminUserTO.getPassword() + AdminConstants.PASSWORD_SALT_KEY);
        adminUserTO.setPassword(password_md5);
        adminUserDao.addAdminUser(adminUserTO);
    }

    @Override
    public void updateAdminUser(AdminUserTO adminUserTO) {
        if(adminUserTO.getPassword()!=null){
            String password_md5 = DigestUtils.getMD5(adminUserTO.getPassword() + AdminConstants.PASSWORD_SALT_KEY);
            adminUserTO.setPassword(password_md5);
        }
        adminUserDao.updateAdminUser(adminUserTO);
    }

    @Override
    public AdminUserTO doLogin(String email, String password) {
        password = DigestUtils.getMD5(password + AdminConstants.PASSWORD_SALT_KEY);
        return adminUserDao.getUserByEmailAndPwd(email, password);
    }
}
