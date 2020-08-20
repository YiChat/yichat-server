package com.zf.yichat.api.controller;

import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:47 2019/6/3 2019
 */
@RestController
@RequestMapping("error")
public class ErrorController {

    @RequestMapping("/auth")
    public FsResponse auth() {

        return FsResponseGen.fail(YiChatMsgCode.SYSTEM_LOGIN_STATUS);
    }
}
