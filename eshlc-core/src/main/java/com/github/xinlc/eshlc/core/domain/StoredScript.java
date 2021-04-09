package com.github.xinlc.eshlc.core.domain;

import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.util.Map;

/**
 * ES 存储脚本
 *
 * @author Richard
 * @since 2021-04-05
 */
class StoredScript extends AbstractScript {

    StoredScript(String id, Map<String, Object> params) {
        super(id, params);
    }

    @Override
    public Script toEsScript() {
        return new Script(ScriptType.STORED, null, getIdOrCode(), null, getParams());
    }
}
