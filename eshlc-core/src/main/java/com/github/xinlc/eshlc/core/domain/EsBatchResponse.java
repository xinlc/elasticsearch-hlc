package com.github.xinlc.eshlc.core.domain;

/**
 * ES 文档实体
 *
 * @author Richard
 * @since 2021-03-31
 */
public class EsBatchResponse {
    /**
     * 序列号
     */
    private Integer seqNo;

    /**
     * 索引名称
     */
    private String index;

    /**
     * 文档ID
     */
    private String id;

    /**
     * 是否成功
     */
    private Boolean successful;

    /**
     * 失败原因
     */
    private String reason;

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

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

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "EsBatchResponse{" +
            "seqNo=" + seqNo +
            ", index='" + index + '\'' +
            ", id='" + id + '\'' +
            ", successful=" + successful +
            ", reason='" + reason + '\'' +
            '}';
    }
}
