package com.jumaojiang.vo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * ssm_api
 *
 * @author wuhanwei
 * @version 1.0
 * @date 2021/10/24
 */
public class GameQueryVo {

    private String chineseName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date gameDateBegin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date gameDateEnd;
    private Integer typeId;

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public Date getGameDateBegin() {
        return gameDateBegin;
    }

    public void setGameDateBegin(Date gameDateBegin) {
        this.gameDateBegin = gameDateBegin;
    }

    public Date getGameDateEnd() {
        return gameDateEnd;
    }

    public void setGameDateEnd(Date gameDateEnd) {
        this.gameDateEnd = gameDateEnd;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
