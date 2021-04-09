package com.github.xinlc.eshlc.core.domain;

import java.util.Collections;
import java.util.Map;

/**
 * ES 脚本对象
 *
 * @author Richard
 * @since 2021-04-05
 */
public interface IEsScript {
    /**
     * 创建有参内联脚本
     *
     * @param content 脚本内容
     * @param params  参数
     * @return 脚本
     */
    static IEsScript inline(String content, Map<String, Object> params) {
        return new InlineScript(content, params);
    }

    /**
     * 创建无惨内联脚本
     *
     * @param content 脚本内容
     * @return 脚本
     */
    static IEsScript inline(String content) {
        return new InlineScript(content, Collections.emptyMap());
    }

    /**
     * 创建存储脚本
     *
     * @param id     脚本Id
     * @param params 参数
     * @return 脚本
     */
    static IEsScript stored(String id, Map<String, Object> params) {
        return new StoredScript(id, params);
    }

    /**
     * 创建无参脚本
     *
     * @param id 脚本Id
     * @return 脚本
     */
    static IEsScript stored(String id) {
        return new StoredScript(id, Collections.emptyMap());
    }

    /**
     * 获取可选参数
     *
     * @return 可选参数
     */
    Map<String, String> getOptions();

    /**
     * 设置可选参数
     *
     * @param options 可选参数
     */
    void setOptions(Map<String, String> options);

    /**
     * 设置Map参数
     *
     * @param params 参数
     */
    void setParams(Map<String, Object> params);

    /**
     * 获取脚本预研，默认是<code>painless</code>
     *
     * @return 脚本语言
     */
    default String getLang() {
        return "painless";
    }

    /**
     * 设置脚本语言
     *
     * @param lang 脚本语言
     */
    void setLang(String lang);

    /**
     * 转换成ES的脚本对象
     *
     * @return ES脚本
     */
    org.elasticsearch.script.Script toEsScript();
}
