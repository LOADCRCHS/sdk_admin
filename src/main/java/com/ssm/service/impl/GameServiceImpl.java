package com.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.dao.core.CPDao;
import com.ssm.dao.core.GameDao;
import com.ssm.sdk.common.pojo.CPTO;
import com.ssm.sdk.common.pojo.GameTO;
import com.ssm.sdk.common.util.DigestUtils;
import com.ssm.service.GameService;
import com.ssm.util.AdminConstants;
import com.ssm.util.SecretKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private CPDao cpDao;
    @Autowired
    private GameDao gameDao;

    @Override
    public void addCPTO(CPTO cpto) {
        String secretKey = SecretKeyUtil.getSecretKey();
        String nkey = SecretKeyUtil.getSecretKey();
        String pwd = SecretKeyUtil.getSecretKey();
        cpto.setSecretKey(secretKey);
        cpto.setNoticeKey(nkey);
        cpto.setPassword(pwd);
        cpDao.addCP(cpto);
    }

    @Override
    public void updateCPTO(CPTO cpto) {
        cpDao.updateCP(cpto);
    }

    @Override
    public void delCPTO(int id) {
        cpDao.delCP(id);
    }

    @Override
    public PageInfo<CPTO> selCPLIst(CPTO cpto, Integer pageNum, Integer limit) {
        PageHelper.startPage(pageNum, limit);
        return new PageInfo<>(cpDao.getAllCPList(cpto));
    }

    @Override
    public PageInfo<GameTO> getAllGameList(GameTO gameTO, Integer pageNum, Integer limit) {
        PageHelper.startPage(pageNum, limit);
        return new PageInfo<>(gameDao.getAllGameList(gameTO));
    }
}
