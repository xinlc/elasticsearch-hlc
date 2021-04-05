package com.github.xinlc.eshlc.core.server;

import com.github.xinlc.eshlc.core.domain.EsQueryContext;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * 扩展查询类
 *
 * @author Richard
 * @since 2021-04-03
 */
public abstract class AbstractQuery {

    protected RestHighLevelClient client;

    /**
     * 包装 RestHighLevelClient
     *
     * @param client ES客户端
     */
    public AbstractQuery(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * 查询抽象
     *
     * @param queryContext 查询全文
     * @return 查询实现
     */
    public abstract IEsQueryAware query(EsQueryContext queryContext);

}
