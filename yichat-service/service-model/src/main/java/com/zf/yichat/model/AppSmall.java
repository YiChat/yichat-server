package com.zf.yichat.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "app_small")
public class AppSmall implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    /**
     * 小程序标题
     */
    private String title;
    private String icon;
    private String url;
    private Date ctime;

    public AppSmall(Integer id, String title, String icon, String url, Date ctime) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.url = url;
        this.ctime = ctime;
    }

    public AppSmall() {
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
     * 获取小程序标题
     *
     * @return title - 小程序标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置小程序标题
     *
     * @param title 小程序标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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