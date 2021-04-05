package com.github.xinlc.eshlc.core.server;

import com.github.xinlc.eshlc.core.exception.EsException;
import com.github.xinlc.eshlc.core.exception.EsSearchException;
import com.github.xinlc.eshlc.core.factory.EsFramework;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * ES 文档查询服务实现
 *
 * @author Richard
 * @since 2021-04-01
 */
public class DefaultSearchOperations extends AbstractOperations implements ISearchOperations {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public DefaultSearchOperations(EsFramework esFramework) {
        super(esFramework);
    }

    @Override
    public SearchResponse search(SearchRequest searchRequest) throws EsException {
        SearchResponse searchResponse;

        try {
            searchResponse = esFactory.search(searchRequest);
        } catch (IOException e) {
            logger.debug("ES search failed：", e);
            throw new EsSearchException(e.getMessage());
        }

        return searchResponse;
    }
}
