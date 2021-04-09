package com.github.xinlc.eshlc.core.domain;

import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.util.Map;

/**
 * ES 内联脚本
 *
 * @author Richard
 * @since 2021-04-05
 */
class InlineScript extends AbstractScript {

    InlineScript(String code, Map<String, Object> params) {
        super(code, params);
    }

    @Override
    public Script toEsScript() {
        return new Script(ScriptType.INLINE, getLang(), getIdOrCode(), getOptions(), getParams());
    }
}
