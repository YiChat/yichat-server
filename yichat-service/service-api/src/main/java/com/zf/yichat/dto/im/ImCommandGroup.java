package com.zf.yichat.dto.im;

import java.util.List;

/**
 * im接口返回对象
 *
 * @author yichat
 * @date create in 15:04 2019/5/29 2019
 */
public class ImCommandGroup {

    private String jid;
    private String node;
    private List<ImFieldGroup> fields;

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public List<ImFieldGroup> getFields() {
        return fields;
    }

    public void setFields(List<ImFieldGroup> fields) {
        this.fields = fields;
    }

}
