package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.pojo.TableData;
import com.ssm.sdk.common.pojo.CPTO;
import com.ssm.sdk.common.pojo.GameTO;
import com.ssm.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/game/game.html")
public class GameController {
    @Autowired
    private GameService gameService;


    @RequestMapping(params = "act=page")
    public String page() {
        return "game";
    }

    @RequestMapping(params = "act=list")
    @ResponseBody
    public TableData list(GameTO criteria, @RequestParam(name = "page") Integer pageNum, @RequestParam(name = "limit") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize > 30) {
            pageSize = 10;
        }
        TableData data = new TableData();
        PageInfo<GameTO> gameTOPageInfo = gameService.getAllGameList(criteria, pageNum, pageSize);
        data.setCode(0);
        data.setCount(gameTOPageInfo.getTotal());
        data.setData(gameTOPageInfo.getList());
        return data;
    }
}
