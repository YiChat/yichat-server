package com.zf.yichat.utils.common;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DtoChangeUtils {
    public static <F, T> T getDto(F dto, Function<F, T> event) {
        if (dto == null) {
            return null;
        }
        return event.apply(dto);
    }

    public static <K, V> Map<K, V> getMap(List<V> list, java.util.function.Function<V, K> keyMapper) {
        if (GeneralUtils.isNullOrEmpty(list)) {
            return list.stream().collect(Collectors.toMap(keyMapper, v -> v));
        } else {
            return Maps.newHashMap();
        }
    }

    public static <F, K, V> Map<K, V> getMap(List<F> list, java.util.function.Function<F, K> keyMapper, java.util.function.Function<F, V> valueMapper) {
        if (GeneralUtils.isNotNullOrEmpty(list)) {
            return list.stream().collect(Collectors.toMap(keyMapper, valueMapper));
        } else {
            return Maps.newHashMap();
        }
    }

    public static <F, T> List<T> getList(List<F> list, Function<F, T> event) {
        if (list != null && list.size() > 0) {
            return list.stream().map(event::apply).filter(Objects::nonNull).collect(Collectors.toList());
        } else {
            return Lists.newArrayList();
        }
    }

    public static <F, T> FsResponse<T> getPageList(List<F> list, Function<F, T> event) {
        PageInfo pageInfo = new PageInfo(list);
        FsResponse response = FsResponseGen.successData(getList(list, event));
        response.setPageNo(pageInfo.getPageNum());
        response.setPageSize(pageInfo.getPageSize());
        response.setCount(pageInfo.getTotal());
        return response;
    }

    public static <F, T> List<T> getList(List<T> oList, List<F> list, Function<F, T> event) {
        if (oList == null) {
            oList = Lists.newArrayList();
        }
        if (list != null && list.size() > 0) {
            oList.addAll(list.stream().map(event::apply).filter(Objects::nonNull).collect(Collectors.toList()));
        }
        return oList;
    }
}
