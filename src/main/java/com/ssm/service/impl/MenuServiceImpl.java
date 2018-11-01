package com.ssm.service.impl;

import com.ssm.dao.MenuDao;
import com.ssm.pojo.MenuTO;
import com.ssm.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<MenuTO> getTreeMenu() {
        //todo
        return null;
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
