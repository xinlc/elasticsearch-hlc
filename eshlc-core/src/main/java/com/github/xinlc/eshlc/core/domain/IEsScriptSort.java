package com.github.xinlc.eshlc.core.domain;

import org.elasticsearch.search.sort.ScriptSortBuilder;

/**
 * ES 脚本排序对象
 *
 * @author Richard
 * @since 2021-04-05
 */
public interface IEsScriptSort extends IEsSort {
    /**
     * 获取排序脚本
     *
     * @return 排序脚本
     */
    IEsScript script();

    /**
     * 脚本排序类型
     *
     * @return 排序类型
     */
    ScriptSortBuilder.ScriptSortType sortType();
}
