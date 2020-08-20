package com.zf.yichat.api.controller.video;

import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.*;
import com.zf.yichat.api.dto.resp.VideoRewardMemoDto;
import com.zf.yichat.model.SysDict;
import com.zf.yichat.service.SysDictService;
import com.zf.yichat.service.VideoService;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.DictKey;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:05 2020/4/15 2020
 */
@RestController
@RequestMapping("video")
public class VideoController extends BaseController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private SysDictService sysDictService;


    @RequestMapping("/publish")
    public FsResponse save(@RequestBody VideoSaveRequest params) {
        params.valid();

        return (videoService.save(params.getPath(), params.getThumbnail(), params.getRate(), params.getSeconds(), params.getContent(), getUserId()));

    }


    @RequestMapping("/list")
    public FsResponse list(@RequestBody VideoListRequest params) {


        return FsResponseGen.successData(videoService.list(getUserId(), params.getType()));

    }

    @RequestMapping("/my/list")
    public FsResponse myList(@RequestBody VideoMyListRequest params) {


        return videoService.selectMyList(FsPage.init(params.getPageNo(), params.getPageSize()), params.getType(), getUserId());

    }

    @RequestMapping("/detail")
    public FsResponse detail(@RequestBody VideoDetailRequest params) {


        return FsResponseGen.successData(videoService.detailById(params.getVideoId(), getUserId()));

    }

    @RequestMapping("/praise")
    public FsResponse praise(@RequestBody VideoPraiseRequest params) {

        params.valid();

        return FsResponseGen.successMsg(videoService.praise(params.getVideoId(), getUserId()));

    }


    @RequestMapping("/comment/praise")
    public FsResponse commentPraise(@RequestBody VideoCommentPraiseRequest params) {

        params.valid();
        videoService.commentPraise(params.getCommentId(), getUserId());
        return FsResponseGen.success();

    }


    @RequestMapping("/comment")
    public FsResponse comment(@RequestBody VideoCommentRequest params) {

        params.valid();
        return videoService.comment(params.getVideoId(), params.getCommentId(), params.getContent(), params.getSrcCommentId(), getUserId());

    }


    @RequestMapping("/comment/list")
    public FsResponse commentList(@RequestBody VideoCommentListRequest params) {

        params.valid();

        return videoService.selectCommentList(params.getPageNo(), params.getPageSize(), params.getVideoId(), getUserId());

    }

    @PostMapping("/comment/reply/list")
    public FsResponse commentReplyList(@RequestBody VideoCommentReplyListRequest params) {

        params.valid();

        return videoService.selectCommentReplyList(params.getPageNo(), params.getPageSize(), params.getCommentId(), getUserId());

    }

    @RequestMapping("/play")
    public FsResponse play(@RequestBody VideoPlayRequest params) {

        params.valid();

        return FsResponseGen.successMsg(videoService.play(params.getVideoId(), getUserId()));
    }


    @RequestMapping("/delete")
    public FsResponse delete(@RequestBody VideoDeleteRequest params) {

        params.valid();

        return (videoService.deleteVideo(params.getVideoId(), getUserId()));
    }


    @RequestMapping("/comment/delete")
    public FsResponse deleteComment(@RequestBody VideoCommentDeleteRequest params) {

        params.valid();

        return (videoService.deleteComment(params.getCommentId(), getUserId()));
    }

    //规则描述
    @RequestMapping("/reward/memo")
    public FsResponse desc() {

        SysDict dict = sysDictService.selectOne(DictKey.video_reward_memo);
        return FsResponseGen.successData(new VideoRewardMemoDto(dict.getMemo(), NumberUtils.toLong(dict.getValue())));
    }


}
