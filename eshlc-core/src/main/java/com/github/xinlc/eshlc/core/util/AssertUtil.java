package com.github.xinlc.eshlc.core.util;

import java.text.MessageFormat;

/**
 * 断言工具类
 *
 * @author Richard
 * @since 2021-03-26
 */
public final class AssertUtil {
    private AssertUtil() {
    }

    /**
     * 检测表达式若为True则抛出异常
     *
     * @param expression 表达式
     * @throws RuntimeException 如果 {@code expression} 是True抛出
     */
    public static void check(boolean expression) {
        if (expression) {
            throw new RuntimeException();
        }
    }

    /**
     * 检测表达式若为True则抛出异常
     *
     * @param expression 表达式
     * @param e          异常
     * @throws RuntimeException 如果 {@code expression} 是True抛出{@code e}
     */
    public static void check(boolean expression, RuntimeException e) {
        if (expression) {
            throw e;
        }
    }

    /**
     * 检测表达式若为True则抛出异常
     *
     * @param expression   表达式
     * @param errorMessage 异常信息
     * @throws RuntimeException 如果{@code expression} 是True抛出
     */
    public static void check(boolean expression, Object errorMessage) {
        if (expression) {
            throw new RuntimeException(String.valueOf(errorMessage));
        }
    }

    /**
     * 检测表达式若为True则抛出异常
     *
     * @param expression           表达式
     * @param errorMessageTemplate 异常模板
     * @param errorMessageArgs     模板替换的参数
     * @throws RuntimeException 如果{@code expression} 是True抛出
     */
    public static void check(boolean expression, MessageFormat errorMessageTemplate, Object... errorMessageArgs) {
        if (expression) {
            throw new RuntimeException(errorMessageTemplate.format(errorMessageArgs));
        }
    }

    /**
     * 参数数据若为null则抛出异常
     *
     * @param obj 数据
     * @param <T> 数据类型
     * @return T 如果{@code obj} 不是null返回obj
     * @throws RuntimeException 如果{@code obj} 是null抛出
     */
    public static <T> T check(T obj) {
        if (obj == null) {
            throw new RuntimeException();
        }
        return obj;
    }

    /**
     * 参数数据若为null则抛出异常
     *
     * @param obj     数据
     * @param <T>     数据类型
     * @param message 抛出异常的信息
     * @return T 如果{@code obj} 不是null返回obj
     * @throws RuntimeException 如果{@code obj} 是null抛出
     */
    public static <T> T check(T obj, Object message) {
        if (obj == null) {
            throw new RuntimeException(String.valueOf(message));
        }
        return obj;
    }

    /**
     * 参数数据若为null则抛出异常
     *
     * @param obj                  数据
     * @param <T>                  数据的类型
     * @param errorMessageTemplate 异常模板
     * @param errorMessageArgs     模板替换的参数
     * @return T 如果{@code obj} 不是null返回obj
     * @throws RuntimeException 如果{@code obj} 是null抛出
     */
    public static <T> T check(T obj, MessageFormat errorMessageTemplate, Object... errorMessageArgs) {
        if (obj == null) {
            throw new RuntimeException(errorMessageTemplate.format(errorMessageArgs));
        }
        return obj;
    }

    /**
     * 参数数据若为null则抛出异常
     *
     * @param obj 参数数据
     * @param e   异常
     * @param <T> 参数类型
     * @return T 如果{@code obj} 不是null返回obj
     * @throws RuntimeException 如果{@code obj} 是null抛出{@code e}
     */
    public static <T> T check(T obj, RuntimeException e) {
        if (obj == null) {
            throw e;
        }
        return obj;
    }
}
