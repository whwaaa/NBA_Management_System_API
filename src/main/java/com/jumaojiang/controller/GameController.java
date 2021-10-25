package com.jumaojiang.controller;

import com.github.pagehelper.PageInfo;
import com.jumaojiang.pojo.Game;
import com.jumaojiang.pojo.Team;
import com.jumaojiang.service.GameService;
import com.jumaojiang.vo.AjaxResultVo;
import com.jumaojiang.vo.GameQueryVo;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * ssm_api
 *
 * @author wuhanwei
 * @version 1.0
 * @date 2021/10/24
 */
@Controller
@RequestMapping("game")
public class GameController {

    @Autowired
    private GameService gameService;

    // 多条件分页查询
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public AjaxResultVo queryList(@RequestParam("pageNum") Integer pageNum,
                                  @RequestParam("pageSize") Integer pageSize,
                                  GameQueryVo gameQueryVo){
        if(pageNum == null || pageNum < 0){
            pageNum = 1;
        }
        if(pageSize == null || pageSize < 0){
            pageSize = 5;
        }
        PageInfo gameList = gameService.getGameList(pageNum, pageSize, gameQueryVo);
        if(gameList != null){
            return new AjaxResultVo(200, "ok", null, gameList);
        }
        return new AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
    }

    // 根据主键查询
    @ResponseBody
    @RequestMapping(value = "{gameId}", method = RequestMethod.GET)
    public AjaxResultVo queryBygameId(@PathVariable("gameId") Integer gameId){
        Game game = gameService.queryByGameId(gameId);
        if(game != null){
            return new AjaxResultVo(200, "ok", game, null);
        }
        return new AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
    }

    // 根据主键更新
    @ResponseBody
    @RequestMapping(value = "{gameId}", method = RequestMethod.PUT)
    public AjaxResultVo upateByGameId(@PathVariable("gameId") Integer gameId, Game game){
        game.setGameId(gameId);
        Integer res = gameService.updateByTeamId(game);
        if(res != null){
            return new AjaxResultVo();
        }
        return new AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
    }

    // 根据主键删除
    @ResponseBody
    @RequestMapping(value = "{gameIdList}", method = RequestMethod.DELETE)
    public AjaxResultVo deleteByGameId(@PathVariable("gameIdList") String gameIdList){
        String[] split = gameIdList.split("_");
        for (String s : split) {
            Integer geamId = Integer.parseInt(s);
            Integer res = gameService.deleteByGameId(geamId);
            if(res == 0){
                return new AjaxResultVo(500, "服务器内部异常，请稍后再试！");
            }
        }
        return new AjaxResultVo();
    }

    // 添加赛程数据
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public AjaxResultVo insertGame(Game game){
        Integer res = gameService.insertGame(game);
        if(res > 0){
            return new AjaxResultVo();
        }
        return new AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
    }

}
