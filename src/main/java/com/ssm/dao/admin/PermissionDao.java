package com.ssm.dao.admin;

import com.ssm.pojo.MenuTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionDao {

    void deleteRolePermissionByRoleId(Integer roleId);

    void addRolePermission(@Param(value = "roleId") Integer roleId, @Param(value = "menuId") Integer menuId);

    void addUserRole(@Param(value = "userId") Integer userId, @Param(value = "roleId") Integer roleId);

    List<Integer> getRolePermissionByRoleId(Integer roleId);

    List<Integer> getUserRoleByUserId(Integer userId);

    void deleteUserRoleByUserId(Integer userId);

    List<MenuTO> getUserMenu(Integer id);

    List<Integer> getRoleIdByMenuId(Integer id);
}
