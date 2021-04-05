package com.github.xinlc.eshlc.core.server;

import cn.hutool.core.lang.Assert;
import com.github.xinlc.eshlc.core.constant.EsConstant;
import com.github.xinlc.eshlc.core.domain.EsBatchResponse;
import com.github.xinlc.eshlc.core.domain.EsDocument;
import com.github.xinlc.eshlc.core.exception.*;
import com.github.xinlc.eshlc.core.factory.EsFramework;
import com.github.xinlc.eshlc.core.util.AnnotationUtil;
import com.github.xinlc.eshlc.core.util.EsTools;
import com.github.xinlc.eshlc.core.util.JsonUtil;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ES 文档服务实现
 *
 * @author Richard
 * @since 2021-03-30
 */
public class DefaultDocumentOperations extends AbstractOperations implements IDocumentOperations {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public DefaultDocumentOperations(EsFramework esFramework) {
        super(esFramework);
    }

    @Override
    public <T> String save(T t) throws EsException {
        String esIndex = AnnotationUtil.getEsIndex(t);
        String esId = AnnotationUtil.getEsId(t);
        return this.save(esIndex, JsonUtil.toJson(t), esId);
    }

    @Override
    public String save(String index, String jsonData) throws EsException {
        return this.save(index, jsonData, null);
    }

    @Override
    public <T> String save(String index, T data) throws EsException {
        return this.save(index, data, null);
    }

    @Override
    public <T> String save(String index, T data, String id) throws EsException {
        return this.save(index, JsonUtil.toJson(data), id);
    }

    @Override
    public String save(String index, String jsonData, String id) throws EsException {
        Assert.notEmpty(index, "ElasticSearch index name cannot empty.");

        IndexResponse indexResponse;
        try {
            indexResponse = esFactory.index(index, jsonData, id);
        } catch (IOException e) {
            logger.debug("Document save failed：", e);
            throw new EsSaveDocException(e.getMessage());
        }

        // 处理响应结果
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            return indexResponse.getId();
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            return indexResponse.getId();
        }
        throw new EsSaveDocException();
    }

    @Override
    public <T> Boolean update(T t) throws EsException {
        String esIndex = AnnotationUtil.getEsIndex(t);
        String esId = AnnotationUtil.getEsId(t);
        return this.update(esIndex, JsonUtil.toJson(t), esId);
    }

    @Override
    public <T> Boolean update(String index, T data, String id) throws EsException {
        return this.update(index, JsonUtil.toJson(data), id);
    }

    @Override
    public Boolean update(String index, String jsonData, String id) throws EsException {
        Assert.notEmpty(index, "ElasticSearch index name cannot empty.");
        Assert.notEmpty(id, "ElasticSearch document id cannot empty.");

        UpdateResponse updateResponse;
        try {
            updateResponse = esFactory.update(index, jsonData, id);
        } catch (IOException e) {
            logger.debug("Document update failed：", e);
            throw new EsUpdateDocException(e.getMessage());
        }

        // 处理响应结果
        if (updateResponse.status() == RestStatus.OK) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public <T> Boolean delete(T t) throws EsException {
        String esIndex = AnnotationUtil.getEsIndex(t);
        String esId = AnnotationUtil.getEsId(t);
        return this.delete(esIndex, esId);
    }

    @Override
    public Boolean delete(String index, String id) throws EsException {
        Assert.notEmpty(index, "ElasticSearch index name cannot empty.");
        Assert.notEmpty(id, "ElasticSearch document id cannot empty.");

        DeleteResponse deleteResponse;
        try {
            deleteResponse = esFactory.delete(index, id);
        } catch (IOException e) {
            logger.debug("Document delete failed：", e);
            throw new EsDeleteDocException(e.getMessage());
        }

        // 处理响应结果
        if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
            return Boolean.TRUE;
        } else if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
            throw new EsDocNotFoundException();
        }
        throw new EsDeleteDocException();
    }

    @Override
    public <T> List<EsBatchResponse> saveBatch(List<T> list) throws EsException {
        return this.saveBatch(list, EsConstant.BULK_SIZE);
    }

    @Override
    public <T> List<EsBatchResponse> saveBatch(List<T> list, int batchSize) throws EsException {
        List<EsDocument> documentList = new ArrayList<>();
        for (T t : list) {
            String esIndex = AnnotationUtil.getEsIndex(t);
            String esId = AnnotationUtil.getEsId(t);

            Assert.notEmpty(esIndex, "ElasticSearch index name cannot empty.");

            EsDocument esDocument = new EsDocument();
            esDocument.setIndex(esIndex);
            esDocument.setId(esId);
            esDocument.setJsonData(JsonUtil.toJson(t));
            documentList.add(esDocument);
        }

        // 批量保存
        return saveBatches(documentList, batchSize);
    }

    /**
     * 批量保存
     *
     * @param documentList 文档集合
     * @param batchSize    批次数量
     * @return 批处理结果
     */
    private List<EsBatchResponse> saveBatches(List<EsDocument> documentList, int batchSize) {
        // 分割集合
        List<List<EsDocument>> lists = EsTools.splitList(documentList, batchSize, true);
        BulkResponse[] bulkResponses = new BulkResponse[lists.size()];

        EsTools.forEach(lists, (i, n) -> bulkResponses[i] = saveBatchPart(n));

        // 处理结果
        return mapBulkResponse(bulkResponses);
    }

    /**
     * 分批保存
     *
     * @param documentList 文档集合
     * @return 响应
     */
    private BulkResponse saveBatchPart(List<EsDocument> documentList) {
        BulkResponse response;

        try {
            response = esFactory.bulkIndex(documentList);
        } catch (IOException e) {
            logger.debug("save batch failed：", e);

            // 这一批都失败，赋空值，由上游处理
            BulkItemResponse[] bulkItemResponses = new BulkItemResponse[documentList.size()];
            response = new BulkResponse(bulkItemResponses, 0L);
        }

        return response;
    }

    /**
     * 处理Bulk结果
     *
     * @param bulkResponses Bulk响应
     * @return 批处理结果
     */
    private List<EsBatchResponse> mapBulkResponse(BulkResponse[] bulkResponses) {
        // 处理Bulk结果
        List<EsBatchResponse> batchResponses = Arrays.stream(bulkResponses)
            .flatMap(bulk -> Arrays.stream(bulk.getItems()))
            .parallel()
            .map(itemResponse -> {
                EsBatchResponse esBatchResponse = new EsBatchResponse();
                if (null != itemResponse) {
                    esBatchResponse.setIndex(itemResponse.getIndex());
                    esBatchResponse.setId(itemResponse.getId());
                    esBatchResponse.setSuccessful(!itemResponse.isFailed());
                    esBatchResponse.setReason(itemResponse.getFailureMessage());
                } else {
                    esBatchResponse.setReason(EsConstant.UNKNOWN);
                }
                return esBatchResponse;
            })
            .collect(Collectors.toList());

        // 设置序列号
        EsTools.forEach(batchResponses, (i, n) -> n.setSeqNo(i));

        return batchResponses;
    }
}
