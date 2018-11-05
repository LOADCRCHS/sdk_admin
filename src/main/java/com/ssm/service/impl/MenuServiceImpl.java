package com.ssm.service.impl;

import com.ssm.dao.MenuDao;
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

    @Override
    public List<MenuTO> getTreeMenu() {
        //todo
        List<MenuTO> menus = menuDao.getAllMenu();
        List<MenuTO> firstMenus = new ArrayList<>();
        Map<Integer, MenuTO> menuMap = new HashMap<>();
        for (MenuTO menu : menus) {
            menu.getAttributes().put("url", menu.getUrl());
            menu.getAttributes().put("parentId", menu.getParentId());
            menuMap.put(menu.getId(), menu);
            if (menu.getParentId() == 1) {
                firstMenus.add(menu);
            }
        }
        for (MenuTO menu : menus) {
            if (menu.getParentId() > 1) {
                MenuTO parentMenu = menuMap.get(menu.getParentId());
                if (parentMenu != null) {
                    parentMenu.getChildren().add(menu);
                }
            }
        }
        return firstMenus;
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
