package com.github.xinlc.eshlc.core.domain;

import com.github.xinlc.eshlc.core.enums.SortDirectionType;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;

/**
 * ES 字段排序默认实现
 *
 * @author Richard
 * @since 2021-04-05
 */
class DefaultFieldSort extends AbstractSort implements IEsFieldSort {

    private Object missing;
    private String unmappedType;

    DefaultFieldSort(String name, SortDirectionType direction) {
        super(name, direction);
    }

    @Override
    public Object missing() {
        return missing;
    }

    @Override
    public IEsFieldSort missing(Object missing) {
        this.missing = missing;
        return this;
    }

    @Override
    public String unmappedType() {
        return unmappedType;
    }

    @Override
    public IEsFieldSort unmappedType(String unmappedType) {
        this.unmappedType = unmappedType;
        return this;
    }

    @Override
    public SortBuilder<?> toSortBuilder() {
        FieldSortBuilder builder = SortBuilders.fieldSort(name())
            .missing(missing)
            .unmappedType(unmappedType)
            .order(direction().getOrder());

        setSortMode(builder::sortMode);
        return builder;
    }
}
