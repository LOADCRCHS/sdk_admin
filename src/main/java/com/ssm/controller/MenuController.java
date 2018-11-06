package com.ssm.controller;

import com.ssm.pojo.MenuTO;
import com.ssm.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("menu.html")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(params = "act=page")
    public String menuPage() {
        return "menu";
    }

    @RequestMapping(params = "act=tree")
    @ResponseBody
    public List<MenuTO> menuTree() {
        return menuService.getTreeMenu();
    }

    @RequestMapping(params = "act=go_edit")
    public String goEdit(Integer id, HttpServletRequest request) {
        if (id != null) {
            MenuTO menu = menuService.getMenuById(id);
            request.setAttribute("menu", menu);
        }
        return "menu_edit";
    }

    @RequestMapping(params = "act=edit")
    @ResponseBody
    public Map<String, Object> edit(MenuTO menu) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (menu.getId() == null) {
                menuService.addMenu(menu);
            } else {
                menuService.updateMenu(menu);
            }

            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(params = "act=delete")
    @ResponseBody
    public Map<String, Object> delete(Integer[] ids) {
        Map<String, Object> result = new HashMap<>();
        try {
            menuService.deleteMenus(ids);
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
