package com.ssm.service.impl;

import com.ssm.dao.admin.MenuDao;
import com.ssm.dao.admin.PermissionDao;
import com.ssm.pojo.MenuTO;
import com.ssm.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<MenuTO> getTreeMenu() {
        List<MenuTO> menus = menuDao.getAllMenu();
        return makeMenuTree(menus);
    }

    @Override
    public List<MenuTO> getTreeMenu(Integer id) {
        List<MenuTO> menus = permissionDao.getUserMenu(id);
        return makeMenuTree(menus);
    }

    private List<MenuTO> makeMenuTree(List<MenuTO> menus) {
        List<MenuTO> firstMenu = new ArrayList<>();//一级菜单
        Map<Integer, MenuTO> menuMap = new HashMap<>();
        for (MenuTO menu : menus) {
            menu.getAttributes().put("url", menu.getUrl());
            menu.getAttributes().put("parentId", menu.getParentId());
            menuMap.put(menu.getId(), menu);

            if (menu.getParentId() != null && menu.getParentId() == 1) {
                firstMenu.add(menu);
            }
        }
        for (MenuTO menu : menus) {//组装树形菜单
            if (menu.getParentId() != null && menu.getParentId() > 1) {
                MenuTO parent = menuMap.get(menu.getParentId());
                if (parent != null) {
                    parent.getChildren().add(menu);
                }
            }
        }

        return firstMenu;
    }

    @Override
    public void addMenu(MenuTO menu) {
        menuDao.addMenu(menu);
    }

    @Override
    public void updateMenu(MenuTO menu) {
        menuDao.updateMenu(menu);
    }

    @Override
    public MenuTO getMenuById(Integer id) {
        return menuDao.getMenuById(id);
    }

    @Override
    public void deleteMenus(Integer[] ids) {
        for (Integer id : ids) {
            menuDao.deleteMenu(id);
            menuDao.updateParentId(id);
        }
    }
}
