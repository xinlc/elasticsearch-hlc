package com.github.xinlc.eshlc.core.server;

import com.github.xinlc.eshlc.core.domain.IEsPage;
import com.github.xinlc.eshlc.core.exception.EsException;

/**
 * 文档查询接口
 *
 * @author Richard
 * @since 2021-03-29
 */
public interface IEsQueryAware {

    /**
     * 分页获取文档
     *
     * @param clz 结果对象class
     * @param <T> 对象类型
     * @return 分页信息
     */
    <T> IEsPage<T> page(Class<T> clz) throws EsException;

}
