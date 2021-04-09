package com.github.xinlc.eshlc.core.enums;

import org.elasticsearch.search.sort.SortOrder;

/**
 * 排序方向
 *
 * @author Richard
 * @since 2021-04-05
 */
public enum SortDirectionType {
    /**
     * 升序
     */
    ASC(SortOrder.ASC),
    /**
     * 降序
     */
    DESC(SortOrder.DESC);

    /**
     * ES排序方向
     */
    private final SortOrder order;

    SortDirectionType(SortOrder order) {
        this.order = order;
    }

    public SortOrder getOrder() {
        return order;
    }
}
