package com.ssm.service;


import com.ssm.pojo.MenuTO;

import java.util.List;

public interface MenuService {

    List<MenuTO> getTreeMenu();

    void addMenu(MenuTO menu);

    void updateMenu(MenuTO menu);

    MenuTO getMenuById(Integer id);

    void deleteMenus(Integer[] ids);
}
