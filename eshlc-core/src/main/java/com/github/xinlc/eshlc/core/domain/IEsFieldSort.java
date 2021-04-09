package com.github.xinlc.eshlc.core.domain;

/**
 * ES 字段排序对象
 *
 * @author Richard
 * @since 2021-04-05
 */
public interface IEsFieldSort extends IEsSort {
    /**
     * 获取缺失字段默认值
     *
     * @return 缺失字段默认值
     */
    Object missing();

    /**
     * 设置缺失字段默认值
     *
     * @param missing 缺失字段默认值
     * @return 字段排序对象
     */
    IEsFieldSort missing(Object missing);

    /**
     * 获取未映射类型
     *
     * @return 未映射类型
     */
    String unmappedType();

    /**
     * 设置未映射类型
     *
     * @param unmappedType 未映射类型
     * @return 字段排序对象
     */
    IEsFieldSort unmappedType(String unmappedType);
}
