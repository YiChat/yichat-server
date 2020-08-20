package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "app_robot_relation")
public class AppRobotRelation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 机器人ID
     */
    @Column(name = "robot_id")
    private Long robotId;
    /**
     * 1单人 2群聊
     */
    private Integer type;
    /**
     * 类型不同针对的对象
     */
    @Column(name = "refer_id")
    private Long referId;
    private Date ctime;

    public AppRobotRelation(Long id, Long robotId, Integer type, Long referId, Date ctime) {
        this.id = id;
        this.robotId = robotId;
        this.type = type;
        this.referId = referId;
        this.ctime = ctime;
    }

    public AppRobotRelation() {
        super();
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取机器人ID
     *
     * @return robot_id - 机器人ID
     */
    public Long getRobotId() {
        return robotId;
    }

    /**
     * 设置机器人ID
     *
     * @param robotId 机器人ID
     */
    public void setRobotId(Long robotId) {
        this.robotId = robotId;
    }

    /**
     * 获取1单人 2群聊
     *
     * @return type - 1单人 2群聊
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1单人 2群聊
     *
     * @param type 1单人 2群聊
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取类型不同针对的对象
     *
     * @return refer_id - 类型不同针对的对象
     */
    public Long getReferId() {
        return referId;
    }

    /**
     * 设置类型不同针对的对象
     *
     * @param referId 类型不同针对的对象
     */
    public void setReferId(Long referId) {
        this.referId = referId;
    }

    /**
     * @return ctime
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * @param ctime
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}