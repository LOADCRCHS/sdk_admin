package com.ssm.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AdminShiroFilter extends AuthorizationFilter {

    /**
     *
     * @param o mapValue
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        System.out.println("========AdminShiroFilter isAccessAllowed");
        String[] roleArray = (String[]) o;
        if (roleArray == null || roleArray.length == 0) {
            return false;
        }
        Subject subject = SecurityUtils.getSubject();
        for (String s : roleArray) {
            if (subject.hasRole(s)) {
                return true;
            }
        }
        return false;
    }
}
