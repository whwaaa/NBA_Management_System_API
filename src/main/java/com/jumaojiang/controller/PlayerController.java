package com.jumaojiang.controller;

import com.github.pagehelper.PageInfo;
import com.jumaojiang.pojo.Player;
import com.jumaojiang.service.PlayerService;
import com.jumaojiang.vo.AjaxResultVo;
import com.jumaojiang.vo.PlayerQueryVo;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * ssm_api
 *
 * @author wuhanwei
 * @version 1.0
 * @date 2021/10/23
 */
@Controller
@RequestMapping("player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    // 多条件查询球员
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public AjaxResultVo queryPlayerVo(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      PlayerQueryVo playerQueryVo){
        if(pageNum == null || pageNum < 0){
            pageNum = 1;
        }
        if(pageSize == null || pageSize < 0){
            pageSize = 5;
        }
        PageInfo pageInfo = playerService.queryPlayerVo(pageNum, pageSize, playerQueryVo);
        if(pageInfo != null){
            return new AjaxResultVo(200, "ok", null, pageInfo);
        }
        return new AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
    }

    // 根据主键查询球员
    @ResponseBody
    @RequestMapping(value = "{playerId}", method = RequestMethod.GET)
    public AjaxResultVo queryByPlayerId(@PathVariable("playerId") Integer playerId){
        Player player = playerService.queryByPlayerId(playerId);
        if(player != null){
            return new AjaxResultVo(200, "ok", player, null);
        }
        return new AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
    }

    // 根据主键更新球员
    @ResponseBody
    @RequestMapping(value = "{playerId}", method = RequestMethod.PUT)
    public AjaxResultVo updateByPlayerId(@PathVariable("playerId") Integer playerId, Player player){
        player.setPlayerId(playerId);
        Integer res = playerService.updateByPlayerId(player);
        if(res > 0){
            return new AjaxResultVo();
        }
        return new  AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
    }


    // 根据主键逻辑删除
    @ResponseBody
    @RequestMapping(value = "{playerIdList}", method = RequestMethod.DELETE)
    public AjaxResultVo deleteByPlayerId(@PathVariable("playerIdList") String playerIdList){
        String[] split = playerIdList.split("_");
        for (String s : split) {
            Integer playerId = Integer.parseInt(s);
            Integer res = playerService.deleteByPlayerId(playerId);
            if(res == 0){
                return new AjaxResultVo(500, "服务器内部异常，请稍后再试！");
            }
        }
        return new AjaxResultVo();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public AjaxResultVo insertPlayer(Player player){
        Integer res = playerService.insertPlayer(player);
        if(res > 0){
            return new AjaxResultVo();
        }
        return new  AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
    }
}























