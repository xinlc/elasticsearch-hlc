package com.github.xinlc.eshlc.core.factory;

/**
 * ES 外部实现接口
 *
 * @author Richard
 * @since 2021-03-26
 */
public interface IEsExternal {

    /**
     * 创建
     *
     * @param key 容器Key
     * @return 工厂实现
     */
    default IEsFactory create(String key) {
        return new EsFactory();
    }
}
