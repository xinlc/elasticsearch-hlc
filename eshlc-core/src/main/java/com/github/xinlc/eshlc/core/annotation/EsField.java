package com.github.xinlc.eshlc.core.annotation;

import java.lang.annotation.*;

/**
 * ES 字段注解
 *
 * @author Richard
 * @since 2021-03-26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EsField {
    // 未实现，字段类型，分词器。。。
}
