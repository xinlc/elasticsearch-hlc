package com.github.xinlc.eshlc.core.server;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * ES 查询构建器
 *
 * @author Richard
 * @since 2021-03-12
 */
public class EsSearchBuilder {

    private BoolQueryBuilder boolQueryBuilder;

    public EsSearchBuilder() {
    }

    public EsSearchBuilder(BoolQueryBuilder boolQueryBuilder) {
        this.boolQueryBuilder = boolQueryBuilder;
    }

    public static EsSearchBuilder builder() {
        return new EsSearchBuilder(QueryBuilders.boolQuery());
    }

    public static EsSearchBuilder builder(BoolQueryBuilder boolQueryBuilder) {
        return new EsSearchBuilder(boolQueryBuilder);
    }

    /**
     * 等于 =
     *
     * @param field 字段名
     * @param value 字段值
     * @return 构造器
     */
    public EsSearchBuilder eq(String field, Object value) {
        this.boolQueryBuilder.filter(QueryBuilders.termQuery(field, value));
        return this;
    }

    /**
     * 等于 =
     *
     * @param condition 是否添加条件
     * @param field     字段名
     * @param value     字段值
     * @return 构造器
     */
    public EsSearchBuilder eq(boolean condition, String field, Object value) {
        if (condition) {
            return this.eq(field, value);
        }
        return this;
    }

    /**
     * 不等于(not eq）
     *
     * @param field 字段名
     * @param value 字段值
     * @return 构造器
     */
    public EsSearchBuilder ne(String field, Object value) {
        this.boolQueryBuilder.mustNot(QueryBuilders.termQuery(field, value));
        return this;
    }

    /**
     * 不等于(not eq）
     *
     * @param condition 是否添加条件
     * @param field     字段名
     * @param value     字段值
     * @return 构造器
     */
    public EsSearchBuilder ne(boolean condition, String field, Object value) {
        if (condition) {
            return this.ne(field, value);
        }
        return this;
    }

    /**
     * 大于 >
     *
     * @param field 字段名
     * @param value 字段值
     * @return 构造器
     */
    public EsSearchBuilder gt(String field, Object value) {
        this.boolQueryBuilder.filter(QueryBuilders.rangeQuery(field).gt(value));
        return this;
    }

    /**
     * 大于 >
     *
     * @param condition 是否添加条件
     * @param field     字段名
     * @param value     字段值
     * @return 构造器
     */
    public EsSearchBuilder gt(boolean condition, String field, Object value) {
        if (condition) {
            return this.gt(field, value);
        }
        return this;
    }


    /**
     * 大于等于 >=
     *
     * @param field 字段名
     * @param value 字段值
     * @return 构造器
     */
    public EsSearchBuilder ge(String field, Object value) {
        this.boolQueryBuilder.filter(QueryBuilders.rangeQuery(field).gte(value));
        return this;
    }

    /**
     * 大于等于 >=
     *
     * @param condition 是否添加条件
     * @param field     字段名
     * @param value     字段值
     * @return 构造器
     */
    public EsSearchBuilder ge(boolean condition, String field, Object value) {
        if (condition) {
            return this.ge(field, value);
        }
        return this;
    }

    /**
     * 小于 <
     *
     * @param field 字段名
     * @param value 字段值
     * @return 构造器
     */
    public EsSearchBuilder lt(String field, Object value) {
        this.boolQueryBuilder.filter(QueryBuilders.rangeQuery(field).lt(value));
        return this;
    }

    /**
     * 小于 <
     *
     * @param condition 是否添加条件
     * @param field     字段名
     * @param value     字段值
     * @return 构造器
     */
    public EsSearchBuilder lt(boolean condition, String field, Object value) {
        if (condition) {
            return this.lt(field, value);
        }
        return this;
    }

    /**
     * 小于等于 <=
     *
     * @param field 字段名
     * @param value 字段值
     * @return 构造器
     */
    public EsSearchBuilder le(String field, Object value) {
        this.boolQueryBuilder.filter(QueryBuilders.rangeQuery(field).lte(value));
        return this;
    }

    /**
     * 小于等于 <=
     *
     * @param condition 是否添加条件
     * @param field     字段名
     * @param value     字段值
     * @return 构造器
     */
    public EsSearchBuilder le(boolean condition, String field, Object value) {
        if (condition) {
            return this.lt(field, value);
        }
        return this;
    }


    /**
     * BETWEEN 值1 AND 值2
     *
     * @param field 字段名
     * @param begin 开始范围
     * @param end   结束范围
     * @return 构造器
     */
    public EsSearchBuilder between(String field, Object begin, Object end) {
        this.boolQueryBuilder.filter(QueryBuilders.rangeQuery(field).from(begin).to(end));
        return this;
    }

    /**
     * BETWEEN 值1 AND 值2
     *
     * @param condition 是否添加条件
     * @param field     字段名
     * @param begin     开始范围
     * @param end       结束范围
     * @return 构造器
     */
    public EsSearchBuilder between(boolean condition, String field, Object begin, Object end) {
        if (condition) {
            return this.between(field, begin, end);
        }
        return this;
    }

    /**
     * NOT BETWEEN 值1 AND 值2
     *
     * @param field 字段名
     * @param begin 开始范围
     * @param end   结束范围
     * @return 构造器
     */
    public EsSearchBuilder notBetween(String field, Object begin, Object end) {
        this.boolQueryBuilder.mustNot(QueryBuilders.rangeQuery(field).from(begin).to(end));
        return this;
    }

    /**
     * NOT BETWEEN 值1 AND 值2
     *
     * @param condition 是否添加条件
     * @param field     字段名
     * @param begin     开始范围
     * @param end       结束范围
     * @return 构造器
     */
    public EsSearchBuilder notBetween(boolean condition, String field, Object begin, Object end) {
        if (condition) {
            return this.notBetween(field, begin, end);
        }
        return this;
    }

    /**
     * IN
     *
     * @param field  字段名
     * @param values 字段值数组
     * @return 构造器
     */
    public EsSearchBuilder in(String field, Object... values) {
        this.boolQueryBuilder.filter(QueryBuilders.termsQuery(field, values));
        return this;
    }

    /**
     * IN
     *
     * @param condition 是否添加条件
     * @param field     字段名
     * @param values    字段值数组
     * @return 构造器
     */
    public EsSearchBuilder in(boolean condition, String field, Object... values) {
        if (condition) {
            return this.in(field, values);
        }
        return this;
    }

    /**
     * not in
     *
     * @param field  字段名
     * @param values 字段值数组
     * @return 构造器
     */
    public EsSearchBuilder notIn(String field, Object... values) {
        this.boolQueryBuilder.mustNot(QueryBuilders.termsQuery(field, values));
        return this;
    }

    /**
     * not in
     *
     * @param condition 是否添加条件
     * @param field     字段名
     * @param values    字段值数组
     * @return 构造器
     */
    public EsSearchBuilder notIn(boolean condition, String field, Object... values) {
        if (condition) {
            return this.notIn(field, values);
        }
        return this;
    }

    // TODO 以下未完成封装

    public EsSearchBuilder or(String field, Object value) {
        this.boolQueryBuilder.should(QueryBuilders.termQuery(field, value));
//        this.boolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery(field, value));
        return this;
    }


    public EsSearchBuilder sort(String field, SortOrder value) {
//        this.searchSourceBuilder.sort(field, value);
        return this;
    }

//    public EsQueryBuilder page(Integer pageNo, Integer pageSize) {
//        // 上游处理页码
//        // pageNo = (pageNo - 1) * pageSize;
//        this.searchSourceBuilder.from(pageNo).size(pageSize);
//        return this;
//    }

    public EsSearchBuilder matchAll(String field, Object... values) {
        for (Object value : values) {
            this.boolQueryBuilder.filter(QueryBuilders.matchPhrasePrefixQuery(field, value));
        }
        return this;
    }

    public EsSearchBuilder matchOne(String field, Object... values) {
        for (Object value : values) {
            this.boolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery(field, value));
        }
        return this;
    }

    public EsSearchBuilder notMatch(String field, Object value) {
        this.boolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery(field, value));
        return this;
    }

    public EsSearchBuilder fuzzyAll(String field, Fuzziness fuzziness, Object... values) {
        for (Object value : values) {
            this.boolQueryBuilder.filter(QueryBuilders.fuzzyQuery(field, value).fuzziness(fuzziness));
        }
        return this;
    }

    public EsSearchBuilder fuzzyOne(String field, Fuzziness fuzziness, Object... values) {
        for (Object value : values) {
            this.boolQueryBuilder.should(QueryBuilders.fuzzyQuery(field, value).fuzziness(fuzziness));
        }
        return this;
    }

    public EsSearchBuilder notFuzzy(String field, Fuzziness fuzziness, Object value) {
        this.boolQueryBuilder.mustNot(QueryBuilders.fuzzyQuery(field, value).fuzziness(fuzziness));
        return this;
    }

    public EsSearchBuilder isNull(String field) {
        this.boolQueryBuilder.filter(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(field)));
        return this;
    }

    public EsSearchBuilder isNotNull(String field) {
        this.boolQueryBuilder.filter(QueryBuilders.existsQuery(field));
        return this;
    }

    public LocalDateTime toUtcTime(LocalDateTime dateTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli()), ZoneId.of("UTC"));
    }

    public BoolQueryBuilder buildQuery() {
        return this.boolQueryBuilder;
    }
}
