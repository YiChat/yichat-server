package com.zf.yichat.service.impl;

import com.alibaba.fastjson.JSON;
import com.zf.yichat.dto.GroupDto;
import com.zf.yichat.dto.im.*;
import com.zf.yichat.model.SysDict;
import com.zf.yichat.service.ImApiService;
import com.zf.yichat.service.SysDictService;
import com.zf.yichat.service.config.RedisService;
import com.zf.yichat.utils.ServiceUtils;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.common.OKHttpUtil;
import com.zf.yichat.vo.DictKey;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:00 2019/5/29 2019
 */
@Service
public class ImApiServiceImpl implements ImApiService {


    private static String create_im = "http://{}:19080/rest/adhoc/sess-man@app.im";
    private static String group_member = "http://{}:19080/rest/adhoc/muc@muc.app.im";
    private static String group_info = "http://{}:19080/rest/adhoc/muc@muc.app.im";
    private static String delete_group_member = "http://{}:19080/rest/adhoc/sess-man@muc.app.im";
    private static String delete_member = "http://{}:19080/rest/adhoc/sess-man@app.im";
    private static String my_group = "http://{}:19080/rest/adhoc/muc@muc.app.im";
    private static String add_group_member = "http://{}:19080/rest/adhoc/muc@muc.app.im";
    private static String delete_group = "http://{}:19080/rest/adhoc/muc@muc.app.im";
    private static String send_group = "http://{}:19080/rest/adhoc/muc@muc.app.im";
    private static String send_single = "http://{}:19080/rest/adhoc/sess-man@app.im";
    @Autowired
    private RedisService redisService;
    @Autowired
    private SysDictService sysDictService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String getIp() {
        String imIp = redisService.getVal("im.server");
        if (StringUtils.isBlank(imIp)) {
            SysDict dict = sysDictService.selectOne(DictKey.im_server);
            imIp = Optional.ofNullable(dict).map(SysDict::getValue).orElse(null);
            Contracts.assertTrue(StringUtils.isNotBlank(imIp), "im服务器未配置");

        }
        return imIp;
    }

    @Override
    public ImCommand create(String accountId, String password) {

        logger.info("ip:{}", getIp());
        String url = create_im.replace("{}", getIp());

        ImCommand command = new ImCommand();
        List<ImField> list = new ArrayList<>();
        list.add(new ImField("accountjid", accountId + ServiceUtils.IM_USER_ID_SUFFIX));
        list.add(new ImField("password", password));
        command.setFields(list);
        command.setNode("http://jabber.org/protocol/admin#add-user");

        String result = OKHttpUtil.httpPostJson(url, JSON.toJSONString(new ImDataBody(command)), commonHeader());
        logger.info("create imUser result:{}", result);
        if (StringUtils.isNotBlank(result)) {
            ImDataBody res = JSON.parseObject(result, ImDataBody.class);
            if (res.getCommand().isSuccess()) {
                return res.getCommand();
            } else {
                return null;
            }

        }
        return null;
    }

    private Map<String, String> commonHeader() {
        Map<String, String> result = new HashMap<>();
        result.put("None-AES", "1");
        result.put("Content-Type", "application/json");
        result.put("Authorization", "Basic YWRtaW5AYXBwLmltOjEyMzQ1NkBhcHA=");
        return result;
    }


    @Override
    public GroupDto selectGroupById(Long groupId, Long userId) {
        String url = group_info.replace("{}", getIp());

        ImCommand command = new ImCommand();
        List<ImField> list = new ArrayList<>();
        list.add(new ImField("uid", String.valueOf(userId)));
        list.add(new ImField("gid", String.valueOf(groupId)));
        command.setFields(list);
        command.setNode("group-get");

        String result = OKHttpUtil.httpPostJson(url, JSON.toJSONString(new ImDataBody(command)), commonHeader());
        logger.debug("group member get:{}", result);
        if (StringUtils.isNotBlank(result)) {
            ImDataBody res = JSON.parseObject(result, ImDataBody.class);
            if (GeneralUtils.isNotNullOrEmpty(res.getCommand().getFields()) && !res.getCommand().getFields().get(0).equals("code")) {
                try {
                    List<ImField> fields = res.getCommand().getFields();

                    GroupDto dto = new GroupDto();

                    dto.setGroupId(Integer.parseInt(fields.get(0).getValue()));
                    dto.setOwner(Long.parseLong(fields.get(1).getValue()));
                    dto.setGroupName(fields.get(2).getValue());
                    dto.setGroupDescription(fields.get(3).getValue());
                    dto.setGroupAvatar(fields.get(4).getValue());
                    dto.setCrateUnixTime(Long.parseLong(fields.get(5).getValue()));
                    dto.setVersion(fields.get(6).getValue());


                    return dto;
                } catch (Exception e) {
                    return null;
                }
            } else {
                return null;
            }

        }
        return null;
    }

    @Override
    public List<Long> selectGroupUserIdList(Long userId, Long groupId) {
        String url = group_member.replace("{}", getIp());

        ImCommand command = new ImCommand();
        List<ImField> list = new ArrayList<>();
        list.add(new ImField("uid", String.valueOf(userId)));
        list.add(new ImField("gid", String.valueOf(groupId)));
        command.setFields(list);
        command.setNode("member-get");

        String result = OKHttpUtil.httpPostJson(url, JSON.toJSONString(new ImDataBody(command)), commonHeader());
        logger.debug("group member get:{}", result);
        if (StringUtils.isNotBlank(result)) {
            ImDataBody res = JSON.parseObject(result, ImDataBody.class);
            if (GeneralUtils.isNotNullOrEmpty(res.getCommand().getFields()) && !res.getCommand().getFields().get(0).equals("code")) {
                try {
                    return JSON.parseArray(res.getCommand().getFields().get(0).getValue(), Long.class);
                } catch (Exception e) {
                    return GeneralUtils.asList(org.apache.commons.lang3.math.NumberUtils.toLong(res.getCommand().getFields().get(0).getValue()));
                }
            } else {
                return null;
            }

        }
        return null;
    }

    @Override
    public void deleteGroupMember(Integer gid, Long userId) {
        String url = delete_group_member.replace("{}", getIp());

        ImCommand command = new ImCommand();
        List<ImField> list = new ArrayList<>();
        list.add(new ImField("uid", String.valueOf(userId)));
        list.add(new ImField("gid", String.valueOf(gid)));
        command.setFields(list);
        command.setNode("group-quit");

        String result = OKHttpUtil.httpPostJson(url, JSON.toJSONString(new ImDataBody(command)), commonHeader());
        logger.debug("delete group member result:{}", result);
        if (StringUtils.isNotBlank(result)) {
            ImDataBody res = JSON.parseObject(result, ImDataBody.class);
            if (res.getCommand().isSuccess()) {
                logger.debug("delete group member success");
            } else {

            }

        }

    }

    @Override
    public void deleteUser(List<Long> ids) {
        String url = delete_member.replace("{}", getIp());

        ImCommandList command = new ImCommandList();
        List<ImFieldList> list = new ArrayList<>();
        list.add(new ImFieldList("uids", ids.stream().map(String::valueOf).collect(Collectors.toList())));
        command.setFields(list);
        command.setNode("delete-user");

        String result = OKHttpUtil.httpPostJson(url, JSON.toJSONString(new ImDataListBody(command)), commonHeader());
        logger.debug("delete  member result:{}", result);
        if (StringUtils.isNotBlank(result)) {
            ImDataBody res = JSON.parseObject(result, ImDataBody.class);
            if (res.getCommand().isSuccess()) {
                logger.debug("delete  member success");
            } else {

            }

        }
    }

    @Override
    public String getMyGroup(String jsonStr) {
        String url = my_group.replace("{}", getIp());

        String result = OKHttpUtil.httpPostJson(url, jsonStr, commonHeader());
        logger.debug("my  group result:{}", result);
        return result;
    }

    @Override
    public String addGroupMember(String jsonStr) {
        String url = add_group_member.replace("{}", getIp());

        String result = OKHttpUtil.httpPostJson(url, jsonStr, commonHeader());
        logger.debug("add  group member result:{}", result);
        return result;
    }

    @Override
    public String deleteGroupMember(String jsonStr) {
        String url = delete_group_member.replace("{}", getIp());

        String result = OKHttpUtil.httpPostJson(url, jsonStr, commonHeader());
        logger.debug("delete  group member result:{}", result);
        return result;
    }

    @Override
    public String deleteGroup(String jsonStr) {
        String url = delete_group.replace("{}", getIp());

        String result = OKHttpUtil.httpPostJson(url, jsonStr, commonHeader());
        logger.debug("delete  group  result:{}", result);
        return result;
    }

    @Override
    public boolean sendRobotMsg(Integer type, Long id, String text) {
        boolean success = false;
        //单聊
        if (type == 1) {
            String url = send_single.replace("{}", getIp());

            String data = "{ \"command\": { \"node\": \"message-to-users\", \"fields\": [ { \"var\": \"uids\", \"value\": [\"" + id + "\"] }, { \"var\": \"message\", \"value\": \"" + text + "\" } ] } }";
            System.out.println(JSON.toJSONString(data));
            String res = OKHttpUtil.httpPostJson(url, data, commonHeader());
        }

        //群聊
        if (type == 2) {
            String url = send_group.replace("{}", getIp());
            String data = "{ \"command\": { \"node\": \"message-to-groups\", \"fields\": [ { \"var\": \"gids\", \"value\": [\"" + id + "\"] }, { \"var\": \"message\", \"value\": \"" + text + "\" } ] } }";
            String res = OKHttpUtil.httpPostJson(url, data, commonHeader());
            System.out.println(JSON.toJSONString(res));


        }

        return success;
    }

}
