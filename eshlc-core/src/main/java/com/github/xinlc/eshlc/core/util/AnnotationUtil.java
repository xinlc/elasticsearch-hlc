package com.github.xinlc.eshlc.core.util;

import cn.hutool.core.util.ReflectUtil;
import com.github.xinlc.eshlc.core.annotation.EsID;
import com.github.xinlc.eshlc.core.annotation.EsTable;

import java.lang.reflect.Field;

/**
 * 注解工具类
 *
 * @author Richard
 * @since 2021-03-30
 */
public final class AnnotationUtil {
    private AnnotationUtil() {
    }

    /**
     * 获取ES文档索引名称
     *
     * @param obj ES实体
     * @return 索引名称
     */
    public static String getEsIndex(Object obj) {
        EsTable esTable = null;
        if (obj.getClass().isAnnotationPresent(EsTable.class)) {
            esTable = obj.getClass().getAnnotation(EsTable.class);
        }
        if (esTable != null) {
            return esTable.index();
        }
        return null;
    }

    /**
     * 获取ES文档ID
     *
     * @param obj ES实体
     * @return ES文档ID
     */
    public static String getEsId(Object obj) {
        Field[] fields = ReflectUtil.getFields(obj.getClass());
        for (Field f : fields) {
            f.setAccessible(true);
            EsID esid = f.getAnnotation(EsID.class);
            if (esid != null) {
                Object value = null;
                try {
                    value = f.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (value == null) {
                    return null;
                } else {
                    return value.toString();
                }
            }
        }
        return null;
    }
}
