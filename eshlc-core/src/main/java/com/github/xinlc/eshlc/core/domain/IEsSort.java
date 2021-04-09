package com.github.xinlc.eshlc.core.domain;

import com.github.xinlc.eshlc.core.enums.SortDirectionType;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortMode;

/**
 * 排序对象
 *
 * @author Richard
 * @since 2021-04-05
 */
public interface IEsSort {

    /**
     * 获取排序字段名称
     *
     * @return 字段名称
     */
    String name();

    /**
     * 获取排序模式
     *
     * @return 排序模式
     */
    SortMode sortMode();

    /**
     * 设置排序模式
     *
     * @param sortMode 排序模式
     * @return 排序对象
     */
    IEsSort sortMode(SortMode sortMode);

    /**
     * 获取排序方向
     *
     * @return 排序方向
     */
    SortDirectionType direction();

    /**
     * 转换成排序
     *
     * @return 排序构建器
     */
    SortBuilder<?> toSortBuilder();

    /**
     * 创建指定字段升序排序对象
     *
     * @param name 排序字段名
     * @return 排序对象
     */
    static IEsFieldSort fieldAsc(String name) {
        return new DefaultFieldSort(name, SortDirectionType.ASC);
    }

    /**
     * 创建指定字段降序排序对象
     *
     * @param name 排序字段名
     * @return 排序对象
     */
    static IEsFieldSort fieldDesc(String name) {
        return new DefaultFieldSort(name, SortDirectionType.DESC);
    }

    /**
     * 创建评分升序排序对象
     *
     * @return 排序对象
     */
    static IEsScoreSort scoreAsc() {
        return new DefaultScoreSort(SortDirectionType.ASC);
    }

    /**
     * 创建评分降序排序对象
     *
     * @return 排序对象
     */
    static IEsScoreSort scoreDesc() {
        return new DefaultScoreSort(SortDirectionType.DESC);
    }

    /**
     * 创建指定字段升序排序对象
     *
     * @param name 排序字段名
     * @return 排序对象
     */
    static IEsGeoDistanceSort geoDistanceAsc(String name) {
        return new DefaultGeoDistanceSort(name, SortDirectionType.ASC);
    }

    /**
     * 创建指定字段降序排序对象
     *
     * @param name 排序字段名
     * @return 排序对象
     */
    static IEsGeoDistanceSort geoDistanceDesc(String name) {
        return new DefaultGeoDistanceSort(name, SortDirectionType.DESC);
    }

    /**
     * 创建数值类型升序排序脚本
     *
     * @param script 脚本
     * @return 排序脚本
     */
    static IEsScriptSort scriptNumberAsc(IEsScript script) {
        return new DefaultScriptSort(script, ScriptSortBuilder.ScriptSortType.NUMBER, SortDirectionType.ASC);
    }

    /**
     * 创建数值类型降序排序脚本
     *
     * @param script 脚本
     * @return 排序脚本
     */
    static IEsScriptSort scriptNumberDesc(IEsScript script) {
        return new DefaultScriptSort(script, ScriptSortBuilder.ScriptSortType.NUMBER, SortDirectionType.DESC);
    }

    /**
     * 创建字符串类型升序排序脚本
     *
     * @param script 脚本
     * @return 排序脚本
     */
    static IEsScriptSort scriptStringAsc(IEsScript script) {
        return new DefaultScriptSort(script, ScriptSortBuilder.ScriptSortType.STRING, SortDirectionType.ASC);
    }

    /**
     * 创建字符串类型降序排序脚本
     *
     * @param script 脚本
     * @return 排序脚本
     */
    static IEsScriptSort scriptStringDesc(IEsScript script) {
        return new DefaultScriptSort(script, ScriptSortBuilder.ScriptSortType.STRING, SortDirectionType.DESC);
    }
}
