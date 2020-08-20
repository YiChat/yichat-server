package com.zf.yichat.dto.im;

import com.zf.yichat.utils.common.GeneralUtils;

import java.util.List;

/**
 * im接口返回对象
 *
 * @author yichat
 * @date create in 15:04 2019/5/29 2019
 */
public class ImCommandList {

    private String jid;
    private String node;
    private List<ImFieldList> fields;

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

    public List<ImFieldList> getFields() {
        return fields;
    }

    public void setFields(List<ImFieldList> fields) {
        this.fields = fields;
    }

    public boolean isSuccess() {
        if (GeneralUtils.isNotNullOrEmpty(this.fields)) {

        }

        return false;
    }
}
