package com.ssm.dao;

import com.ssm.pojo.AdminUserTO;

import java.util.List;

public interface AdminUserDao {
    List<AdminUserTO> getUserList(AdminUserTO criteria);
}
