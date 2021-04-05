package com.github.xinlc.eshlc.core.server;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.SystemClock;
import cn.hutool.core.util.ArrayUtil;
import com.github.xinlc.eshlc.core.constant.EsConstant;
import com.github.xinlc.eshlc.core.domain.EsHighLight;
import com.github.xinlc.eshlc.core.domain.EsOperatorContext;
import com.github.xinlc.eshlc.core.domain.EsQueryContext;
import com.github.xinlc.eshlc.core.domain.EsSort;
import com.github.xinlc.eshlc.core.enums.OrderType;
import com.github.xinlc.eshlc.core.enums.QueryLogicType;
import com.github.xinlc.eshlc.core.util.JsonUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * 查询封装类
 *
 * @author Richard
 * @since 2021-04-03
 */
public abstract class AbstractQueryAware implements IEsQueryAware {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 原生搜索
     *
     * @param client        客户端
     * @param searchRequest 搜索请求
     * @return 搜索响应
     * @throws IOException
     */
    protected SearchResponse search(RestHighLevelClient client, SearchRequest searchRequest) throws IOException {
        logger.debug("正在搜索ES文档...");
        long start = SystemClock.now();
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        long end = SystemClock.now();

        if (logger.isDebugEnabled()) {
            logger.debug("\n[ES] \n\t搜索ES文档完毕，耗时 {} 毫秒\n\t查询请求：{}\n\t结果响应：{}\n[/ES]", end - start, searchRequest, searchResponse);
        }

        return searchResponse;
    }

    /**
     * 构建搜索条件
     *
     * @param queryContext 查询上下文
     * @return 查询条件构造器
     */
    protected QueryBuilder buildQuery(EsQueryContext queryContext) {
        return analyzeQueryCondition(queryContext);
    }

    /**
     * 构造搜索高亮
     *
     * @param queryContext 查询上下文
     * @return 高亮构造器
     */
    protected HighlightBuilder buildHighlight(EsQueryContext queryContext) {

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        EsHighLight highLight = queryContext.getHighLight();

        if (highLight == null) {
            return highlightBuilder;
        }

        // 如果要多个字段高亮,这项要为 false
        highlightBuilder.requireFieldMatch(false)
            .numOfFragments(0)
            .preTags(highLight.getPreTag())
            .postTags(highLight.getPostTag())
        ;

        // 添加业务端高亮
        highLight.getHighLightList().forEach(highlightBuilder::field);

        return highlightBuilder;
    }

    /**
     * 构造搜索排序
     *
     * @param queryContext 查询上下文
     * @return 排序
     */
    protected List<SortBuilder<?>> buildSorts(EsQueryContext queryContext) {
        EsSort sort = queryContext.getSort();
        List<SortBuilder<?>> sorts = new ArrayList<>();

        // 添加默认打分排序
        sorts.add(SortBuilders.scoreSort().order(SortOrder.DESC));

        if (sort == null) {
            return sorts;
        }

        // 添加业务端排序
        sort.getOrders().forEach(o -> {
            FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort(o.getFieldName())
                .order(o.getOrderType() == OrderType.ASC ? SortOrder.ASC : SortOrder.DESC);
            sorts.add(fieldSortBuilder);
        });

        return sorts;
    }

    /**
     * 解析查询条件
     *
     * @param queryContext 查询上下文
     * @return 布尔查询构造器
     */
    protected BoolQueryBuilder analyzeQueryCondition(EsQueryContext queryContext) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        List<EsOperatorContext> temps = new ArrayList<>(
            queryContext.getContexts());
        Stack<String> params = new Stack<>();
        if (CollUtil.isEmpty(temps)) {
            logger.debug("没有任何条件");
            QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
            boolQueryBuilder.must(queryBuilder);
        } else {
            if (temps.size() <= 1) {
                EsOperatorContext context = temps.get(0);
                if (context.getQueryLogicType() == QueryLogicType.CONDITION) {
                    params.push(this.getCalcMessage(context));
                    boolQueryBuilder.must(this.addCondition(context));
                } else {
                    logger.debug("没有任何条件");
                    QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
                    boolQueryBuilder.must(queryBuilder);
                }
            } else {
                // 解析计算后缀表达式
                Stack<EsOperatorContext> stacks = new Stack<>();
                // 后缀表达式
                List<EsOperatorContext> afterStacks = new ArrayList<>();
                for (EsOperatorContext t : temps) {
                    switch (t.getQueryLogicType()) {
                        case GT:
                            while (!stacks.isEmpty()) {
                                EsOperatorContext temp = stacks.peek();
                                if (temp.getQueryLogicType() == QueryLogicType.LT) {
                                    stacks.pop();
                                    break;
                                } else {
                                    temp = stacks.pop();
                                    afterStacks.add(temp);
                                }
                            }
                            break;
                        case LT:
                        case AND:
                        case OR:
                            stacks.push(t);
                            break;
                        case CONDITION:
                            // 参数信息
                            afterStacks.add(t);
                            break;
                        default:
                            break;
                    }
                }

                if (!stacks.isEmpty()) {
                    while (!stacks.isEmpty()) {
                        afterStacks.add(stacks.pop());
                    }
                }

                Optional brackets = afterStacks.parallelStream().filter(t -> t.getQueryLogicType() == QueryLogicType.GT || t.getQueryLogicType() == QueryLogicType.LT).findFirst();
                if (brackets.isPresent()) {
                    throw new IllegalArgumentException("条件括号不匹配！");
                }

                Stack fixStack = new Stack();
                for (EsOperatorContext t : afterStacks) {
                    if (t.getQueryLogicType() == QueryLogicType.AND ||
                        t.getQueryLogicType() == QueryLogicType.OR) {

                        if (fixStack.size() < 2) {
                            throw new IllegalArgumentException("and 或者 or的运算参数不匹配");
                        }

                        Object first = fixStack.pop();
                        EsOperatorContext second = (EsOperatorContext) fixStack.pop();
                        QueryBuilder resultBuild;
                        QueryBuilder firstBuilder;
                        QueryBuilder secondBuilder = this.addCondition(second);

                        if (first instanceof EsOperatorContext) {
                            firstBuilder = this.addCondition((EsOperatorContext) first);
                        } else {
                            firstBuilder = (QueryBuilder) first;
                        }

                        String calcMessageSecond = this.getCalcMessage(second);
                        String calcFistMessage = null;

                        if (first instanceof EsOperatorContext) {
                            params.push(")");
                            params.push(calcMessageSecond);
                            params.push(t.getQueryLogicType().getMessage());
                            calcFistMessage = this.getCalcMessage((EsOperatorContext) first);
                        } else {
                            params.push(t.getQueryLogicType().getMessage());
                            params.push(")");
                            params.push(calcMessageSecond);
                        }

                        if (t.getQueryLogicType() == QueryLogicType.AND) {
                            resultBuild = QueryBuilders.boolQuery().must(firstBuilder).must(secondBuilder);
                        } else {
                            resultBuild = QueryBuilders.boolQuery().should(firstBuilder).should(secondBuilder);
                        }

                        if (calcFistMessage != null) {
                            params.push(calcFistMessage);
                        }

                        params.push("(");
                        fixStack.push(resultBuild);
                    } else {
                        fixStack.push(t);
                    }
                }
                boolQueryBuilder = (BoolQueryBuilder) fixStack.pop();
            }
            this.printMessage(params);
        }
        return boolQueryBuilder;
    }

    /**
     * 添加原生查询条件
     *
     * @param context 操作上下文
     * @return 查询条件
     */
    protected QueryBuilder addCondition(EsOperatorContext context) {
        QueryBuilder queryBuilder = null;
        switch (context.getOperateType()) {
            case EQUALS:
                checkValue(1, context);
                queryBuilder = QueryBuilders.termsQuery(context.getFieldName(), context.getValues());
                break;
            case LIKE:
                checkValue(1, context);
                queryBuilder = QueryBuilders.wildcardQuery(context.getFieldName(), "*".concat(context.getValues()[0].toString()).concat("*"));
                break;
            case GT:
                checkValue(1, context);
                queryBuilder = QueryBuilders.rangeQuery(context.getFieldName()).
                    from(context.getValues()[0], false);
                break;
            case LT:
                checkValue(1, context);
                queryBuilder = QueryBuilders.rangeQuery(context.getFieldName()).
                    to(context.getValues()[0], false);
                break;
            case NOTEQUALS:
                checkValue(1, context);
                queryBuilder = QueryBuilders.boolQuery().mustNot(QueryBuilders.termsQuery(context.getFieldName(), context.getValues()));
                break;
            case GTE:
                checkValue(1, context);
                queryBuilder = QueryBuilders.rangeQuery(context.getFieldName()).
                    from(context.getValues()[0], true);
                break;
            case LTE:
                checkValue(1, context);
                queryBuilder = QueryBuilders.rangeQuery(context.getFieldName()).
                    to(context.getValues()[0], true);
                break;
            case BETWEENIN_CLUDE:
                checkValue(2, context);
                queryBuilder = QueryBuilders.rangeQuery(context.getFieldName()).
                    from(context.getValues()[0], true).to(context.getValues()[1],
                    true);
                break;
            case BETWEEN_INCLUDE_LEFT:
                checkValue(2, context);
                queryBuilder = QueryBuilders.rangeQuery(context.getFieldName()).
                    from(context.getValues()[0], true).to(context.getValues()[1],
                    false);
                break;
            case BETWEEN_INCLUDE_RIGHT:
                checkValue(2, context);
                queryBuilder = QueryBuilders.rangeQuery(context.getFieldName()).
                    from(context.getValues()[0], false).to(context.getValues()[1],
                    true);
                break;
            case BETWEEN_NOT_INCLUDE:
                checkValue(2, context);
                queryBuilder = QueryBuilders.rangeQuery(context.getFieldName()).
                    from(context.getValues()[0], false).to(context.getValues()[1],
                    false);
                break;
            case REGEX:
                checkValue(1, context);
                queryBuilder = QueryBuilders.regexpQuery(context.getFieldName(), context.getValues()[0].toString());
                break;
            default:
                break;
        }
        return queryBuilder;
    }

    /**
     * 检查查询条件值
     *
     * @param length  长度
     * @param context 上下文
     */
    protected void checkValue(int length, EsOperatorContext context) {
        if (context.getValues().length < length) {
            throw new IllegalArgumentException(context.getFieldName().concat(" values argument length less than ").concat(length + ""));
        }
        for (Object value : context.getValues()) {
            if (value == null) {
                throw new IllegalArgumentException(context.getFieldName().concat(" exists argument value  is null "));
            }
        }
    }

    /**
     * 计算查询条件信息
     *
     * @param context 上下文
     * @return 信息
     */
    protected String getCalcMessage(EsOperatorContext context) {
        MessageFormat format = new MessageFormat(context.getOperateType().getMessage());
        Object[] messages = new Object[context.getValues().length + 1];
        messages[0] = context.getFieldName();
        System.arraycopy(context.getValues(), 0, messages, 1, context.getValues().length);
        return format.format(messages);
    }

    /**
     * 打印查询条件日志
     *
     * @param params 查询参数
     */
    protected void printMessage(Stack<String> params) {
        if (logger.isDebugEnabled() && !params.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            while (!params.isEmpty()) {
                builder.append(" ").append(params.pop()).append(" ");
            }
            logger.debug("搜索条件：" + builder.toString());
        }
    }

    /**
     * 处理搜索结果
     *
     * @param clz          结果class
     * @param queryContext 查询上下文
     * @param searchHits   查询结果
     * @param <T>          结果类型
     * @return 结果集合
     */
    protected <T> List<T> mapHits(Class<T> clz, EsQueryContext queryContext, SearchHits searchHits) {
        if (searchHits == null) {
            return null;
        }
        List<T> results = new ArrayList<>();
        SearchHit[] hits = searchHits.getHits();
        if (ArrayUtil.isNotEmpty(searchHits)) {
            for (SearchHit hit : hits) {
                // 解析 JSON 结果
                T result = JsonUtil.parse(hit.getSourceAsString(), clz);
                results.add(result);
                // 处理高亮
                mapHighlight(result, queryContext, hit.getHighlightFields());
            }
        }
        return results;
    }

    /**
     * 处理高亮结果
     *
     * @param result          处理结果
     * @param queryContext    查询上下文
     * @param highlightFields 高亮字段
     * @param <T>             结果类型
     */
    protected <T> void mapHighlight(T result, EsQueryContext queryContext, Map<String, HighlightField> highlightFields) {
        if (CollUtil.isEmpty(highlightFields)) {
            return;
        }
        EsHighLight highLight = queryContext.getHighLight();
        String preTag = highLight.getPreTag();
        String postTag = highLight.getPostTag();

        /*
            ES 复杂对象数组会扁平化处理，对象位置边界丢失，无法知道数组高亮内容所在位置
            解决：
            1. 官方办法是使用嵌套对象，查询使用嵌套高亮查询，这样位置不会丢失。
            2. 高亮字段值替换掉前后缀，跟原字段值进行比较，相等把高亮字段set到原字段。这种方式适合小文本，
                注意：numOfFragments(0) 高亮不分片段，好实现全文本比较。
         */
        highlightFields.forEach((k, v) -> {
            // 高亮字符串
            String hltStr = v.fragments()[0].toString();

            // 判断嵌套对象, 如：jobList.jobContent
            if (!k.contains(EsConstant.DOT)) {
                // 普通字段，直接覆盖
                BeanUtil.setFieldValue(result, k, hltStr);
                return;
            }

            // 处理嵌套对象
            String[] attributes = k.split(EsConstant.DOT_REGEX);
            String firstAttribute = attributes[0];
            String secondAttribute = attributes[1];
            Object property = BeanUtil.getFieldValue(result, firstAttribute);

            //  嵌套集合对象
            if (property instanceof Collection) {
                for (Text fragment : v.getFragments()) {
                    // 替换高亮 tag，得到原值
                    String originValue = fragment.toString()
                        .replace(preTag, "")
                        .replace(postTag, "");

                    // 遍历集合对象，对比高亮值
                    ((Collection<?>) property).forEach(i -> {
                        Object fieldValue = BeanUtil.getFieldValue(i, secondAttribute);
                        // 高亮值跟原值一致，赋值高亮值
                        if (Objects.equals(originValue, fieldValue)) {
                            BeanUtil.setFieldValue(i, secondAttribute, fragment.toString());
                        }
                    });
                }
            } else {
                // 嵌套字段，直接覆盖
                BeanUtil.setFieldValue(property, secondAttribute, hltStr);
            }
        });
    }

}
