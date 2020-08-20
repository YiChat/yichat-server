package com.zf.yichat.dto.im;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:59 2019/5/30 2019
 */
public class ImDataListBody {
    private ImCommandList command;


    public ImDataListBody(ImCommandList command) {
        this.command = command;
    }

    public ImCommandList getCommand() {
        return command;
    }

    public void setCommand(ImCommandList command) {
        this.command = command;
    }
}
