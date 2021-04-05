package com.github.xinlc.eshlc.core.factory;

import cn.hutool.core.date.SystemClock;
import cn.hutool.core.util.StrUtil;
import com.github.xinlc.eshlc.core.domain.EsDocument;
import com.github.xinlc.eshlc.core.domain.IndexProperties;
import com.github.xinlc.eshlc.core.server.AbstractQuery;
import com.github.xinlc.eshlc.core.server.DefaultQuery;
import com.github.xinlc.eshlc.core.server.EsQueryBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * ES 工厂实现类
 *
 * @author Richard
 * @since 2021-03-26
 */
public class EsFactory implements IEsFactory {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String name;
    private RestHighLevelClient client;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }

    @Override
    public EsQueryBuilder getBuilder() {
        return new EsQueryBuilder();
    }

    @Override
    public AbstractQuery getQuery() {
        return new DefaultQuery(this.client);
    }

    @Override
    public RestHighLevelClient getClient() {
        return client;
    }

    @Override
    public IndexResponse index(String index, String jsonData, String id) throws IOException {
        // 构建索引文档请求
        IndexRequest indexRequest = new IndexRequest(index);
        if (StrUtil.isNotBlank(id)) {
            indexRequest.id(id);
        }
        indexRequest.source(jsonData, XContentType.JSON);

        logger.debug("正在索引ES文档，request：{}", indexRequest);
        long start = SystemClock.now();
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        long end = SystemClock.now();
        logger.debug("索引ES文档完毕，response：{} , 耗时 {} 毫秒", response, end - start);

        return response;
    }

    @Override
    public UpdateResponse update(String index, String jsonData, String id) throws IOException {
        // 构建更新请求
        UpdateRequest updateRequest = new UpdateRequest(index, id);
        updateRequest.doc(jsonData, XContentType.JSON);

        logger.debug("正在修改ES文档，request：{}", updateRequest);
        long start = SystemClock.now();
        UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
        long end = SystemClock.now();
        logger.debug("修改ES文档完毕，response：{} , 耗时 {} 毫秒", response, end - start);

        return response;
    }

    @Override
    public DeleteResponse delete(String index, String id) throws IOException {
        // 构建删除请求
        DeleteRequest deleteRequest = new DeleteRequest(index, id);

        logger.debug("正在删除ES文档，request：{}", deleteRequest);
        long start = SystemClock.now();
        DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
        long end = SystemClock.now();
        logger.debug("删除ES文档完毕，response：{} , 耗时 {} 毫秒", response, end - start);

        return response;
    }

    @Override
    public BulkResponse bulkIndex(List<EsDocument> documentList) throws IOException {
        // 构建批量请求
        BulkRequest bulkRequest = new BulkRequest();
        for (EsDocument esDocument : documentList) {
            IndexRequest indexRequest = new IndexRequest(esDocument.getIndex());
            if (StrUtil.isNotBlank(esDocument.getId())) {
                indexRequest.id(esDocument.getId());
            }
            indexRequest.source(esDocument.getJsonData(), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }

        logger.debug("正在批量索引ES文档，bulk size：{}", bulkRequest.requests().size());
        long start = SystemClock.now();
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        long end = SystemClock.now();
        logger.debug("批量索引ES文档完毕，response size：{} , 耗时 {} 毫秒", response.getItems().length, end - start);

        return response;
    }

    @Override
    public BulkResponse bulkUpdate(List<EsDocument> documentList) throws IOException {
        // 构建批量请求
        BulkRequest bulkRequest = new BulkRequest();
        for (EsDocument esDocument : documentList) {
            UpdateRequest updateRequest = new UpdateRequest(esDocument.getIndex(), esDocument.getId());
            updateRequest.doc(esDocument.getJsonData(), XContentType.JSON);
            bulkRequest.add(updateRequest);
        }

        logger.debug("正在批量更新ES文档，bulk size：{}", bulkRequest.requests().size());
        long start = SystemClock.now();
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        long end = SystemClock.now();
        logger.debug("批量更新ES文档完毕，response size：{} , 耗时 {} 毫秒", response.getItems().length, end - start);

        return response;
    }

    @Override
    public BulkResponse bulkDelete(List<EsDocument> documentList) throws IOException {
        // 构建批量请求
        BulkRequest bulkRequest = new BulkRequest();
        for (EsDocument esDocument : documentList) {
            DeleteRequest deleteRequest = new DeleteRequest(esDocument.getIndex(), esDocument.getId());
            bulkRequest.add(deleteRequest);
        }

        logger.debug("正在批量删除ES文档，bulk size：{}", bulkRequest.requests().size());
        long start = SystemClock.now();
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        long end = SystemClock.now();
        logger.debug("批量删除ES文档完毕，response size：{} , 耗时 {} 毫秒", response.getItems().length, end - start);

        return response;
    }

    @Override
    public SearchResponse search(SearchRequest searchRequest) throws IOException {

        logger.debug("正在搜索ES文档...");
        long start = SystemClock.now();
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        long end = SystemClock.now();

        if (logger.isDebugEnabled()) {
            logger.debug("\n[ES] \n\t搜索ES文档完毕，耗时 {} 毫秒\n\t查询请求：{}\n\t结果响应：{}\n[/ES]", end - start, searchRequest, searchResponse);
        }

        return searchResponse;
    }

    @Override
    public AcknowledgedResponse delete(String index) throws IOException {
        // 构建删除索引请求
        DeleteIndexRequest deleteRequest = new DeleteIndexRequest(index);

        logger.debug("正在删除ES索引，request：{}", deleteRequest);
        long start = SystemClock.now();
        AcknowledgedResponse response = client.indices().delete(deleteRequest, RequestOptions.DEFAULT);
        long end = SystemClock.now();
        logger.debug("删除ES索引完毕，response：{} , 耗时 {} 毫秒", response, end - start);

        return response;
    }

    @Override
    public AcknowledgedResponse updateIndexProperties(String index, IndexProperties properties) throws Exception {
        // 构建修改Mapping请求
        PutMappingRequest request = new PutMappingRequest(index);
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                properties.getProperties().forEach((k, v) -> {
                    try {
                        builder.startObject(k);
                        {
                            v.getParameters().forEach((ik, iv) -> {
                                try {
                                    builder.field(ik, iv);
                                } catch (IOException e) {
                                    logger.error(e.getMessage(), e);
                                }
                            });
                        }
                        builder.endObject();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                });
            }
            builder.endObject();
        }
        builder.endObject();
        request.source(builder);

        AcknowledgedResponse response = this.getClient().indices().putMapping(request, RequestOptions.DEFAULT);
        logger.debug("更新索引响应:{}", response);

        return response;
    }
}
