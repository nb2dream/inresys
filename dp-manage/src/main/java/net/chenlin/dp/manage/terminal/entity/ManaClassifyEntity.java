package net.chenlin.dp.manage.terminal.entity;

import net.chenlin.dp.common.utils.CommonUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * 终端分类实体类
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/9/18 17:13
 */
public class ManaClassifyEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
    private Long classifyId;

    /**
     * 分类代码
     */
    private String classifyCode;

    /**
     * 父级别Id
     */
    private Long parentId;

    /**
     * 父级节节点名
     */
    private String parentName;

    /**
     * 分类名
     */
    private String name;

    /**
     * 层级
     */
    private Integer layer;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 状态，1：显示，0：隐藏
     */
    private Integer status;

    /**
     * 创建用户
     */
    private Long createUserId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Timestamp gmtCreate;

    /**
     * 修改时间
     */
    private Timestamp gmtModified;

    /**
     * ztree属性
     */
    private Boolean open;

    private Boolean isParent;

    private Integer size;

    private List<?> list;

    /**
     * 分类类型： 1 终端分组  2 素材分组
     */
    private Integer type;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyCode() {
        return classifyCode;
    }

    public void setClassifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    /**
     * 判断是否有子节点，有的话设置 isParent 为 true
     */
    public void checkParent() {
        if(CommonUtils.isIntThanZero(this.size)) {
            this.isParent = true;
        } else {
            this.isParent = false;
        }
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}
