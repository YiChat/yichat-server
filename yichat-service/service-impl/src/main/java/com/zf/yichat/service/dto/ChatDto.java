package com.zf.yichat.service.dto;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:48 2019/6/18 2019
 */
public class ChatDto {
    private Integer referType;
    private String referId;
    private String name;
    private String avatar;

    private Msg msg;

    public Integer getReferType() {
        return referType;
    }

    public void setReferType(Integer referType) {
        this.referType = referType;
    }

    public String getReferId() {
        return referId;
    }

    public void setReferId(String referId) {
        this.referId = referId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public class Msg {
        private String nick;
        private Long time;
        private String content;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }


}
