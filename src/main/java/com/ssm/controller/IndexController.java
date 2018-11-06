package com.ssm.controller;

import com.ssm.pojo.MenuTO;
import com.ssm.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("index.html")
    public String index(HttpServletRequest request) {
        List<MenuTO> menus = menuService.getTreeMenu();
        request.setAttribute("menus", menus);
        return "index";
    }
}
