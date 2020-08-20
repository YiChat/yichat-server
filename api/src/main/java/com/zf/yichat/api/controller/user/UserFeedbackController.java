package com.zf.yichat.api.controller.user;

import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.UserFeedbackRequest;
import com.zf.yichat.service.UserFeedbackService;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:40 2019/9/4 2019
 */
@RestController
public class UserFeedbackController extends BaseController {

    @Autowired
    private UserFeedbackService userFeedbackService;

    /**
     * 意见反馈
     */
    @PostMapping("user/feedback")
    public FsResponse feedack(@RequestBody UserFeedbackRequest request) {
        request.valid();
        userFeedbackService.save(request.getContent(), getUserId());
        return FsResponseGen.success();
    }
}
