package com.github.xinlc.eshlc.core.exception;

/**
 * ES 异常
 *
 * @author Richard
 * @since 2021-03-30
 */
public class EsException extends RuntimeException {

    private static final long serialVersionUID = 3675770130183299493L;

    public EsException() {
        this("");
    }

    public EsException(String message) {
        this(message, null);
    }

    public EsException(String message, Throwable cause) {
        this(message, cause, false, false);
    }

    public EsException(Throwable cause) {
        this(null, cause);
    }

    protected EsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
