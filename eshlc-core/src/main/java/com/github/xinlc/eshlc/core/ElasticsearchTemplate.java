package com.github.xinlc.eshlc.core;

import com.github.xinlc.eshlc.core.domain.EsBatchResponse;
import com.github.xinlc.eshlc.core.exception.EsException;
import com.github.xinlc.eshlc.core.server.AbstractElasticsearchTemplate;
import com.github.xinlc.eshlc.core.server.IDocumentOperations;
import com.github.xinlc.eshlc.core.server.ISearchOperations;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;

import java.util.List;

/**
 * ES 服务模板
 *
 * @author Richard
 * @since 2021-03-31
 */
public class ElasticsearchTemplate extends AbstractElasticsearchTemplate {

    public ElasticsearchTemplate(IDocumentOperations documentOperations, ISearchOperations searchOperations) {
        super(documentOperations, searchOperations);
    }

    @Override
    public <T> String save(T t) throws EsException {
        return documentOperations.save(t);
    }

    @Override
    public String save(String index, String jsonData) throws EsException {
        return documentOperations.save(index, jsonData);
    }

    @Override
    public <T> String save(String index, T data) throws EsException {
        return documentOperations.save(index, data);
    }

    @Override
    public <T> String save(String index, T data, String id) throws EsException {
        return documentOperations.save(index, data, id);
    }

    @Override
    public String save(String index, String jsonData, String id) throws EsException {
        return documentOperations.save(index, jsonData, id);
    }

    @Override
    public <T> Boolean update(T t) throws EsException {
        return documentOperations.update(t);
    }

    @Override
    public Boolean update(String index, String jsonData, String id) throws EsException {
        return documentOperations.update(index, jsonData, id);
    }

    @Override
    public <T> Boolean update(String index, T data, String id) throws EsException {
        return documentOperations.update(index, data, id);
    }

    @Override
    public <T> Boolean delete(T t) throws EsException {
        return documentOperations.delete(t);
    }

    @Override
    public Boolean delete(String index, String id) throws EsException {
        return documentOperations.delete(index, id);
    }

    @Override
    public <T> List<EsBatchResponse> saveBatch(List<T> list) throws EsException {
        return documentOperations.saveBatch(list);
    }

    @Override
    public <T> List<EsBatchResponse> saveBatch(List<T> list, int batchSize) throws EsException {
        return documentOperations.saveBatch(list, batchSize);
    }

    @Override
    public SearchResponse search(SearchRequest searchRequest) throws EsException {
        return searchOperations.search(searchRequest);
    }
}
