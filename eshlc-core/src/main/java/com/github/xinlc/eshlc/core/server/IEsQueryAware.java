package com.github.xinlc.eshlc.core.server;

import com.github.xinlc.eshlc.core.domain.IEsPage;

import java.util.List;

/**
 * 查询接口
 *
 * @author Richard
 * @since 2021-03-29
 */
public interface IEsQueryAware {

    <T> List<T> list(Class<T> clz);

    <T> IEsPage<T> page(Class<T> clz);

    <T> T single(Class<T> clz);

}
