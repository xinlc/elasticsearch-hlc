package com.github.xinlc.eshlc.core.domain;

import com.github.xinlc.eshlc.core.enums.SortDirectionType;
import org.elasticsearch.search.sort.SortMode;

import java.util.function.Consumer;

/**
 * ES 抽象排序
 *
 * @author Richard
 * @since 2021-04-05
 */
abstract class AbstractSort implements IEsSort {

    private final String name;
    private final SortDirectionType direction;
    private SortMode sortMode;

    protected AbstractSort() {
        this(null, null);
    }

    AbstractSort(String name, SortDirectionType direction) {
        this.name = name;
        this.direction = direction;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public SortDirectionType direction() {
        return direction;
    }

    @Override
    public SortMode sortMode() {
        return sortMode;
    }

    @Override
    public IEsSort sortMode(SortMode sortMode) {
        this.sortMode = sortMode;
        return this;
    }

    protected void setSortMode(Consumer<SortMode> consumer) {
        if (sortMode != null) {
            consumer.accept(sortMode);
        }
    }
}
