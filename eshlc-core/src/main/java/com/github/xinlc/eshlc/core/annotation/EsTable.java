package com.github.xinlc.eshlc.core.annotation;

import java.lang.annotation.*;

/**
 * ES 实体注解类
 *
 * @author Richard
 * @since 2021-03-26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EsTable {

    /**
     * 索引名称
     *
     * @return String
     */
    String index();

    /**
     * 主分片数
     *
     * @return short
     */
    short shards() default 1;

    /**
     * 副本分片数
     *
     * @return short
     */
    short replicas() default 1;
}
