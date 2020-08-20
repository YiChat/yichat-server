package com.zf.yichat.dto;


import com.zf.yichat.model.SysDict;
import com.zf.yichat.model.SysDictData;

import java.util.List;

public class DictIndexDto {
    private SysDict dict;

    private List<SysDictData> dataList;

    public SysDict getDict() {
        return dict;
    }

    public void setDict(SysDict dict) {
        this.dict = dict;
    }

    public List<SysDictData> getDataList() {
        return dataList;
    }

    public void setDataList(List<SysDictData> dataList) {
        this.dataList = dataList;
    }
}
