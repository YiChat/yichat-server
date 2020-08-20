package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tig_groups")
public class TigGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer gid;
    private String creator;
    private String descri;
    private String imgurl;
    @Column(name = "creation_date")
    private Long creationDate;
    private Long version;
    private String name;

    public TigGroup(Integer gid, String creator, String descri, String imgurl, Long creationDate, Long version, String name) {
        this.gid = gid;
        this.creator = creator;
        this.descri = descri;
        this.imgurl = imgurl;
        this.creationDate = creationDate;
        this.version = version;
        this.name = name;
    }

    public TigGroup() {
        super();
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
     * @return creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * @return descri
     */
    public String getDescri() {
        return descri;
    }

    /**
     * @param descri
     */
    public void setDescri(String descri) {
        this.descri = descri == null ? null : descri.trim();
    }

    /**
     * @return imgurl
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * @param imgurl
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    /**
     * @return creation_date
     */
    public Long getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate
     */
    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}