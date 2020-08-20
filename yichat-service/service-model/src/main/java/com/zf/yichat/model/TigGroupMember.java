package com.zf.yichat.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tig_group_members")
public class TigGroupMember implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private Integer gid;
    private String uid;
    private Date timestamp;

    public TigGroupMember(Integer id, Integer gid, String uid, Date timestamp) {
        this.id = id;
        this.gid = gid;
        this.uid = uid;
        this.timestamp = timestamp;
    }

    public TigGroupMember() {
        super();
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return gid
     */
    public Integer getGid() {
        return gid;
    }

    /**
     * @param gid
     */
    public void setGid(Integer gid) {
        this.gid = gid;
    }

    /**
     * @return uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * @return timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}