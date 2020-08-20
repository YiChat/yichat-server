package com.zf.yichat.api.controller.user;

import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.RegisterRequest;
import com.zf.yichat.api.dto.resp.RegisterDto;
import com.zf.yichat.model.User;
import com.zf.yichat.model.UserIm;
import com.zf.yichat.service.BalanceService;
import com.zf.yichat.service.FriendService;
import com.zf.yichat.service.SysDictService;
import com.zf.yichat.service.UserService;
import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.BalanceType;
import com.zf.yichat.vo.DictKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 用户注册
 *
 * @author yichat
 * @date create in 15:10 2019/5/28 2019
 */
@Controller
public class RegisterController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private ConfigSet configSet;

    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private BalanceService balanceService;

    @Autowired
    private FriendService friendService;


    @RequestMapping("user/register")
    @ResponseBody
    public FsResponse register(@RequestBody RegisterRequest request) {
        request.valid();
        if (userService.selectByMobile(request.getMobile()) != null) {
            return FsResponseGen.fail(configSet.isMobileAppIdStatus() ? YiChatMsgCode.REGISTER_MOBILE_APPID_EXIST : YiChatMsgCode.REGISTER_MOBILE_EXIST);
        }
        User user = request.generateUser();

        //校验设置ID
        if (StringUtils.isNotBlank(request.getDeviceId())) {
            if (userService.existDeviceId(request.getDeviceId())) {
                return FsResponseGen.fail(YiChatMsgCode.REGISTER_DEVICEID_EXIST);
            }
        }

        User recUser = null;
        //推荐码
        if (StringUtils.isNotBlank(request.getRecommendCode())) {

            recUser = userService.selectByRecommendCode(request.getRecommendCode());
            if (Objects.nonNull(recUser)) {
                BigDecimal reword = new BigDecimal(sysDictService.selectOne(DictKey.recommend_user_reward).getValue());
                user.setRecommendCode(request.getRecommendCode());
                user.setRecommendTime(new Date());


                balanceService.update(recUser.getId(), BalanceType.RECOMMEND_USER, reword, recUser.getId(), "推荐用户注册奖励");
            } else {
                return FsResponseGen.fail(YiChatMsgCode.USER_INFO_RECOMMEND_CODE_NONE);
            }
        }
        user.setRecommendCode(user.getRecommendCode());
        UserIm im = userService.add(user);

        RegisterDto data = new RegisterDto();
        data.setImPassword(im.getImPassword());
        data.setToken(user.getToken());
        data.setUserId(user.getId());
        data.setAppId(user.getAppid());
        data.setMobile(user.getMobile());
        data.setUcode(user.getUcode());
        data.setRecUserId(Long.valueOf(request.getRecommendCode()));


        if (recUser != null) {
            friendService.duang(recUser.getId(), user.getId());
        }

        return FsResponseGen.successData(data);
    }

}
