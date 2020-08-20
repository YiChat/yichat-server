package com.zf.yichat.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.zf.yichat.dto.DictIndexDto;
import com.zf.yichat.mapper.SysDictDataMapper;
import com.zf.yichat.mapper.SysDictMapper;
import com.zf.yichat.model.SysDict;
import com.zf.yichat.model.SysDictData;
import com.zf.yichat.service.SysDictService;
import com.zf.yichat.service.config.RedisService;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.FsConst;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.vo.DictKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictMapper dictMapper;
    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public FsResponse selectIndexList(FsPage init, String name) {

        PageHelper.startPage(init.getPageNo(), init.getPageSize());
        Example example = new Example(SysDict.class);
        if (StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
        }
        example.setOrderByClause(" ctime desc");
        return DtoChangeUtils.getPageList(dictMapper.selectByExample(example), v -> {
            DictIndexDto dto = new DictIndexDto();
            dto.setDict(v);
            //dto.setDataList(selectDataByDictCode(v.getCode()));
            return dto;
        });
    }

    @Override
    public List<SysDictData> selectDataByDictCode(String code) {
        Example example = new Example(SysDictData.class);
        example.createCriteria().andEqualTo("pcode", code);
        example.setOrderByClause(" sort asc");
        return dictDataMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public DictIndexDto selectDtoById(Integer id) {
        SysDict dict = dictMapper.selectByPrimaryKey(id);
        return DtoChangeUtils.getDto(dict, v -> {
            DictIndexDto dto = new DictIndexDto();
            dto.setDict(v);
            dto.setDataList(selectDataByDictCode(v.getCode()));
            return dto;
        });
    }

    @Override
    public SysDict selectById(Integer id) {
        return dictMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysDict dict, List<SysDictData> dataList) {
        int i = 0;
        if (Objects.nonNull(dict.getId())) {
            i = dictMapper.updateByPrimaryKeySelective(dict);
        } else {
            dict.setCtime(new Date());
            i = dictMapper.insertUseGeneratedKeys(dict);
        }

        //积分签到的值
        if (StringUtils.equals(dict.getCode(), DictKey.user_sign_get.name())) {
            redisService.setVal(DictKey.user_sign_get.name(), dict.getValue());
        }

        //积分签到的值
        if (StringUtils.equals(dict.getCode(), DictKey.user_sign_continue_get.name())) {
            redisService.setVal(DictKey.user_sign_continue_get.name(), dict.getValue());
        }

        //aes加密key
        if (StringUtils.equals(dict.getCode(), DictKey.aes_api_encript.name())) {
            redisService.setVal(DictKey.aes_api_encript.name(), dict.getValue());
        }

        //更新子项
        if (GeneralUtils.isNotNullOrEmpty(dataList)) {
            List<SysDictData> datas = selectDataByDictCode(dict.getCode());
            if (GeneralUtils.isNullOrEmpty(datas)) {
                dictDataMapper.insertList(dataList);
            } else {
                List<SysDictData> update = new ArrayList<>();
                List<SysDictData> insert = new ArrayList<>();
                for (SysDictData data : dataList) {

                    boolean flag = false;
                    for (SysDictData base : datas) {
                        if (StringUtils.equals(data.getCode(), base.getCode())) {
                            update.add(data);
                            datas.remove(base);
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        data.setStatus(FsConst.Status.EFFECT);
                        data.setCtime(new Date());
                        insert.add(data);
                    }
                }

                if (GeneralUtils.isNotNullOrEmpty(datas)) {
                    datas.forEach(v -> {
                        dictDataMapper.deleteByPrimaryKey(v.getId());
                    });
                }

                if (GeneralUtils.isNotNullOrEmpty(update)) {
                    update.forEach(v -> {
                        dictDataMapper.updateByPrimaryKeySelective(v);
                    });
                }

                if (GeneralUtils.isNotNullOrEmpty(insert)) {
                    dictDataMapper.insertList(insert);
                }

            }
        }

        return i;
    }

    @Override
    public List<SysDictData> selectData(DictKey dictKey) {

        SysDict dict = selectOne(dictKey);
        if (Objects.nonNull(dict)) {
            Example dataEx = new Example(SysDictData.class);
            dataEx.createCriteria().andEqualTo("pcode", dictKey.name());
            dataEx.setOrderByClause(" sort asc");
            //dataEx.setOrderByClause(QueryConst.ID_ASC);
            return dictDataMapper.selectByExample(dataEx);
        }
        return Lists.newArrayList();
    }

    @Override
    public SysDict selectOne(DictKey dictKey) {
        Example ex = new Example(SysDict.class);
        ex.createCriteria().andEqualTo("code", dictKey.name()).andEqualTo("status", FsConst.Status.EFFECT);
        return dictMapper.selectOneByExample(ex);
    }

    @Override
    public SysDict selectOneNotCareStatus(DictKey dictKey) {
        Example ex = new Example(SysDict.class);
        ex.createCriteria().andEqualTo("code", dictKey.name());
        return dictMapper.selectOneByExample(ex);
    }

    @Override
    public SysDictData selectOneByName(DictKey dictKey, String name) {
        return selectDataByDictCode(dictKey.name()).stream().filter(v -> StringUtils.equals(v.getName(), name)).findFirst().orElse(null);
    }

    @Override
    public SysDictData selectOneByCode(DictKey dictKey, String code) {
        return selectDataByDictCode(dictKey.name()).stream().filter(v -> StringUtils.equals(v.getCode(), code)).findFirst().orElse(null);
    }
}
