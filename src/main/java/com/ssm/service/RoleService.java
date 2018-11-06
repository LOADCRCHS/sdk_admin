package com.ssm.service;

import com.ssm.pojo.AdminUserTO;
import com.ssm.pojo.RoleTO;

import java.util.List;

public interface RoleService {
    void addRole(RoleTO role);

    void updateRole(RoleTO role);

    void deleteRole(Integer id);

    RoleTO getRoleById(Integer id);

    List<RoleTO> getRoleList(RoleTO role);

    List<Integer> getRolePermissionByRoleId(Integer roleId);

    void addPermission(Integer roleId, Integer[] menuIds);

}
