package com.jumaojiang.vo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * ssm_api
 *
 * @author wuhanwei
 * @version 1.0
 * @date 2021/10/23
 */
public class PlayerQueryVo {
    private String playerName;
    private Integer playerNum;
    private String location;
    @DateTimeFormat(pattern = "yyyy-DD-mm")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-DD-mm")
    private Date endDate;
    private String nationality;

    @Override
    public String toString() {
        return "PlayerQueryVo{" +
                "playerName='" + playerName + '\'' +
                ", playerNum=" + playerNum +
                ", location='" + location + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", nationality='" + nationality + '\'' +
                '}';
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(Integer playerNum) {
        this.playerNum = playerNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}






















