/*
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
public class MenuController2 {

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
    @ResponseBody
    public Map<String, Object> goEdit(Integer id, HttpServletRequest request) {
        if (id == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("menuTO",menuService.getMenuById(id));
        map.put("menuTree",menuService.getTreeMenu());
        return map;
    }

}
*/
