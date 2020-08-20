package com.zf.yichat.api.controller.user;

import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.UserSignRequest;
import com.zf.yichat.service.UserService;
import com.zf.yichat.utils.response.FsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 签到积分
 *
 * @author yichat
 * @date create in 10:36 2019/8/8 2019
 */
@RestController
public class UserSignController extends BaseController {


    @Autowired
    private UserService userService;

    /**
     * 签到接口
     */
    @PostMapping("user/sign")
    public FsResponse sign(@RequestBody UserSignRequest params) {


        return userService.recordSign(getUserId(), Optional.ofNullable(params.getSignType()).orElse(0) == 1);


    }

}
