package com.ssm.shiro;

import com.ssm.dao.admin.MenuDao;
import com.ssm.dao.admin.PermissionDao;
import com.ssm.pojo.MenuTO;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdminShiroFilterFactoryBean extends ShiroFilterFactoryBean {
    private static final String ROLE_STRING = "roles[{0}]";
    private static String filterChainDefinitions;
    @Autowired
    private MenuDao menuDAO;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public void setFilterChainDefinitions(String definitions) {
        //在项目启动时调用
        System.out.println("========AdminShiroFilterFactoryBean setFilterChainDefinitions========");
        filterChainDefinitions = definitions;
        Ini ini = new Ini();
        ini.load(definitions);
        Ini.Section section = ini.getSection("urls");
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection("");
        }
        List<MenuTO> menuList = menuDAO.getAllMenu();

        for (MenuTO menuTO : menuList) {
            if (!StringUtils.isEmpty(menuTO.getUrl())) {
                List<Integer> roleIds = permissionDao.getRoleIdByMenuId(menuTO.getId());
                StringBuilder stringBuilder = new StringBuilder();
                if (roleIds != null && roleIds.size() > 0) {
                    for (Integer roleId : roleIds) {
                        stringBuilder.append(roleId).append(",");
                    }
                    String ids = stringBuilder.substring(0, stringBuilder.length() - 1);
                    String url = menuTO.getUrl();
                    if (url.contains("?")) {
                        section.put(url.substring(0, url.indexOf("?")), MessageFormat.format(ROLE_STRING, ids));
                    } else {
                        section.put(url, MessageFormat.format(ROLE_STRING, ids));
                    }
                }
            }
        }
        Set<String> keySet = section.keySet();
        for (String key : keySet) {
            System.out.println("=="+key+" - "+section.get(key));
        }
        section.put("/**", "authc");//其他url必须都登陆，这句一定在最后
        this.setFilterChainDefinitionMap(section);

    }

    //todo 待理解
    public synchronized void update() {
        System.out.println("=======AdminShiroFilterFactoryBean update======");
        try {
            AbstractShiroFilter shiroFilter = (AbstractShiroFilter) this.getObject();
            PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            // 过滤管理器
            DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
            // 清除权限配置
            manager.getFilterChains().clear();
            this.getFilterChainDefinitionMap().clear();

            // 重新设置权限
            this.setFilterChainDefinitions(filterChainDefinitions);


            Map<String, String> chains = this.getFilterChainDefinitionMap();
            if (!CollectionUtils.isEmpty(chains)) {
                Iterator var12 = chains.entrySet().iterator();

                while (var12.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry) var12.next();
                    String url = (String) entry.getKey();
                    String chainDefinition = (String) entry.getValue();
                    manager.createChain(url, chainDefinition);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
