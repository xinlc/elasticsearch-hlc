package com.github.xinlc.eshlc.core.server;

import com.github.xinlc.eshlc.core.domain.EsBatchResponse;
import com.github.xinlc.eshlc.core.exception.EsException;

import java.util.List;

/**
 * ES 文档服务
 *
 * @author Richard
 * @since 2021-03-30
 */
public interface IDocumentOperations {

    /**
     * 新增文档
     * <p>
     * 会解析 @EsTable 标识的实体
     *
     * @param t   新增的数据
     * @param <T> 文档数据类型
     * @return 文档ID
     * @throws EsException ES异常
     */
    <T> String save(T t) throws EsException;

    /**
     * 新增文档
     *
     * @param index    索引名称
     * @param jsonData json数据
     * @return 文档ID
     * @throws EsException ES异常
     */
    String save(String index, String jsonData) throws EsException;

    /**
     * 新增文档
     *
     * @param index 索引名称
     * @param data  文档
     * @param <T>   文档数据类型
     * @return 文档ID
     * @throws EsException ES异常
     */
    <T> String save(String index, T data) throws EsException;

    /**
     * 新增文档
     *
     * @param index 索引名称
     * @param data  文档数据
     * @param id    文档ID
     * @param <T>   文档数据类型
     * @return 文档ID
     * @throws EsException ES异常
     */
    <T> String save(String index, T data, String id) throws EsException;

    /**
     * 新增文档
     *
     * @param index    索引名称
     * @param jsonData json数据
     * @param id       文档ID
     * @return 文档ID
     * @throws EsException ES异常
     */
    String save(String index, String jsonData, String id) throws EsException;

    /**
     * 修改文档
     * <p>
     * 会解析 @EsTable 标识的实体
     *
     * @param t   修改文档实体
     * @param <T> 文档数据类型
     * @return 是否成功
     * @throws EsException ES异常
     */
    <T> Boolean update(T t) throws EsException;

    /**
     * 修改文档
     *
     * @param index    索引名称
     * @param jsonData json数据
     * @param id       文档ID
     * @return 是否成功
     * @throws EsException ES异常
     */
    Boolean update(String index, String jsonData, String id) throws EsException;

    /**
     * 修改文档
     *
     * @param index 索引名称
     * @param data  文档数据
     * @param id    文档ID
     * @param <T>   文档数据类型
     * @return 是否成功
     * @throws EsException ES异常
     */
    <T> Boolean update(String index, T data, String id) throws EsException;

    /**
     * 删除文档
     * <p>
     * 会解析 @EsTable 标识的实体
     *
     * @param t   文档实体
     * @param <T> 文档数据类型
     * @return 是否成功
     * @throws EsException ES异常
     */
    <T> Boolean delete(T t) throws EsException;

    /**
     * 新增文档
     *
     * @param index 索引名称
     * @param id    文档ID
     * @return 是否成功
     * @throws EsException ES异常
     */
    Boolean delete(String index, String id) throws EsException;

    /**
     * 批量新增文档
     * <p>
     * 会解析 @EsTable 标识的实体
     *
     * @param list 新增的数据
     * @param <T>  文档数据类型
     * @return 批处理结果
     * @throws EsException ES异常
     */
    <T> List<EsBatchResponse> saveBatch(List<T> list) throws EsException;

    /**
     * 批量新增文档
     * <p>
     * 会解析 @EsTable 标识的实体
     *
     * @param list      新增的数据
     * @param batchSize 批次数量
     * @param <T>       文档数据类型
     * @return 批处理结果
     * @throws EsException ES异常
     */
    <T> List<EsBatchResponse> saveBatch(List<T> list, int batchSize) throws EsException;

    /**
     * 批量更新文档
     * <p>
     * 会解析 @EsTable 标识的实体
     *
     * @param list 更新的数据
     * @param <T>  文档数据类型
     * @return 批处理结果
     * @throws EsException ES异常
     */
    <T> List<EsBatchResponse> updateBatch(List<T> list) throws EsException;

    /**
     * 批量更新文档
     * <p>
     * 会解析 @EsTable 标识的实体
     *
     * @param list      更新的数据
     * @param batchSize 批次数量
     * @param <T>       文档数据类型
     * @return 批处理结果
     * @throws EsException ES异常
     */
    <T> List<EsBatchResponse> updateBatch(List<T> list, int batchSize) throws EsException;

    /**
     * 批量删除文档
     * <p>
     * 会解析 @EsTable 标识的实体
     *
     * @param list 删除的数据
     * @param <T>  文档数据类型
     * @return 批处理结果
     * @throws EsException ES异常
     */
    <T> List<EsBatchResponse> deleteBatch(List<T> list) throws EsException;

    /**
     * 批量删除文档
     * <p>
     * 会解析 @EsTable 标识的实体
     *
     * @param list      删除的数据
     * @param batchSize 批次数量
     * @param <T>       文档数据类型
     * @return 批处理结果
     * @throws EsException ES异常
     */
    <T> List<EsBatchResponse> deleteBatch(List<T> list, int batchSize) throws EsException;

    /**
     * 批量删除文档
     *
     * @param index 索引名称
     * @param ids   文档ID集合
     * @return 批处理结果
     * @throws EsException ES异常
     */
    List<EsBatchResponse> deleteBatch(String index, List<String> ids) throws EsException;

    /**
     * 批量删除文档
     *
     * @param index     索引名称
     * @param ids       文档ID集合
     * @param batchSize 批次数量
     * @return 批处理结果
     * @throws EsException ES异常
     */
    List<EsBatchResponse> deleteBatch(String index, List<String> ids, int batchSize) throws EsException;

}
