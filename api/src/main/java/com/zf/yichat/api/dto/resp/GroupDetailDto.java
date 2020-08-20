package com.zf.yichat.api.dto.resp;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:22 2019/10/9 2019
 */
public class GroupDetailDto {

    private Integer gid;
    //群主
    private Long creator;
    //群名称
    private String name;
    //群描述
    private String desc;
    //背景图片
    private String imgurlde;
    //创建时间戳
    private Long create_date;
    //版本
    private Long version;


    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
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

    public Long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Long create_date) {
        this.create_date = create_date;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
