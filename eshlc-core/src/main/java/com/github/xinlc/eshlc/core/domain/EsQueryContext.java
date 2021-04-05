package com.github.xinlc.eshlc.core.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 可执行的上下文类
 *
 * @author Richard
 * @since 2021-04-03
 */
public class EsQueryContext {

    private IEsPageable pageable = new EsPageRequest();

    private EsHighLight highLight = new EsHighLight();

    private EsSort sort = new EsSort();

    private String[] indexNames;

    private String[] includeFields;

    private String[] excludeFields;

    /**
     * 查询语法存储
     */
    List<EsOperatorContext> contexts = new ArrayList<>(0);

    public void addContext(EsOperatorContext context) {
        contexts.add(context);
    }

    public IEsPageable getPageable() {
        return pageable;
    }

    public void setPageable(IEsPageable pageable) {
        this.pageable = pageable;
    }

    public EsHighLight getHighLight() {
        return highLight;
    }

    public void setHighLight(EsHighLight highLight) {
        this.highLight = highLight;
    }

    public EsSort getSort() {
        return sort;
    }

    public void setSort(EsSort sort) {
        this.sort = sort;
    }

    public String[] getIndexNames() {
        return indexNames;
    }

    public void setIndexNames(String[] indexNames) {
        this.indexNames = indexNames;
    }

    public String[] getIncludeFields() {
        return includeFields;
    }

    public void setIncludeFields(String[] includeFields) {
        this.includeFields = includeFields;
    }

    public String[] getExcludeFields() {
        return excludeFields;
    }

    public void setExcludeFields(String[] excludeFields) {
        this.excludeFields = excludeFields;
    }

    public List<EsOperatorContext> getContexts() {
        return contexts;
    }

    public void setContexts(List<EsOperatorContext> contexts) {
        this.contexts = contexts;
    }
}
