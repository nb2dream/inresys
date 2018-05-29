package net.chenlin.dp.manage.program.entity;

import net.chenlin.dp.common.entity.BaseEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 节目单与节目关系实体
 */
public class ManaProgramTaskRelationEntity extends BaseEntity {

    /**
     * 节目id
     */
    private Long programId;

    /**
     * 节目单id
     */
    private Long taskId;

    /**
     * 播放顺序
     */
    private Integer playerNum;

    /**
     * 开始日期
     */
    private String beginDate;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 周数
     */
    private String week;


    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(Integer playerNum) {
        this.playerNum = playerNum;
    }

    public String getBeginDate() {
        return beginDate;
    }


    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate == null ? null : beginDate.trim();
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }
}