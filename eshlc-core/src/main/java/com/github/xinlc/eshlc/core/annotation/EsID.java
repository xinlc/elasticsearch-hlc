package com.github.xinlc.eshlc.core.annotation;

import java.lang.annotation.*;

/**
 * ES 实体ID标识
 *
 * @author Richard
 * @since 2021-03-26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EsID {
}
