package com.ssm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ssm.sdk.common.pojo.CPTO;
import com.ssm.sdk.common.pojo.GameTO;

public interface GameService {

    void addCPTO(CPTO cpto);

    void updateCPTO(CPTO cpto);

    void delCPTO(int id);

    PageInfo<CPTO> selCPLIst(CPTO cpto, Integer pageNum, Integer limit);

    PageInfo<GameTO> getAllGameList(GameTO gameTO, Integer pageNum, Integer limit);

}
