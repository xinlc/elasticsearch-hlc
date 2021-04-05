package com.github.xinlc.eshlc.core.factory;

import com.github.xinlc.eshlc.core.domain.EsDocument;
import com.github.xinlc.eshlc.core.domain.IndexProperties;
import com.github.xinlc.eshlc.core.server.AbstractQuery;
import com.github.xinlc.eshlc.core.server.EsQueryBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.List;

/**
 * ES 工厂接口
 *
 * @author Richard
 * @since 2021-03-26
 */
public interface IEsFactory {

    /**
     * 工厂名字
     *
     * @param name 名字
     */
    void setName(String name);

    /**
     * ES 客户端
     *
     * @param client ES客户端
     */
    void setClient(RestHighLevelClient client);

    /**
     * 获取查询构造器
     *
     * @return ES SearchBuilder
     */
    EsQueryBuilder getBuilder();

    /**
     * 获取ES查询实现类
     *
     * @return AbstractESQuery
     */
    AbstractQuery getQuery();

    /**
     * 获取客户端
     *
     * @return 高阶客户端
     */
    RestHighLevelClient getClient();

    /**
     * 索引文档
     *
     * @param index    索引名称
     * @param jsonData json数据
     * @param id       文档ID，为空 则自动生成
     * @return IndexResponse
     * @throws IOException IO异常
     */
    IndexResponse index(String index, String jsonData, String id) throws IOException;

    /**
     * 修改文档
     *
     * @param index    索引名称
     * @param jsonData json数据
     * @param id       文档ID
     * @return UpdateResponse
     * @throws IOException IO异常
     */
    UpdateResponse update(String index, String jsonData, String id) throws IOException;

    /**
     * 通过ID删除文档
     *
     * @param index 索引名称
     * @param id    文档ID
     * @return DeleteResponse
     * @throws IOException IO异常
     */
    DeleteResponse delete(String index, String id) throws IOException;

    /**
     * 批量索引文档
     *
     * @param documentList 文档集合
     * @return BulkResponse
     * @throws IOException IO异常
     */
    BulkResponse bulkIndex(List<EsDocument> documentList) throws IOException;

    /**
     * 批量更新文档
     *
     * @param documentList 文档集合
     * @return BulkResponse
     * @throws IOException IO异常
     */
    BulkResponse bulkUpdate(List<EsDocument> documentList) throws IOException;

    /**
     * 批量删除文档
     *
     * @param documentList 文档集合
     * @return BulkResponse
     * @throws IOException IO异常
     */
    BulkResponse bulkDelete(List<EsDocument> documentList) throws IOException;

    /**
     * ES 高级客户原生查询
     *
     * @param searchRequest 搜索请求
     * @return 搜索响应
     * @throws IOException IO异常
     */
    SearchResponse search(SearchRequest searchRequest) throws IOException;

    /**
     * 更新索引属性
     *
     * @param index      索引名称
     * @param properties 索引属性
     * @return AcknowledgedResponse
     * @throws Exception 更新异常
     */
    AcknowledgedResponse updateIndexProperties(String index, IndexProperties properties) throws Exception;

    /**
     * 删除索引及数据
     *
     * @param index 索引名称
     * @return AcknowledgedResponse
     * @throws IOException IO异常
     */
    AcknowledgedResponse delete(String index) throws IOException;
}
