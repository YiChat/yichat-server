package com.zf.yichat.api.controller.trend;

import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.*;
import com.zf.yichat.mapper.TrendConfigMapper;
import com.zf.yichat.model.Trend;
import com.zf.yichat.model.TrendConfig;
import com.zf.yichat.service.FriendService;
import com.zf.yichat.service.TrendService;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Objects;
import java.util.Optional;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:44 2019/6/5 2019
 */
@RestController
@RequestMapping("trend")
public class TrendController extends BaseController {


    @Autowired
    private TrendService trendService;

    @Autowired
    private FriendService friendService;
    @Autowired
    private TrendConfigMapper trendConfigMapper;

    /**
     * 发布动态
     */
    @PostMapping("/publish")
    public FsResponse publish(@RequestBody TrendPublishRequest params) {
        params.valid();
        return trendService.add(params.getContent(), params.getImgs(), params.getVideos(), params.getLocation(), getUserId());
    }

    /**
     * 删除动态
     */
    @PostMapping("/delete")
    public FsResponse delete(@RequestBody TrendDeleteRequest params) {

        params.valid();
        Trend trend = trendService.selectById(params.getTrendId());
        Contracts.assertTrue(trend.getUserId().compareTo(getUserId()) == 0, YiChatMsgCode.TREND_DELETE_AUTH.msg());

        trendService.delete(params.getTrendId());
        return FsResponseGen.success();
    }

    /**
     * 动态列表
     */
    @PostMapping("/list")
    public FsResponse list(@RequestBody TrendListRequest params) {
        params.valid();
        if (getUserId().compareTo(params.getUserId()) != 0) {
            Contracts.assertTrue(friendService.isFriend(getUserId(), params.getUserId()), YiChatMsgCode.TREND_LIST_AUTH.msg());
        }
        return trendService.selectList(FsPage.init(params.getPageNo(), params.getPageSize()), params.getUserId(), getUserId());
    }

    /**
     * 动态详情
     */
    @PostMapping("/detail")
    public FsResponse detail(@RequestBody TrendDetailRequest params) {
        params.valid();

        return FsResponseGen.successData(trendService.detailById(params.getTrendId(), getUserId()));
    }

    /**
     * 好友动态
     */
    @PostMapping("/friend/list")
    public FsResponse friendList(@RequestBody FsPage params) {

        return trendService.selectFriendList(FsPage.init(params.getPageNo(), params.getPageSize()), getUserId());
    }

    /**
     * 动态背景图
     */
    @PostMapping("background")
    public FsResponse background(@RequestBody TrendBackgroundRequest request) {

        Example example = new Example(TrendConfig.class);
        example.createCriteria().andEqualTo("userId", Optional.ofNullable(request.getUserId()).orElse(getUserId()));
        return FsResponseGen.successData(Optional.ofNullable(trendConfigMapper.selectOneByExample(example)).map(TrendConfig::getImg).orElse(null));
    }

    /**
     * 背景图设置
     */
    @PostMapping("background/set")
    public FsResponse backgroundSet(@RequestBody TrendBackgroundRequest request) {

        Long userId = Optional.ofNullable(request.getUserId()).orElse(getUserId());
        Example example = new Example(TrendConfig.class);
        example.createCriteria().andEqualTo("userId", userId);

        TrendConfig config = trendConfigMapper.selectOneByExample(example);
        if (Objects.nonNull(config)) {
            config.setImg(request.getImg());
            trendConfigMapper.updateByPrimaryKeySelective(config);
        } else {
            config = new TrendConfig();
            config.setImg(request.getImg());
            config.setUserId(userId);
            trendConfigMapper.insertSelective(config);
        }
        return FsResponseGen.success();
    }


}
