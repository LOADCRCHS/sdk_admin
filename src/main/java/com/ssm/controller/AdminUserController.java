package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.pojo.AdminUserTO;
import com.ssm.pojo.RoleTO;
import com.ssm.pojo.TableData;
import com.ssm.service.AdminUserService;
import com.ssm.service.RoleService;
import com.ssm.util.AdminConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/adminuser.html")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(params = "act=page")
    public String page() {
        return "admin_user";
    }

    @RequestMapping(params = "act=list")
    @ResponseBody
    public TableData userList(AdminUserTO criteria, @RequestParam(name = "page") Integer pageNum, @RequestParam(name = "limit") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize > 30) {
            pageSize = 10;
        }
        TableData data = new TableData();
        PageInfo<AdminUserTO> users = adminUserService.getUserList(criteria, pageNum, pageSize);
        data.setCode(0);
        data.setCount(users.getTotal());
        data.setData(users.getList());
        return data;
    }

    @RequestMapping(params = "act=role_list")
    @ResponseBody
    private List<RoleTO> roleList() {
        RoleTO roleTO = new RoleTO();
        roleTO.setStatus(AdminConstants.ROLE_SATUS_VALID);
        return roleService.getRoleList(roleTO);
    }

    @RequestMapping(params = "act=user_role")
    @ResponseBody
    public List<Integer> userRole(Integer userId) {
        return adminUserService.getUserRoleByUserId(userId);
    }

    @RequestMapping(params = "act=assign")
    @ResponseBody
    public Map<String, Object> assignRole(Integer userId, Integer[] roleIds) {
        Map<String, Object> result = new HashMap<>();
        try {
            adminUserService.addPermission(userId, roleIds);
            result.put("status", true);
        } catch (Exception e) {
            result.put("status", false);
            result.put("message", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(params = "act=edit")
    @ResponseBody
    public Map<String, Object> edit(AdminUserTO adminUserTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (adminUserTO.getId() == null) {
                adminUserService.addAdminUser(adminUserTO);
            } else {
                adminUserService.updateAdminUser(adminUserTO);
            }
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(params = "act=del")
    @ResponseBody
    public Map<String, Object> delete(Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            adminUserService.deleteUserAdmin(id);
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
