package com.github.xinlc.eshlc.core.domain;

import com.github.xinlc.eshlc.core.enums.SortDirectionType;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;

/**
 * ES 脚本排序默认实现
 *
 * @author Richard
 * @since 2021-04-05
 */
class DefaultScriptSort extends AbstractSort implements IEsScriptSort {

    private final IEsScript script;
    private final ScriptSortBuilder.ScriptSortType sortType;
    private final SortDirectionType sortDirection;

    DefaultScriptSort(IEsScript script, ScriptSortBuilder.ScriptSortType sortType, SortDirectionType sortDirection) {
        this.script = script;
        this.sortType = sortType;
        this.sortDirection = sortDirection;
    }

    @Override
    public IEsScript script() {
        return script;
    }

    @Override
    public ScriptSortBuilder.ScriptSortType sortType() {
        return sortType;
    }

    @Override
    public SortBuilder<?> toSortBuilder() {
        ScriptSortBuilder builder = SortBuilders.scriptSort(script.toEsScript(), sortType).order(sortDirection.getOrder());

        setSortMode(builder::sortMode);
        return builder;
    }
}
