package com.zf.yichat.service;


import com.zf.yichat.dto.DictIndexDto;
import com.zf.yichat.model.SysDict;
import com.zf.yichat.model.SysDictData;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.vo.DictKey;

import java.util.List;

public interface SysDictService {


    FsResponse selectIndexList(FsPage init, String name);

    List<SysDictData> selectDataByDictCode(String dictCode);

    DictIndexDto selectDtoById(Integer id);

    int save(SysDict dict, List<SysDictData> dataList);

    SysDict selectById(Integer id);

    List<SysDictData> selectData(DictKey dictKey);

    SysDict selectOne(DictKey dictKey);

    SysDict selectOneNotCareStatus(DictKey dictKey);

    SysDictData selectOneByName(DictKey dictKey, String name);

    SysDictData selectOneByCode(DictKey dictKey, String code);
}
