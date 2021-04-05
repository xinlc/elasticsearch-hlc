package com.github.xinlc.eshlc.core.enums;

/**
 * 分词器类型
 *
 * @author Richard
 * @since 2021-03-26
 */
public enum AnalyzerType {
    /**
     * 分词器
     */
    STANDARD,
    SIMPLE,
    WHITESPACE,
    STOP,
    KEYWORD,
    PATTERN,
    FINGERPRINT,
    ENGLISH,
    IK_SMART,
    IK_MAX_WORD;
}
