package com.github.xinlc.eshlc.core.server;

import cn.hutool.core.lang.Assert;
import com.github.xinlc.eshlc.core.factory.EsFramework;
import com.github.xinlc.eshlc.core.factory.IEsFactory;

/**
 * ES 抽象服务
 *
 * @author Richard
 * @since 2021-03-31
 */
public abstract class AbstractOperations {

    protected final EsFramework esFramework;
    protected final IEsFactory esFactory;

    public AbstractOperations(EsFramework esFramework) {
        Assert.notNull(esFramework, "EsFramework must not be null!");

        this.esFramework = esFramework;
        this.esFactory = esFramework.getFactory();
    }

}
