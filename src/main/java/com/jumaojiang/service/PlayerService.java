package com.jumaojiang.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumaojiang.mapper.PlayerMapper;
import com.jumaojiang.pojo.Player;
import com.jumaojiang.pojo.PlayerExample;
import com.jumaojiang.pojo.Team;
import com.jumaojiang.vo.PlayerQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ssm_api
 *
 * @author wuhanwei
 * @version 1.0
 * @date 2021/10/23
 */
@Service
public class PlayerService {

    @Autowired
    private PlayerMapper playerMapper;

    /**
     * 球员多条件分页查询
     * @param pageNum
     * @param pageSize
     * @param playerQueryVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageInfo queryPlayerVo(Integer pageNum, Integer pageSize, PlayerQueryVo playerQueryVo){
        // 创建多条件查询对象
        PlayerExample playerExample = new PlayerExample();
        PlayerExample.Criteria criteria = playerExample.createCriteria();
        // 添加多条件查询
        if(playerQueryVo.getPlayerName()!=null && !"".equals(playerQueryVo.getPlayerName())){
            criteria.andPlayerNameLike("%" + playerQueryVo.getPlayerName() + "%");
        }
        if(playerQueryVo.getPlayerNum()!=null){
            criteria.andPlayerNumEqualTo(playerQueryVo.getPlayerNum());
        }
        if(playerQueryVo.getLocation()!=null && !"".equals(playerQueryVo.getLocation())){
            criteria.andLocationLike("%" + playerQueryVo.getLocation() + "%");
        }
        if(playerQueryVo.getNationality()!=null && !"".equals(playerQueryVo.getNationality())){
            criteria.andNationalityLike("%" + playerQueryVo.getNationality() + "%");
        }
        if(playerQueryVo.getBeginDate()!=null){
            criteria.andBirthdayGreaterThanOrEqualTo(playerQueryVo.getBeginDate());
        }
        if(playerQueryVo.getEndDate()!=null){
            criteria.andBirthdayLessThanOrEqualTo(playerQueryVo.getEndDate());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Player> players = playerMapper.selectByExample(playerExample);
        return new PageInfo(players);
    }

    /**
     * 根据主键查询球员
     * @param playerId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Player queryByPlayerId(Integer playerId){
        return playerMapper.selectByPrimaryKey(playerId);
    }


    /**
     * 根据主键更新
     * @param player
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Integer updateByPlayerId(Player player){
        return playerMapper.updateByPrimaryKeySelective(player);
    }

    /**
     * 根据主键逻辑删除
     * @param playerId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Integer deleteByPlayerId(Integer playerId){
        Player player = new Player();
        player.setPlayerId(playerId);
        player.setIsDel(1);
        return playerMapper.updateByPrimaryKeySelective(player);
    }

    /**
     * 添加一个球员信息
     * @param player
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Integer insertPlayer(Player player){
        return playerMapper.insertSelective(player);
    }

}
