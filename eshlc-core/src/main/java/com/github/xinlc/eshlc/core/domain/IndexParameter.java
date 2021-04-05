package com.github.xinlc.eshlc.core.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * ES 索引参数类
 *
 * @author Richard
 * @since 2021-03-26
 */
public class IndexParameter {

    private Map<String, String> parameters = new HashMap<>();

    /**
     * 获取实例
     *
     * @return IndexParameter
     */
    public static IndexParameter getInstance() {
        return new IndexParameter();
    }


    /**
     * 写入参数
     *
     * @param key   写入的key
     * @param value 写入的value
     * @return IndexParameter
     */
    public IndexParameter addKey(String key, String value) {
        parameters.put(key, value);
        return this;
    }

    /**
     * 移除参数
     *
     * @param key 移除的key
     * @return IndexProperties
     */
    public IndexParameter removeKey(String key) {
        parameters.remove(key);
        return this;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "IndexParameter{" +
            "parameters=" + parameters +
            '}';
    }
}
