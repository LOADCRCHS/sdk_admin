package com.ssm.service.impl;

import com.ssm.dao.PermissionDao;
import com.ssm.dao.RoleDao;
import com.ssm.pojo.RoleTO;
import com.ssm.service.RoleService;
import com.ssm.util.AdminConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;


    @Override
    public void addRole(RoleTO role) {
        //todo 加上检验角色名不能重复的操作
        roleDao.addRole(role);
    }

    @Override
    public void updateRole(RoleTO role) {
        roleDao.updateRole(role);
    }

    @Override
    public void deleteRole(Integer id) {
        RoleTO roleTO = new RoleTO();
        roleTO.setId(id);
        roleTO.setStatus(AdminConstants.ROLE_SATUS_INVALID);
        roleDao.updateRole(roleTO);
    }

    @Override
    public RoleTO getRoleById(Integer id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public List<RoleTO> getRoleList(RoleTO role) {
        return roleDao.getRoleList(role);
    }

    @Override
    public List<Integer> getRolePermissionByRoleId(Integer roleId) {
        return permissionDao.getRolePermissionByRoleId(roleId);
    }

    @Override
    public void addPermission(Integer roleId, Integer[] menuIds) {
        permissionDao.deleteRolePermissionByRoleId(roleId);
        if (menuIds != null) {
            for (Integer menuId : menuIds) {
                permissionDao.addRolePermission(roleId, menuId);
            }
        }
    }
}
