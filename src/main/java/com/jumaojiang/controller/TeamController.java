package com.jumaojiang.controller;

import com.github.pagehelper.PageInfo;
import com.jumaojiang.pojo.Team;
import com.jumaojiang.service.TeamService;
import com.jumaojiang.vo.AjaxResultVo;
import com.jumaojiang.vo.TeamQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * ssm_api
 *
 * @author wuhanwei
 * @version 1.0
 * @date 2021/10/23
 */
@Controller
@RequestMapping("team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    // 多条件分页查询
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public AjaxResultVo selectTeam(Integer pageNum, Integer pageSize, TeamQueryVo teamQueryVo){
        if(pageNum == null || pageNum < 0){
            pageNum = 1;
        }
        if(pageSize == null || pageSize < 0){
            pageSize = 5;
        }
        PageInfo pageInfo = teamService.selectTeam(pageNum, pageSize, teamQueryVo);
        return new AjaxResultVo<>(pageInfo);
    }

    // 根据主键更新
    @ResponseBody
    @RequestMapping(value = "{teamId}", method = RequestMethod.PUT)
    public AjaxResultVo updateByTeamId(@PathVariable("teamId") Integer teamId, Team team){
        team.setTeamId(teamId);
        Integer res = teamService.updateByTeamId(team);
        if(res > 0){
            return new AjaxResultVo();
        }
        return new AjaxResultVo(500, "服务器内部异常，请稍后再试！");
    }

    // 根据主键查询
    @ResponseBody
    @RequestMapping(value = "{teamId}", method = RequestMethod.GET)
    public AjaxResultVo queryByTeamId(@PathVariable("teamId") Integer teamId){
        Team team = teamService.queryByTeamId(teamId);
        if(team != null){
            return new AjaxResultVo(200, "ok", team, null);
        }
        return new AjaxResultVo(500, "服务器内部异常，请稍后再试！");
    }

    // 根据主键逻辑删除
    @ResponseBody
    @RequestMapping(value = "{teamIdList}", method = RequestMethod.DELETE)
    public AjaxResultVo deleteByTeamId(@PathVariable("teamIdList") String teamIdList){
        String[] split = teamIdList.split("_");
        for (String s : split) {
            Integer teamId = Integer.parseInt(s);
            Integer res = teamService.deleteByTeamId(teamId);
            if(res == 0){
                return new AjaxResultVo(500, "服务器内部异常，请稍后再试！");
            }
        }
        return new AjaxResultVo();
    }

    // 增加一条数据
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public AjaxResultVo insertTeam(Team team){
        Integer res = teamService.insertTeam(team);
        if(res > 0){
            return new AjaxResultVo();
        }
        return new AjaxResultVo(500, "服务器内部异常，请稍后再试！");
    }

    // 上传文件
    @ResponseBody
    @RequestMapping(value = "logo/{teamId}")
    public AjaxResultVo upload(@PathVariable(value = "teamId", required = false) Integer teamId,
                               @RequestParam(value = "logo", required = false) MultipartFile file,
                               HttpServletRequest request){
        // 存储文件
        String fileSavePath = request.getServletContext().getRealPath("/img/uploadFile/");
        String fileName = teamService.uploadLogo(fileSavePath, file);
        if(fileName == null){
            // 储存失败
            return new AjaxResultVo(500, "服务器内部异常，请稍后再试！");
        }
        // 更新数据库teamLogo
        Team team = new Team();
        team.setTeamId(teamId);
        team.setTeamLogo(fileName);
        Integer res = teamService.updateByTeamId(team);
        if(res > 0){
            return new AjaxResultVo(200, "ok", fileName, null);
        }
        return new AjaxResultVo(500, "服务器内部异常，请稍后再试！");
    }

}
