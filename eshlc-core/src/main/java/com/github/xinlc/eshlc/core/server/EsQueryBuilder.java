package com.github.xinlc.eshlc.core.server;

import com.github.xinlc.eshlc.core.domain.*;
import com.github.xinlc.eshlc.core.enums.OperatorType;
import com.github.xinlc.eshlc.core.enums.OrderType;
import com.github.xinlc.eshlc.core.enums.QueryLogicType;

/**
 * ES 查询封装
 *
 * @author Richard
 * @since 2021-04-03
 */
public class EsQueryBuilder {

    private final EsQueryContext queryContext = new EsQueryContext();

    /**
     * 新增查询条件
     *
     * @param field  字段
     * @param type   操作类型
     * @param values 条件值
     * @return 查询构造器
     */
    public EsQueryBuilder addCondition(String field, OperatorType type, Object... values) {
        this.addContext(field, type, QueryLogicType.CONDITION, values);
        return this;
    }


    /**
     * 新增类似于 and 操作符
     *
     * @return 查询构造器
     */
    public EsQueryBuilder and() {
        this.addContext(null, null, QueryLogicType.AND, new Object[]{});
        return this;
    }

    /**
     * 添加查询上下文
     *
     * @param field          字段
     * @param type           操作类型
     * @param queryLogicType 查询语法
     * @param values         条件值
     */
    private void addContext(String field, OperatorType type, QueryLogicType queryLogicType, Object... values) {
        EsOperatorContext context = new EsOperatorContext();
        context.setFieldName(field);
        context.setQueryLogicType(queryLogicType);
        context.setValues(values);
        context.setOperateType(type);
        queryContext.addContext(context);
    }

    /**
     * 新增 操作符 or
     *
     * @return 查询构造器
     */
    public EsQueryBuilder or() {
        this.addContext(null, null, QueryLogicType.OR, new Object[]{});
        return this;
    }

    /**
     * 新增左括号
     *
     * @return 查询构造器
     */
    public EsQueryBuilder startInnerCondition() {
        this.addContext(null, null, QueryLogicType.LT, new Object[]{});
        return this;
    }

    /**
     * 新增右边括号
     *
     * @return 查询构造器
     */
    public EsQueryBuilder endInnerCondition() {
        this.addContext(null, null, QueryLogicType.GT, null);
        return this;
    }

    /**
     * 搜索的索引
     *
     * @param indexNames 索引名称
     * @return 查询构造器
     */
    public EsQueryBuilder indexNames(String... indexNames) {
        this.queryContext.setIndexNames(indexNames);
        return this;
    }

    /**
     * 查询排除字段
     *
     * @param excludeFields 字段名称
     * @return 查询构造器
     */
    public EsQueryBuilder excludeFields(String... excludeFields) {
        this.queryContext.setExcludeFields(excludeFields);
        return this;
    }

    /**
     * 查询包含字段
     *
     * @param includeFields 字段名称
     * @return 查询构造器
     */
    public EsQueryBuilder includeFields(String... includeFields) {
        this.queryContext.setIncludeFields(includeFields);
        return this;
    }

    /**
     * 分页
     *
     * @param page 页码
     * @param size 大小
     * @return 查询构造器
     */
    public EsQueryBuilder page(Integer page, Integer size) {
        this.queryContext.setPageable(IEsPageable.of(page, size));
        return this;
    }

    /**
     * 排序
     *
     * @param sort 排序
     * @return 查询构造器
     */
    public EsQueryBuilder order(IEsSort sort) {
        this.queryContext.getSort().sort(sort);
        return this;
    }

    /**
     * 高亮
     *
     * @param field 高亮字段
     * @return 查询构造器
     */
    public EsQueryBuilder page(String field) {
        this.queryContext.getHighLight().field(field);
        return this;
    }

    /**
     * 不带条件查询
     *
     * @return 查询构造器
     */
    public EsQueryBuilder all() {
        return this;
    }

    /**
     * 获取查询上下文
     *
     * @return 查询构造器
     */
    public EsQueryContext getQueryContext() {
        return queryContext;
    }
}
