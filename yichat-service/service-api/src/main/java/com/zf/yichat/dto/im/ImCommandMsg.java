package com.zf.yichat.dto.im;

import com.zf.yichat.utils.common.GeneralUtils;

import java.util.List;

/**
 * im接口返回对象
 *
 * @author yichat
 * @date create in 15:04 2019/5/29 2019
 */
public class ImCommandMsg {

    private String jid;
    private String node;
    private List<ImMsgFieldList> fields;

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

    public List<ImMsgFieldList> getFields() {
        return fields;
    }

    public void setFields(List<ImMsgFieldList> fields) {
        this.fields = fields;
    }

    public boolean isSuccess() {
        if (GeneralUtils.isNotNullOrEmpty(this.fields)) {
            return this.fields.stream().allMatch(v -> org.apache.commons.lang3.StringUtils.equals(v.getValue().toString(), "[\"1\"]"));
        }

        return false;
    }
}
