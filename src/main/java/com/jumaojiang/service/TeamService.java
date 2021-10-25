package com.jumaojiang.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumaojiang.mapper.TeamMapper;
import com.jumaojiang.pojo.Team;
import com.jumaojiang.pojo.TeamExample;
import com.jumaojiang.utils.FileUtil;
import com.jumaojiang.vo.TeamQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * ssm_api
 *
 * @author wuhanwei
 * @version 1.0
 * @date 2021/10/23
 */
@Service
public class TeamService {

    @Autowired
    private TeamMapper teamMapper;

    /**
     * 多条件查询team
     * @param pageNum
     * @param pageSize
     * @param teamQueryVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageInfo selectTeam(int pageNum, int pageSize, TeamQueryVo teamQueryVo) {
        // 创建多条件查询对象
        TeamExample teamExample = new TeamExample();
        TeamExample.Criteria criteria = teamExample.createCriteria();
        // 添加条件
        if (teamQueryVo.getTeamName() != null && !"".equals(teamQueryVo.getTeamName().trim())) {
            criteria.andTeamNameLike("%" + teamQueryVo.getTeamName() + "%");
        }
        if (teamQueryVo.getChineseName() != null && !"".equals(teamQueryVo.getChineseName().trim())) {
            criteria.andChineseNameLike("%" + teamQueryVo.getChineseName() + "%");
        }
        if (teamQueryVo.getCoach() != null && !"".equals(teamQueryVo.getCoach().trim())) {
            criteria.andCoachLike("%" + teamQueryVo.getCoach() + "%");
        }
        if (teamQueryVo.getBeginDate() != null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(teamQueryVo.getBeginDate());
        }
        if (teamQueryVo.getEndDate() != null) {
            criteria.andCreateTimeLessThanOrEqualTo(teamQueryVo.getEndDate());
        }
        if (teamQueryVo.getArea() != null && teamQueryVo.getArea() >= 0) {
            criteria.andAreaEqualTo(teamQueryVo.getArea());
        }
        // 分页
        PageHelper.startPage(pageNum, pageSize);
        List<Team> teamList = teamMapper.selectByExample(teamExample);
        return new PageInfo(teamList);
    }

    /**
     * 根据主键更新
     * @param team
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Integer updateByTeamId(Team team) {
        return teamMapper.updateByPrimaryKeySelective(team);
    }


    /**
     * 根据主键查询
     * @param teamId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Team queryByTeamId(Integer teamId) {
        return teamMapper.selectByPrimaryKey(teamId);
    }


    /**
     * 根据主键逻辑删除
     * @param teamId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Integer deleteByTeamId(Integer teamId) {
        Team team = new Team();
        team.setTeamId(teamId);
        team.setIsDel(1);
        return teamMapper.updateByPrimaryKeySelective(team);
    }

    /**
     * 增加一条数据
     * @param team
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Integer insertTeam(Team team){
        return teamMapper.insertSelective(team);
    }

    /**
     * 存储logo图片
     * @param file
     * @return
     */
    public String uploadLogo(String fileSavePath, MultipartFile file){
        // 生成文件名
        String fileName = FileUtil.fileRename(file);
        // 创建存储路径,存储文件
        File savePath = new File(fileSavePath);
        if(!savePath.exists())
            savePath.mkdirs();
        try {
            file.transferTo(new File(savePath,fileName));
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}























