package com.github.xinlc.eshlc.core.domain;

/**
 * ES 文档实体
 *
 * @author Richard
 * @since 2021-03-31
 */
public class EsDocument {
    /**
     * 索引名称
     */
    private String index;

    /**
     * 文档ID
     */
    private String id;

    /**
     * Json文档
     */
    private String jsonData;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public String toString() {
        return "EsDocument{" +
            "index='" + index + '\'' +
            ", id='" + id + '\'' +
            ", jsonData='" + jsonData + '\'' +
            '}';
    }
}
