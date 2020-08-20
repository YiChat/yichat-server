package com.zf.yichat.api.controller;

import com.zf.yichat.api.dto.request.SmsSendRequest;
import com.zf.yichat.service.SmsService;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信验证码功能
 *
 * @author yichat
 * @date create in 15:56 2019/7/15 2019
 */
@RestController
@RequestMapping("sms")
public class SmsController extends BaseController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public FsResponse sendSms(@RequestBody SmsSendRequest request) {

        String msg = GeneralUtils.randomNum(6);
        boolean success = smsService.send(request.getMobile(), msg);
        if (!success) {
            return FsResponseGen.fail(YiChatMsgCode.SYSTEM_SMS_SEND_FAIL);
        }
        smsService.save(request.getMobile(), msg);
        return FsResponseGen.successData(msg);

    }


}
