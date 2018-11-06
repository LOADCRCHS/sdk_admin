package com.ssm.controller;

import com.ssm.pojo.MenuTO;
import com.ssm.pojo.RoleTO;
import com.ssm.pojo.TableData;
import com.ssm.service.MenuService;
import com.ssm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role.html")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @RequestMapping(params = "act=page")
    public String rolePage() {
        return "role";
    }

    @RequestMapping(params = "act=list")
    @ResponseBody
    public TableData roleList(RoleTO role) {
        TableData data = new TableData();
        List<RoleTO> roles = roleService.getRoleList(role);
        data.setCode(0);
        data.setCount(roles.size());
        data.setData(roles);
        return data;
    }

    @RequestMapping(params = "act=edit")
    @ResponseBody
    public Map<String, Object> edit(RoleTO role) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (role.getId() == null) {
                roleService.addRole(role);
            } else {
                roleService.updateRole(role);
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
            roleService.deleteRole(id);
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(params = "act=role_menu")
    @ResponseBody
    public List<Integer> roleMenu(Integer roleId) {
        return roleService.getRolePermissionByRoleId(roleId);
    }

    @RequestMapping(params = "act=assign")
    @ResponseBody
    public Map<String, Object> assignMenu(Integer roleId, Integer[] menuIds) {
        Map<String, Object> result = new HashMap<>();
        try {
            roleService.addPermission(roleId, menuIds);
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(params = "act=menu_tree")
    @ResponseBody
    public List<MenuTO> menuTree() {
        return menuService.getTreeMenu();
    }
}
