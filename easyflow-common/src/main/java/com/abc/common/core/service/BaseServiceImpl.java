package com.abc.common.core.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.function.Function;

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    public <T, OUT> Page<OUT> pageList2CustomList(List<T> pageList, Function<List<T>, List<OUT>> processor) {
        List<OUT> result = processor.apply(pageList);
        Page<OUT> page = new Page<>();
        page.addAll(result);
        page.setTotal(((Page) pageList).getTotal());
        return page;
    }

}
