package com.github.xinlc.eshlc.core.enums;

/**
 * 支持的操作类型
 *
 * @author Richard
 * @since 2021-04-03
 */
public enum OperatorType {

    /**
     * 等于
     **/
    EQUALS("{0}={1}"),

    /**
     * 匹配
     */
    LIKE("{0} like %{1}%"),

    /**
     * 大于
     */
    GT("{0}>{1}"),

    /**
     * 小于
     */
    LT("{0}<{1}"),

    /**
     * 不相等
     */
    NOTEQUALS("{0}!={1}"),

    /**
     * 大于等于
     */
    GTE("{0}>={1}"),

    /**
     * 小于等于
     */
    LTE("{0}<={1}"),

    /**
     * 两者之间包含
     */
    BETWEENIN_CLUDE("{0}>={1} and {0}<={2}"),

    /**
     * 两者之间包含左
     */
    BETWEEN_INCLUDE_LEFT("{0}>={1} and {0}<{2}"),

    /**
     * 两者之间包含右边
     */
    BETWEEN_INCLUDE_RIGHT("{0}>{1} and {0}=<{2}"),

    /**
     * 两者之间不包含
     */
    BETWEEN_NOT_INCLUDE("{0}>{1} and {0}<{2}"),

    /**
     * 正则表达式
     */
    REGEX("{0} regex {1}");

    private final String message;

    OperatorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
