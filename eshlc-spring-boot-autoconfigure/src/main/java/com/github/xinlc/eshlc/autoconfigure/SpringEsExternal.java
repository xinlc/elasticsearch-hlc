package com.github.xinlc.eshlc.autoconfigure;

import cn.hutool.extra.spring.SpringUtil;
import com.github.xinlc.eshlc.core.factory.EsFactory;
import com.github.xinlc.eshlc.core.factory.IEsExternal;
import com.github.xinlc.eshlc.core.factory.IEsFactory;

/**
 * spring ES 实现
 *
 * @author Richard
 * @since 2021-03-26
 */
public class SpringEsExternal implements IEsExternal {

    @Override
    public IEsFactory create(String key) {
        EsFactory esFactory = new EsFactory();

        SpringUtil.registerBean(key, esFactory);

        return esFactory;
    }
}
