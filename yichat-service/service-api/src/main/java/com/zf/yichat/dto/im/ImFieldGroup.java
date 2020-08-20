package com.zf.yichat.dto.im;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:05 2019/5/29 2019
 */
public class ImFieldGroup {
    private String gid;
    private String creator;
    private String name;
    private String desc;
    private String imgurlde;
    private String create_date;
    private String version;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgurlde() {
        return imgurlde;
    }

    public void setImgurlde(String imgurlde) {
        this.imgurlde = imgurlde;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
