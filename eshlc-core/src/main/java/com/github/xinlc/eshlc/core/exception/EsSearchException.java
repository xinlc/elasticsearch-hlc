package com.github.xinlc.eshlc.core.exception;

/**
 * ES 搜索异常
 *
 * @author Richard
 * @since 2021-03-31
 */
public class EsSearchException extends EsException {

    private static final long serialVersionUID = 4533040668108265755L;

    public EsSearchException() {
        super("ES search failed.");
    }

    public EsSearchException(String message) {
        super(message);
    }
}
