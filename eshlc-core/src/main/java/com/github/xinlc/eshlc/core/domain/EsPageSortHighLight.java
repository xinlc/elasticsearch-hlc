package com.github.xinlc.eshlc.core.domain;

/**
 * ES 分页+排序+高亮请求
 *
 * @author Richard
 * @since 2021-04-01
 */
public class EsPageSortHighLight extends EsPageRequest {

    private EsSort sort;
    private EsHighLight highLight;

    public EsPageSortHighLight() {
    }

    public EsPageSortHighLight(int currentPage, int pageSize) {
        this.setPage(currentPage);
        this.setSize(pageSize);
    }

    public EsPageSortHighLight(int currentPage, int pageSize, EsSort sort) {
        this.setPage(currentPage);
        this.setSize(pageSize);
        this.sort = sort;
    }

    public EsSort getSort() {
        return sort;
    }

    public void setSort(EsSort sort) {
        this.sort = sort;
    }

    public EsHighLight getHighLight() {
        return highLight;
    }

    public void setHighLight(EsHighLight highLight) {
        this.highLight = highLight;
    }

    @Override
    public String toString() {
        return "EsPageSortHighLight{" +
            "sort=" + sort +
            ", highLight=" + highLight +
            '}';
    }
}
