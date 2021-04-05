package com.github.xinlc.eshlc.core.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 索引属性配置
 *
 * @author Richard
 * @since 2021-03-26
 */
public class IndexProperties {

    private Map<String, IndexParameter> properties = new HashMap<>();

    /**
     * 获取实例
     *
     * @return IndexProperties
     */
    public static IndexProperties getInstance() {
        return new IndexProperties();
    }

    /**
     * 写入参数
     *
     * @param key       写入的key
     * @param parameter 写入的参数
     * @return IndexProperties
     */
    public IndexProperties addKey(String key, IndexParameter parameter) {
        properties.put(key, parameter);
        return this;
    }

    /**
     * 移除参数
     *
     * @param key 移除的key
     * @return IndexProperties
     */
    public IndexProperties removeKey(String key) {
        properties.remove(key);
        return this;
    }

    public Map<String, IndexParameter> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, IndexParameter> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "IndexProperties{" +
            "properties=" + properties +
            '}';
    }
}
