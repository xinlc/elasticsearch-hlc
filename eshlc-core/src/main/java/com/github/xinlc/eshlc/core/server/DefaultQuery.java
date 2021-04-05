package com.github.xinlc.eshlc.core.server;

import com.github.xinlc.eshlc.core.constant.EsConstant;
import com.github.xinlc.eshlc.core.domain.*;
import com.github.xinlc.eshlc.core.exception.EsException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 默认查询实现
 *
 * @author Richard
 * @since 2021-04-03
 */
public class DefaultQuery extends AbstractQuery {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public DefaultQuery(RestHighLevelClient client) {
        super(client);
    }

    @Override
    public IEsQueryAware query(EsQueryContext queryContext) {
        return new DefaultQueryResult(queryContext, client);
    }

    static class DefaultQueryResult extends AbstractQueryAware {

        private final EsQueryContext queryContext;

        private final RestHighLevelClient client;

        private final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        private final SearchRequest searchRequest = new SearchRequest();

        DefaultQueryResult(EsQueryContext queryContext, RestHighLevelClient client) {
            this.queryContext = queryContext;
            this.client = client;
        }

        @Override
        public <T> IEsPage<T> page(Class<T> clz) throws EsException {
            // 查询超时时间
            sourceBuilder.timeout(new TimeValue(EsConstant.FIVE, TimeUnit.SECONDS));

            // 解决total只显示1w的问题
            sourceBuilder.trackTotalHits(true);

            // 分页
            IEsPageable pageable = queryContext.getPageable();
            sourceBuilder.from(pageable.getFrom());
            sourceBuilder.size(pageable.getSize());

            // 构造检索条件
            sourceBuilder.query(this.buildQuery(this.queryContext));

            // 构造高亮
            sourceBuilder.highlighter(this.buildHighlight(this.queryContext));

            // 排序
            this.buildSorts(this.queryContext).forEach(sourceBuilder::sort);

            searchRequest.indices(this.queryContext.getIndexNames());
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse;
            try {
                searchResponse = this.search(this.client, this.searchRequest);
            } catch (IOException e) {
                throw new EsException(e);
            }

            // 结果处理
            SearchHits hits = searchResponse.getHits();
            List<T> results = this.mapHits(clz, this.queryContext, hits);

            return new EsPageImpl<>(hits.getTotalHits().value, results, pageable);
        }
    }
}
