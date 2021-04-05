package com.github.xinlc.eshlc.core.server;

import cn.hutool.core.lang.Assert;

/**
 * ES 抽象服务模板
 *
 * @author Richard
 * @since 2021-03-31
 */
public abstract class AbstractElasticsearchTemplate implements IElasticsearchOperations {

    protected final IDocumentOperations documentOperations;
    protected final ISearchOperations searchOperations;

    public AbstractElasticsearchTemplate(IDocumentOperations documentOperations, ISearchOperations searchOperations) {
        Assert.notNull(documentOperations, "DocumentOperations must not be null!");
        Assert.notNull(searchOperations, "SearchOperations must not be null!");

        this.documentOperations = documentOperations;
        this.searchOperations = searchOperations;
    }
}
