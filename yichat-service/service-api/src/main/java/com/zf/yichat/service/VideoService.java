package com.zf.yichat.service;

import com.zf.yichat.dto.VideoDto;
import com.zf.yichat.model.Video;
import com.zf.yichat.model.VideoComment;
import com.zf.yichat.model.VideoCommentPraise;
import com.zf.yichat.model.VideoPraise;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:11 2020/4/15 2020
 */
public interface VideoService {

    FsResponse save(String path, String thumbnail, BigDecimal rate, Integer seconds, String paramsThumbnail, Long userId);

    String praise(Long videoId, Long userId);

    Video selectById(Long videoId);

    VideoComment selectCommentById(Long commentId);

    List<VideoDto> list(Long userId, Integer type);

    VideoPraise selectPraiseByUserId(Long video, Long userId);

    FsResponse comment(Long videoId, Long commentId, String content, Long srcCommentId, Long userId);

    FsResponse selectCommentList(Integer pageNo, Integer pageSize, Long videoId, Long userId);

    FsResponse selectCommentReplyList(Integer pageNo, Integer pageSize, Long commentId, Long userId);

    void commentPraise(Long commentId, Long userId);

    VideoCommentPraise selectCommentPraiseByUserId(Long commentId, Long userId);

    FsResponse selectMyList(FsPage page, Integer type, Long userId);

    VideoDto detailById(Long videoId, Long userId);

    void viewVideo(Long videoId, Long userId);

    String play(Long videoId, Long userId);

    FsResponse deleteVideo(Long videoId, Long userId);

    FsResponse deleteComment(Long commentId, Long userId);
}
