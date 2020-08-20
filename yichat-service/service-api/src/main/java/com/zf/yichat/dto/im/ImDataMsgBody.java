package com.zf.yichat.dto.im;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:59 2019/5/30 2019
 */
public class ImDataMsgBody {
    private ImCommandMsg command;


    public ImDataMsgBody(ImCommandMsg command) {
        this.command = command;
    }

    public ImCommandMsg getCommand() {
        return command;
    }

    public void setCommand(ImCommandMsg command) {
        this.command = command;
    }
}
