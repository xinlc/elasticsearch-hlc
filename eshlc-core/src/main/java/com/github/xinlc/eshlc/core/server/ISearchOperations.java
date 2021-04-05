package com.github.xinlc.eshlc.core.server;

import com.github.xinlc.eshlc.core.exception.EsException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;

/**
 * ES 文档查询服务
 *
 * @author Richard
 * @since 2021-04-01
 */
public interface ISearchOperations {

    /**
     * ES 高级客户端原生查询
     *
     * @param searchRequest 搜索请求
     * @return 搜索响应
     * @throws EsException ES 异常
     */
    SearchResponse search(SearchRequest searchRequest) throws EsException;

}
