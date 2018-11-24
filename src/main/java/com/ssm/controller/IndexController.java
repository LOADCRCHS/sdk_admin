package com.ssm.controller;

import com.ssm.pojo.AdminUserTO;
import com.ssm.pojo.MenuTO;
import com.ssm.service.AdminUserService;
import com.ssm.service.MenuService;
import com.ssm.util.AdminConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/noauth/")
public class IndexController {
    @Autowired
    private MenuService menuService;

    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping("index.html")
    public String index(HttpServletRequest request) {
//        List<MenuTO> menus = menuService.getTreeMenu();
//        request.setAttribute("menus", menus);
        return "index";
    }

    @RequestMapping("gologin.html")
    public String gologin() {
        return "login";
    }

    @RequestMapping("login.html")
    public String login(String password, String email, HttpServletRequest request, HttpSession session) {
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);
        Subject subject = SecurityUtils.getSubject();

        try {
            //使用shiro进行登陆
            subject.login(token);
            Integer userId = (Integer) subject.getPrincipal();
            List<MenuTO> treeMenu = menuService.getTreeMenu(userId);
            session.setAttribute(AdminConstants.SESSION_MENU, treeMenu);
            return "redirect:index.html";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            request.setAttribute("message", "用户名或密码错误");
            return "login";
        }

        /*AdminUserTO user = adminUserService.doLogin(email, password);
        if (user == null) {
            request.setAttribute("message", "用户名或密码错误");
            return "login";
        } else {
            List<MenuTO> menus = menuService.getTreeMenu(user.getId());
            session.setAttribute(AdminConstants.SESSION_USER, user);
            session.setAttribute(AdminConstants.SESSION_MENU, menus);
            return "redirect:index.html";
        }*/
    }

    @RequestMapping("logout.html")
    public String logout(HttpSession session) {
        // session.invalidate();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    @RequestMapping("auth_error.html")
    public String error() {
        return "auth_error";
    }
}
