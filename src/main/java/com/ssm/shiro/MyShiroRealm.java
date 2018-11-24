package com.ssm.shiro;

import com.ssm.dao.admin.AdminUserDao;
import com.ssm.dao.admin.PermissionDao;
import com.ssm.pojo.AdminUserTO;
import com.ssm.sdk.common.util.DigestUtils;
import com.ssm.util.AdminConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {


    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private AdminUserDao adminUserDao;

    /**
     * 每次访问url的时候调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("---doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getPrincipal();
        List<Integer> roleIds = permissionDao.getUserRoleByUserId(userId);
        if (roleIds != null && roleIds.size() > 0) {
            for (Integer roleId : roleIds) {
                info.addRole(roleId.toString());
            }
        }
        return info;
    }

    /**
     * 登陆的时候调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("---doGetAuthenticationInfo");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String email = token.getUsername();
        String password = new String(token.getPassword());
        AdminUserTO user = adminUserDao.getUserByEmail(email);
        if (user == null || !DigestUtils.getMD5(password + AdminConstants.PASSWORD_SALT_KEY).equals(user.getPassword())) {
            return null;
        }
        //主键，密码，数据源名
        return new SimpleAuthenticationInfo(user.getId(), password, super.getName());
    }
}
