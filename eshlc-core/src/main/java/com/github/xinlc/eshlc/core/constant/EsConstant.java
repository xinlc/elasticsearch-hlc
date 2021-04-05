package com.github.xinlc.eshlc.core.constant;

import java.time.format.DateTimeFormatter;

/**
 * ES 常量
 *
 * @author Richard
 * @since 2020-03-26
 */
public final class EsConstant {
    private EsConstant() {
    }

    public static final String ELASTICSEARCH = "elasticsearch";
    public static final String ENABLE = "enable";
    public static final String TRUE = "true";
    public static final String GET = "get";
    public static final String IS = "is";
    public static final Integer ZERO = 0;
    public static final Integer ONE = 1;
    public static final Integer TWO = 2;
    public static final Integer THREE = 3;
    public static final Integer FIVE = 5;
    public static final Integer TEN = 10;
    public static final String WRITE_REPLACE = "writeReplace";
    public static final String EMPTY = "";
    public static final Integer HTTP_SUCCESS_STATUS = 200;
    public static final String HIGHLIGHT_PRE_TAGS = "<span class='hlt'>";
    public static final String HIGHLIGHT_POST_TAGS = "</span>";
    public static final String PINYIN = "pinyin";
    public static final String IK_SMART = "ik_smart";
    public static final String KEYWORD = "keyword";
    public static final String DOT_KEYWORD = ".keyword";
    public static final String DOT_PINYIN = ".pinyin";
    public static final String DOT_SUGGEST = ".suggest";
    public static final String DOT_AUTOCOMPLETE = ".autocomplete";
    public static final String UTC = "UTC";
    public static final String ADD_EIGHT = "+8";
    public static final DateTimeFormatter DATA_TIME_FOMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATA_FOMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String DOT = ".";
    public static final String DOT_REGEX = "\\.";
    public static final String UNKNOWN = "Unknown";

    /**
     * 批量操作，每批次条数
     */
    public static final int BULK_SIZE = 5000;


}
