package com.ssm.dao.core;


import com.ssm.sdk.common.pojo.ChannelTO;

import java.util.List;

public interface ChannelDao {

    List<ChannelTO> getAllChannels();
}
