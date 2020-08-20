package com.zf.yichat.dto.im;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:59 2019/5/30 2019
 */
public class ImDataBody {
    private ImCommand command;

    public ImDataBody(ImCommand command) {
        this.command = command;
    }

    public ImCommand getCommand() {
        return command;
    }

    public void setCommand(ImCommand command) {
        this.command = command;
    }
}
