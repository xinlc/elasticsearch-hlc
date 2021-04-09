package com.github.xinlc.eshlc.core.domain;

import java.util.Collections;
import java.util.Map;

/**
 * ES 脚本排序
 *
 * @author Richard
 * @since 2021-04-05
 */
abstract class AbstractScript implements IEsScript {

    private static final String DEFAULT_LANG = "painless";
    private final String idOrCode;
    private Map<String, Object> params;
    private String lang;
    private Map<String, String> options;

    protected AbstractScript(String idOrCode, Map<String, Object> params) {
        this.idOrCode = idOrCode;
        this.params = params;
        this.lang = DEFAULT_LANG;
        this.options = Collections.emptyMap();
    }

    @Override
    public String getLang() {
        return lang;
    }

    @Override
    public Map<String, String> getOptions() {
        return options;
    }

    @Override
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getIdOrCode() {
        return idOrCode;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public void setOptions(Map<String, String> options) {
        this.options = options;
    }
}
