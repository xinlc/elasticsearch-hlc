package com.github.xinlc.eshlc.core.domain;

import com.github.xinlc.eshlc.core.enums.SortDirectionType;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;

/**
 * ES 评分排序默认实现
 *
 * @author Richard
 * @since 2021-04-05
 */
class DefaultScoreSort extends AbstractSort implements IEsScoreSort {

    DefaultScoreSort(SortDirectionType direction) {
        super(null, direction);
    }

    @Override
    public SortBuilder<?> toSortBuilder() {
        ScoreSortBuilder builder = SortBuilders.scoreSort();
        builder.order(direction().getOrder());
        return builder;
    }
}
