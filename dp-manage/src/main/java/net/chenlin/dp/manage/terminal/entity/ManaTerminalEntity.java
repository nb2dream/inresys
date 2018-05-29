package net.chenlin.dp.manage.terminal.entity;

import java.util.Date;

/**
 * 终端实体类
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/9/18 17:12
 */
public class ManaTerminalEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 终端id
     */
    private Long id;

    /**
     * 终端id
     */
    private String terminalId;

    /**
     * 终端名
     */
    private String terminalName;

    /**
     * 创建时间
     */
    private Date  createDate;

    /**
     * 创建用户id
     */
    private Long createUserId;

    /**
     * 最后一次上线时间
     */
    private Date lastOnlineDate;

    /**
     * 网络状态： 0：离线 1：在线
     */
    private Integer networkStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 同步状态  0：未同步 1：已同步
     */
    private Integer syncStatus;

    /**
     * 终端所属分组id
     */
    private Long classifyId;

    /**
     * 根节点 用于加载全部项
     */
    private Integer rootNode;

    /**
     * 终端分组名
     */
    private String classifyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getLastOnlineDate() {
        return lastOnlineDate;
    }

    public void setLastOnlineDate(Date lastOnlineDate) {
        this.lastOnlineDate = lastOnlineDate;
    }

    public Integer getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(Integer networkStatus) {
        this.networkStatus = networkStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Integer getRootNode() {
        return rootNode;
    }

    public void setRootNode(Integer rootNode) {
        this.rootNode = rootNode;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public ManaTerminalEntity() {
    }

    public ManaTerminalEntity(String terminalId, Integer networkStatus) {
        this.terminalId = terminalId;
        this.networkStatus = networkStatus;
    }
}
