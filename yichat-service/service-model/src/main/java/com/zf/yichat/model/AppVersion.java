package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "app_version")
public class AppVersion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private Integer type;
    /**
     * 版本
     */
    private String version;
    private String memo;
    /**
     * 0 发布  1取消发布
     */
    private Integer status;
    /**
     * 下载链接
     */
    private String url;
    /**
     * 是否强制更新 0否 1是
     */
    @Column(name = "update_status")
    private Integer updateStatus;
    private Date ctime;

    public AppVersion(Integer id, Integer type, String version, String memo, Integer status, String url, Integer updateStatus, Date ctime) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.memo = memo;
        this.status = status;
        this.url = url;
        this.updateStatus = updateStatus;
        this.ctime = ctime;
    }

    public AppVersion() {
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
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取版本
     *
     * @return version - 版本
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置版本
     *
     * @param version 版本
     */
    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    /**
     * @return memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * 获取0 发布  1取消发布
     *
     * @return status - 0 发布  1取消发布
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0 发布  1取消发布
     *
     * @param status 0 发布  1取消发布
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取下载链接
     *
     * @return url - 下载链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置下载链接
     *
     * @param url 下载链接
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取是否强制更新 0否 1是
     *
     * @return update_status - 是否强制更新 0否 1是
     */
    public Integer getUpdateStatus() {
        return updateStatus;
    }

    /**
     * 设置是否强制更新 0否 1是
     *
     * @param updateStatus 是否强制更新 0否 1是
     */
    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
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