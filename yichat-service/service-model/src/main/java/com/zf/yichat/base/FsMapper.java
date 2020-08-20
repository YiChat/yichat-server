package com.zf.yichat.base;

import tk.mybatis.mapper.common.*;
import tk.mybatis.mapper.common.base.BaseInsertMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;
import tk.mybatis.mapper.common.base.delete.DeleteMapper;
import tk.mybatis.mapper.common.base.select.SelectAllMapper;
import tk.mybatis.mapper.common.base.select.SelectCountMapper;
import tk.mybatis.mapper.common.base.select.SelectMapper;
import tk.mybatis.mapper.common.base.select.SelectOneMapper;

public interface FsMapper<T> extends Mapper<T>, SelectOneMapper<T>, SelectMapper<T>, SelectAllMapper<T>, SelectCountMapper<T>, BaseInsertMapper<T>, BaseUpdateMapper<T>, DeleteMapper<T>, ExampleMapper<T>, RowBoundsMapper<T>, MySqlMapper<T>, Marker {
}
