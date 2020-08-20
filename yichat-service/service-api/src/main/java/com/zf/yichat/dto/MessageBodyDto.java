package com.zf.yichat.dto;

import java.io.Serializable;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:33 2019/9/17 2019
 */
public class MessageBodyDto implements Serializable {


    private Data data;
    private Integer type;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public class Data {
        private Ext ext;
        private Integer msgType;
        private String msgId;
        private String from;
        private String to;
        private Body body;
        private Integer chatType;
        private Long timestamp;

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public Integer getMsgType() {
            return msgType;
        }

        public void setMsgType(Integer msgType) {
            this.msgType = msgType;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }

        public Integer getChatType() {
            return chatType;
        }

        public void setChatType(Integer chatType) {
            this.chatType = chatType;
        }
    }

    public class Body {
        private String content;
        private String fileName;
        private String size;
        private String remotePath;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getRemotePath() {
            return remotePath;
        }

        public void setRemotePath(String remotePath) {
            this.remotePath = remotePath;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }


    public class Ext {
        private String msgFromNick;
        private Integer action;
        private String nick;
        private String msgId;
        private String avatar;
        private String userId;
        private Long msgFrom;
        private String atUser;

        public String getAtUser() {
            return atUser;
        }

        public void setAtUser(String atUser) {
            this.atUser = atUser;
        }

        public String getMsgFromNick() {
            return msgFromNick;
        }

        public void setMsgFromNick(String msgFromNick) {
            this.msgFromNick = msgFromNick;
        }

        public Integer getAction() {
            return action;
        }

        public void setAction(Integer action) {
            this.action = action;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Long getMsgFrom() {
            return msgFrom;
        }

        public void setMsgFrom(Long msgFrom) {
            this.msgFrom = msgFrom;
        }
    }


}
