package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 姓名
     */
    private String name;
    /**
     * 关联角色
     */
    @Column(name = "roleId")
    private Integer roleid;
    /**
     * 关联公司
     */
    @Column(name = "companyId")
    private Integer companyid;
    /**
     * 推荐人
     */
    private String referee;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 0正常  1删除
     */
    private Integer status;
    /**
     * 备注
     */
    private String memo;
    /**
     * 创建人ID
     */
    @Column(name = "userId")
    private Integer userid;
    private Date ctime;
    private Date utime;

    public SysUser(Integer id, String username, String password, String name, Integer roleid, Integer companyid, String referee, String mobile, String email, Integer status, String memo, Integer userid, Date ctime, Date utime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.roleid = roleid;
        this.companyid = companyid;
        this.referee = referee;
        this.mobile = mobile;
        this.email = email;
        this.status = status;
        this.memo = memo;
        this.userid = userid;
        this.ctime = ctime;
        this.utime = utime;
    }

    public SysUser() {
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
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取关联角色
     *
     * @return roleId - 关联角色
     */
    public Integer getRoleid() {
        return roleid;
    }

    /**
     * 设置关联角色
     *
     * @param roleid 关联角色
     */
    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    /**
     * 获取关联公司
     *
     * @return companyId - 关联公司
     */
    public Integer getCompanyid() {
        return companyid;
    }

    /**
     * 设置关联公司
     *
     * @param companyid 关联公司
     */
    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    /**
     * 获取推荐人
     *
     * @return referee - 推荐人
     */
    public String getReferee() {
        return referee;
    }

    /**
     * 设置推荐人
     *
     * @param referee 推荐人
     */
    public void setReferee(String referee) {
        this.referee = referee == null ? null : referee.trim();
    }

    /**
     * 获取手机号
     *
     * @return mobile - 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取0正常  1删除
     *
     * @return status - 0正常  1删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0正常  1删除
     *
     * @param status 0正常  1删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取备注
     *
     * @return memo - 备注
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置备注
     *
     * @param memo 备注
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * 获取创建人ID
     *
     * @return userId - 创建人ID
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * 设置创建人ID
     *
     * @param userid 创建人ID
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
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

    /**
     * @return utime
     */
    public Date getUtime() {
        return utime;
    }

    /**
     * @param utime
     */
    public void setUtime(Date utime) {
        this.utime = utime;
    }
}