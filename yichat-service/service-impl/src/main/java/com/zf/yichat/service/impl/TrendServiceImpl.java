package com.zf.yichat.service.impl;

import com.github.pagehelper.PageHelper;
import com.zf.yichat.dto.TrendCommentListDto;
import com.zf.yichat.dto.TrendItemDto;
import com.zf.yichat.dto.TrendListDto;
import com.zf.yichat.mapper.TrendCommentMapper;
import com.zf.yichat.mapper.TrendConfigMapper;
import com.zf.yichat.mapper.TrendMapper;
import com.zf.yichat.mapper.TrendPraiseMapper;
import com.zf.yichat.model.*;
import com.zf.yichat.service.FriendService;
import com.zf.yichat.service.TrendService;
import com.zf.yichat.service.UserService;
import com.zf.yichat.utils.ServiceUtils;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.FsConst;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.UserStatusEnum;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:02 2019/6/5 2019
 */
@Service
public class TrendServiceImpl implements TrendService {

    @Autowired
    private TrendMapper trendMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TrendPraiseMapper trendPraiseMapper;
    @Autowired
    private TrendCommentMapper trendCommentMapper;
    @Autowired
    private FriendService friendService;

    @Autowired
    private TrendConfigMapper trendConfigMapper;

    @Override
    public FsResponse add(String content, String imgs, String videos, String location, Long userId) {

        User user = userService.selectById(userId);
        Contracts.assertTrue(UserStatusEnum.isOpen(user.getStatus()), YiChatMsgCode.SYSTEM_USER_CLOSE.msg());

        Trend trend = new Trend();
        trend.setContent(content);
        trend.setImgs(imgs);
        trend.setVideos(videos);
        trend.setLocation(location);
        trend.setUserId(userId);
        trend.setStatus(0);
        trend.setPraiseCount(0);
        trend.setCommentCount(0);
        trend.setCtime(new Date());
        trendMapper.insertUseGeneratedKeys(trend);

        return FsResponseGen.successData(DtoChangeUtils.getDto(trend, copy -> {
            TrendListDto dto = new TrendListDto();
            dto.setTrendId(copy.getId());
            dto.setUserId(copy.getUserId());
            dto.setContent(copy.getContent());
            dto.setImgs(copy.getImgs());
            dto.setVideos(copy.getVideos());
            dto.setLocation(copy.getLocation());
            dto.setNick(user.getNick());
            dto.setAvatar(user.getAvatar());
            dto.setPraiseCount(copy.getPraiseCount());
            dto.setCommentCount(copy.getCommentCount());
            dto.setPraiseList(new ArrayList<>(0));
            dto.setTimeDesc("刚刚");
            dto.setCommentList(new ArrayList<>(0));
            return dto;
        }));

    }

    @Override
    public void delete(Long trendId) {

        Trend trend = new Trend();
        trend.setId(trendId);
        trend.setStatus(FsConst.Status.INVALID);
        trendMapper.updateByPrimaryKeySelective(trend);

    }

    @Override
    public Trend selectById(Long trendId) {
        return trendMapper.selectByPrimaryKey(trendId);
    }

    @Override
    public FsResponse selectList(FsPage page, Long userId, Long currentUserId) {
        Example example = new Example(Trend.class);
        example.createCriteria().andEqualTo(FsConst.SqlColumn.STATUS, FsConst.Status.EFFECT).andEqualTo("userId", userId);
        example.setOrderByClause(" id desc");
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        FsResponse response = DtoChangeUtils.getPageList(trendMapper.selectByExample(example), copy -> {
            TrendListDto dto = new TrendListDto();
            dto.setTrendId(copy.getId());
            dto.setUserId(copy.getUserId());
            dto.setContent(copy.getContent());
            dto.setImgs(copy.getImgs());
            dto.setVideos(copy.getVideos());
            dto.setLocation(copy.getLocation());
            User user = userService.selectById(copy.getUserId());
            dto.setNick(user.getNick());
            dto.setAvatar(user.getAvatar());
            dto.setPraiseCount(copy.getPraiseCount());
            dto.setCommentCount(copy.getCommentCount());
            dto.setPraiseList(selectPraiseUserList(copy.getId(), currentUserId));
            dto.setTimeDesc(ServiceUtils.trendTimeDesc(copy.getCtime()));
            dto.setCommentList(selectCommentList(copy.getId(), currentUserId));

            return dto;
        });

        response.setData(new TrendItemDto((List<TrendListDto>) response.getData(), selectBackground(userId)));

        return response;
    }

    @Override
    public TrendListDto detailById(Long trendId, Long userId) {
        Trend trend = selectById(trendId);
        Contracts.assertNotNull(trend, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertTrue(trend.getStatus().compareTo(FsConst.Status.EFFECT) == 0, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        return DtoChangeUtils.getDto(trend, copy -> {
            TrendListDto dto = new TrendListDto();
            dto.setTrendId(copy.getId());
            dto.setUserId(copy.getUserId());
            dto.setContent(copy.getContent());
            dto.setImgs(copy.getImgs());
            dto.setVideos(copy.getVideos());
            dto.setLocation(copy.getLocation());
            User user = userService.selectById(copy.getUserId());
            dto.setNick(user.getNick());
            dto.setAvatar(user.getAvatar());
            dto.setPraiseCount(copy.getPraiseCount());
            dto.setCommentCount(copy.getCommentCount());
            dto.setPraiseList(selectPraiseUserList(copy.getId(), userId));
            dto.setTimeDesc(ServiceUtils.trendTimeDesc(copy.getCtime()));
            dto.setCommentList(selectCommentList(copy.getId(), userId));
            return dto;
        });
    }

    @Override
    public FsResponse selectFriendList(FsPage page, Long userId) {

        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<Trend> list = trendMapper.selectFriendList(userId);
        FsResponse response = DtoChangeUtils.getPageList(list, copy -> {
            TrendListDto dto = new TrendListDto();
            dto.setTrendId(copy.getId());
            dto.setUserId(copy.getUserId());
            dto.setContent(copy.getContent());
            dto.setImgs(copy.getImgs());
            dto.setVideos(copy.getVideos());
            dto.setLocation(copy.getLocation());
            User user = userService.selectById(copy.getUserId());
            dto.setNick(user.getNick());
            dto.setAvatar(user.getAvatar());
            dto.setPraiseCount(copy.getPraiseCount());
            dto.setCommentCount(copy.getCommentCount());
            dto.setPraiseList(selectPraiseUserList(copy.getId(), userId));
            dto.setTimeDesc(ServiceUtils.trendTimeDesc(copy.getCtime()));
            dto.setCommentList(selectCommentList(copy.getId(), userId));

            return dto;
        });

        response.setData(new TrendItemDto((List<TrendListDto>) response.getData(), selectBackground(userId)));

        return response;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void praise(Long trendId, Long userId) {
        //查询是否存在点赞记录
        TrendPraise praise = selectPraise(trendId, userId);
        if (Objects.nonNull(praise)) {
            if (praise.getStatus() == FsConst.Status.INVALID) {
                praise.setStatus(FsConst.Status.EFFECT);
                int i = trendPraiseMapper.updateByPrimaryKeySelective(praise);
                if (i == 1) {
                    trendMapper.updatePraiseCount(trendId, 1);
                }
            }
        } else {

            praise = new TrendPraise();
            praise.setTrendId(trendId);
            praise.setUserId(userId);
            int i = trendPraiseMapper.insertSelective(praise);
            if (i == 1) {
                trendMapper.updatePraiseCount(trendId, 1);
            }
        }

    }

    @Override
    public void unpraise(Long trendId, Long userId) {
        //查询是否存在点赞记录
        TrendPraise praise = selectPraise(trendId, userId);
        if (Objects.nonNull(praise)) {
            if (praise.getStatus() == FsConst.Status.EFFECT) {
                praise.setStatus(FsConst.Status.INVALID);
                int i = trendPraiseMapper.updateByPrimaryKeySelective(praise);
                if (i == 1) {
                    trendMapper.updatePraiseCount(trendId, -1);
                }
            }
        } else {

        }
    }

    private TrendPraise selectPraise(Long trendId, Long userId) {
        TrendPraise praise = new TrendPraise();
        praise.setTrendId(trendId);
        praise.setUserId(userId);
        return trendPraiseMapper.selectOne(praise);
    }


    @Override
    public List<TrendListDto.PraiseUser> selectPraiseUserList(Long trendId, Long userId) {
        /*Example select = new Example(TrendPraise.class);
        select.createCriteria().andEqualTo(FsConst.SqlColumn.STATUS, FsConst.Status.EFFECT).andEqualTo("trendId", trendId);*/
        return DtoChangeUtils.getList(trendPraiseMapper.selectList(trendId, userId), v -> {
            TrendListDto.PraiseUser dto = new TrendListDto().new PraiseUser();
            User user = userService.selectById(v.getUserId());
            dto.setNick(user.getNick());
            dto.setUserId(v.getUserId());
            return dto;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void comment(String content, Long trendId, Long userId, Long commentId) {

        TrendComment comment = new TrendComment();

        Trend trend = selectById(trendId);
        Contracts.assertNotNull(trend, YiChatMsgCode.TREND_COMMENT_FAILED.msg());
        Contracts.assertTrue(userId.compareTo(trend.getUserId()) == 0 || friendService.isFriend(userId, trend.getUserId()), YiChatMsgCode.TREND_COMMENT_FAILED.msg());

        if (Objects.nonNull(commentId)) {
            TrendComment srcComment = selectCommentById(commentId);
            Contracts.assertTrue(srcComment.getStatus().compareTo(FsConst.Status.EFFECT) == 0, YiChatMsgCode.TREND_COMMENT_FAILED.msg());
            comment.setSrcUserId(srcComment.getUserId());
        }

        comment.setCommentId(commentId);
        comment.setContent(content);
        comment.setTrendId(trendId);
        comment.setUserId(userId);
        int i = trendCommentMapper.insertSelective(comment);

        //评论次数+1
        if (i == 1) {
            trendMapper.updateCommentCount(trendId, 1);
        }


    }

    @Override
    public TrendComment selectCommentById(Long commentId) {
        return trendCommentMapper.selectByPrimaryKey(commentId);
    }

    @Override
    public FsResponse deleteComment(Long commentId, Long userId) {
        TrendComment comment = selectCommentById(commentId);
        Contracts.assertNotNull(comment, YiChatMsgCode.TREND_COMMENT_DELETE_FAIL.msg());
        Contracts.assertTrue(comment.getUserId().compareTo(userId) == 0, YiChatMsgCode.TREND_COMMENT_DELETE_FAIL.msg());


        //评论次数-1
        TrendComment update = new TrendComment();
        update.setId(commentId);
        update.setStatus(FsConst.Status.INVALID);
        int i = trendCommentMapper.updateByPrimaryKeySelective(update);
        if (i == 1) {
            trendMapper.updateCommentCount(comment.getTrendId(), -1);
        }

        return FsResponseGen.successData(selectCommentList(comment.getTrendId(), userId));
    }

    @Override
    public FsResponse commentList(FsPage page, Long trendId, Long userId) {
        /*if (page.hasPageNo()) {
            PageHelper.startPage(page.getPageNo(), page.getPageSize());
        }*/
        return DtoChangeUtils.getPageList(trendCommentMapper.selectList(trendId, userId), v -> v);
    }

    @Override
    public List<TrendCommentListDto> selectCommentList(Long trendId, Long userId) {
        return trendCommentMapper.selectList(trendId, userId);
    }

    @Override
    public String selectBackground(Long userId) {
        Example example = new Example(TrendConfig.class);
        example.createCriteria().andEqualTo("userId", userId);
        return Optional.ofNullable(trendConfigMapper.selectOneByExample(example)).map(TrendConfig::getImg).orElse(null);
    }
}
