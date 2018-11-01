package com.ssm.dao;

import com.ssm.pojo.MenuTO;

import java.util.List;

public interface MenuDao {

    List<MenuTO> getAllMenu();

    void addMenu(MenuTO menu);

    void updateMenu(MenuTO menu);

    void deleteMenu(Integer id);

    void updateParentId(Integer parentId);

    MenuTO getMenuById(Integer id);
}
