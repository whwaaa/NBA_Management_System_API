package com.jumaojiang.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumaojiang.mapper.GameMapper;
import com.jumaojiang.mapper.GametypeMapper;
import com.jumaojiang.mapper.TeamMapper;
import com.jumaojiang.pojo.*;
import com.jumaojiang.utils.FileUtil;
import com.jumaojiang.vo.GameQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ssm_api
 *
 * @author wuhanwei
 * @version 1.0
 * @date 2021/10/24
 */
@Service
public class GameService {
    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private GametypeMapper gametypeMapper;

    /**
     * 多条件分页查询Game
     * @param pageNum
     * @param pageSize
     * @param gameQueryVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageInfo getGameList(Integer pageNum, Integer pageSize, GameQueryVo gameQueryVo){
        // 创建多条件查询对象
        GameExample gameExample = new GameExample();
        GameExample.Criteria criteria = gameExample.createCriteria();
        GameExample.Criteria criteria2 = gameExample.createCriteria();
        // 添加多条件
        if(gameQueryVo.getGameDateBegin() != null){
            criteria.andGameDateGreaterThanOrEqualTo(gameQueryVo.getGameDateBegin());
            criteria2.andGameDateGreaterThanOrEqualTo(gameQueryVo.getGameDateBegin());
        }
        if(gameQueryVo.getGameDateEnd() != null){
            criteria.andGameDateLessThanOrEqualTo(gameQueryVo.getGameDateEnd());
            criteria2.andGameDateLessThanOrEqualTo(gameQueryVo.getGameDateEnd());
        }
        if(gameQueryVo.getTypeId()!=null && gameQueryVo.getTypeId()>0){
            criteria.andTypeIdEqualTo(gameQueryVo.getTypeId());
            criteria2.andTypeIdEqualTo(gameQueryVo.getTypeId());
        }
        if(gameQueryVo.getChineseName()!=null && !"".equals(gameQueryVo.getChineseName())){
            // 先查询team中like查询条件的所有team
            TeamExample teamExample = new TeamExample();
            TeamExample.Criteria criteria1 = teamExample.createCriteria();
            criteria1.andChineseNameLike("%" + gameQueryVo.getChineseName() + "%");
            List<Team> teamList = teamMapper.selectByExample(teamExample);
            // 去除所有team的teamId封装进teamIdList
            List<Integer> teamIdList = new ArrayList<>();
            for (Team team : teamList) {
                teamIdList.add(team.getTeamId());
            }
            // 传入teamIdList进行查询
            criteria.andHomeTeamIdIn(teamIdList);
            criteria2.andVisitingTeamIdIn(teamIdList);
        }
        // 两个criteria对象或查询
        gameExample.or(criteria2);
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Game> games = gameMapper.selectByExample(gameExample);
        for (Game game : games) {
            // 查询homeTeamId的Team信息存入game
            Team homeTeam = teamMapper.selectByPrimaryKey(game.getHomeTeamId());
            game.setHomeTeam(homeTeam);
            // 查询visitingTeamId的Team信息存入game
            Team visitingTeam = teamMapper.selectByPrimaryKey(game.getVisitingTeamId());
            game.setVisitingTeam(visitingTeam);
            // 查询typeId的gameType信息存入game
            Gametype gameType = gametypeMapper.selectByPrimaryKey(game.getTypeId());
            game.setGameType(gameType);
        }
        return new PageInfo(games);
    }

    /**
     * 根据主键查询
     * @param gameId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Game queryByGameId(Integer gameId){
        Game game = gameMapper.selectByPrimaryKey(gameId);
        List<Team> teamList = teamMapper.selectByExample(null);
        game.setTeamList(teamList);
        return game;
    }

    /**
     * 根据主键更新
     * @param game
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Integer updateByTeamId(Game game){
        if(game.getHomeTeamId()!=null && game.getHomeTeamId()==-1){
            game.setHomeTeamId(null);
        }
        if(game.getVisitingTeamId()!=null && game.getVisitingTeamId()==-1){
            game.setVisitingTeamId(null);
        }
        if(game.getTypeId()!=null && game.getTypeId()==-1){
            game.setTypeId(null);
        }
        if(game.getStatus()!=null && game.getStatus()==-1){
            game.setStatus(null);
        }
        return gameMapper.updateByPrimaryKeySelective(game);
    }

    /**
     * 根据主键逻辑删除
     * @param gameId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Integer deleteByGameId(Integer gameId){
        Game game = new Game();
        game.setGameId(gameId);
        game.setIsDel(1);
        return gameMapper.updateByPrimaryKeySelective(game);
    }

    /**
     * 添加赛程数据
     * @param game
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Integer insertGame(Game game){
        return gameMapper.insertSelective(game);
    }
}














