package com.zf.yichat.api.controller.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.resp.WxUserInfoDto;
import com.zf.yichat.service.config.ConfigPay;
import com.zf.yichat.utils.common.OKHttpUtil;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 09:03 2020/3/23 2020
 */
@Controller
@RequestMapping("wx")
public class WxController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(WxController.class);
    @Autowired
    private ConfigPay configPay;

    @RequestMapping("user/info")
    public FsResponse getUserInfo(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + configPay.getWeixin().getAppId()
                + "&secret=" + configPay.getWeixin().getAppKey() + "&code="
                + code + "&grant_type=authorization_code";

        String res = OKHttpUtil.httpGet(url);

        logger.info("请求获取token返回：{}", res);
        String token = JSON.parseObject(res).getString("access_token");
        String openId = JSON.parseObject(res).getString("openid");

        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openId;
        String infoRes = OKHttpUtil.httpGet(infoUrl);
        logger.info("请求获取userinfo返回：{}", res);

        JSONObject jb = JSON.parseObject(infoRes);
        WxUserInfoDto dto = new WxUserInfoDto();
        dto.setAvatar(jb.getString("headimgurl"));
        dto.setCity(jb.getString("city"));
        dto.setProvince(jb.getString("province"));
        dto.setNick(jb.getString("nickname"));
        dto.setOpenId(jb.getString("openid"));
        dto.setUnionId(jb.getString("unionid"));
        dto.setSex(jb.getInteger("sex"));
        return FsResponseGen.successData(dto);
    }
}
