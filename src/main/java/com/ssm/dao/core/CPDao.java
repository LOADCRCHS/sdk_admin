package com.ssm.dao.core;


import com.ssm.sdk.common.pojo.CPTO;

import java.util.List;

public interface CPDao {

    List<CPTO> getAllCPList(CPTO cpto);

    void addCP(CPTO cpto);

    void updateCP(CPTO cpto);

    void delCP(int id);
}
