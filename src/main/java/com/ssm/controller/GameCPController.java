package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.pojo.AdminUserTO;
import com.ssm.pojo.TableData;
import com.ssm.sdk.common.pojo.CPTO;
import com.ssm.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/game/game_cp.html")
public class GameCPController {
    @Autowired
    private GameService gameService;


    @RequestMapping(params = "act=page")
    public String page() {
        return "game_cp";
    }

    @RequestMapping(params = "act=list")
    @ResponseBody
    public TableData list(CPTO criteria, @RequestParam(name = "page") Integer pageNum, @RequestParam(name = "limit") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize > 30) {
            pageSize = 10;
        }
        TableData data = new TableData();
        PageInfo<CPTO> cptoPageInfo = gameService.selCPLIst(criteria, pageNum, pageSize);
        data.setCode(0);
        data.setCount(cptoPageInfo.getTotal());
        data.setData(cptoPageInfo.getList());
        return data;
    }

    @RequestMapping(params = "act=edit")
    @ResponseBody
    public Map<String, Object> edit(CPTO cpto) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (cpto.getId() == null) {
                gameService.addCPTO(cpto);
            } else {
                gameService.updateCPTO(cpto);
            }
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    @RequestMapping(params = "act=del")
    @ResponseBody
    public Map<String, Object> delete(Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            gameService.delCPTO(id);
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
