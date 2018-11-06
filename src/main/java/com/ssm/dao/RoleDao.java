package com.ssm.dao;


import com.ssm.pojo.RoleTO;

import java.util.List;

public interface RoleDao {

    void addRole(RoleTO role);

    void updateRole(RoleTO role);

    RoleTO getRoleById(Integer id);

    List<RoleTO> getRoleList(RoleTO role);

}
