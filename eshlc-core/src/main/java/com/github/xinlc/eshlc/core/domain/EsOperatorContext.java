package com.github.xinlc.eshlc.core.domain;

import com.github.xinlc.eshlc.core.enums.OperatorType;
import com.github.xinlc.eshlc.core.enums.QueryLogicType;

import java.util.Arrays;

/**
 * 查询条件封装类
 *
 * @author Richard
 * @since 2021-04-03
 */
public class EsOperatorContext {

    /**
     * 语法
     */
    private QueryLogicType queryLogicType;

    /**
     * 域
     */
    private String fieldName;

    /**
     * 值
     */
    private Object[] values;

    /**
     * 操作类型
     */
    private OperatorType operateType;

    public QueryLogicType getQueryLogicType() {
        return queryLogicType;
    }

    public void setQueryLogicType(QueryLogicType queryLogicType) {
        this.queryLogicType = queryLogicType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public OperatorType getOperateType() {
        return operateType;
    }

    public void setOperateType(OperatorType operateType) {
        this.operateType = operateType;
    }

    @Override
    public String toString() {
        return "EsOperatorContext{" +
            "queryType=" + queryLogicType +
            ", fieldName='" + fieldName + '\'' +
            ", values=" + Arrays.toString(values) +
            ", operateType=" + operateType +
            '}';
    }
}
