package com.github.xinlc.eshlc.core.enums;

/**
 * 查询语法
 *
 * @author Richard
 * @since 2021-04-03
 */
public enum QueryLogicType {

    /**
     * and 并且
     */
    AND("and"),

    /**
     * OR符号
     */
    OR("or"),

    /**
     * 左括号
     */
    LT("("),

    /**
     * 右括号
     */
    GT(")"),

    /**
     * CONDITION
     */
    CONDITION("");

    private final String message;

    QueryLogicType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
