package com.github.xinlc.eshlc.core.function;

import com.github.xinlc.eshlc.core.constant.EsConstant;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * Lambda 方式获取字段名
 *
 * @author Richard
 * @since 2021-03-12
 */
@FunctionalInterface
public interface GetFunction<T, R> extends Function<T, R>, Serializable {

    /**
     * 获取字段
     *
     * @return 返回字段名
     */
    default String field() {
        SerializedLambda serializedLambda = null;
        try {
            serializedLambda = this.getSerializedLambda();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        String methodName = serializedLambda.getImplMethodName();
        return resolveFieldName(methodName);
    }

    /**
     * 获取序列化 Lambda
     *
     * @return Serialized Lambda
     */
    default SerializedLambda getSerializedLambda() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method write = this.getClass().getDeclaredMethod(EsConstant.WRITE_REPLACE);
        write.setAccessible(true);
        return (SerializedLambda) write.invoke(this);
    }

    /**
     * 解析字段名称
     *
     * @param getMethodName 方法名
     * @return 字段名
     */
    default String resolveFieldName(String getMethodName) {
        if (getMethodName.startsWith(EsConstant.GET)) {
            getMethodName = getMethodName.substring(3);
        } else if (getMethodName.startsWith(EsConstant.IS)) {
            getMethodName = getMethodName.substring(2);
        }
        getMethodName = getMethodName.substring(0, 1).toLowerCase() + getMethodName.substring(1);
        return getMethodName;
    }

}
