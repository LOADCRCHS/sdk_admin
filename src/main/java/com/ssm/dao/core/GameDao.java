package com.ssm.dao.core;

import com.ssm.sdk.common.pojo.GameTO;

import java.util.List;
import java.util.Map;

public interface GameDao {
    List<GameTO> getAllGameList(GameTO gameTO);

    Integer getServerIdByGameAndSeq(Map param);
}
