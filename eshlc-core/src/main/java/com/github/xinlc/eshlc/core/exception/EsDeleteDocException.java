package com.github.xinlc.eshlc.core.exception;

/**
 * ES 删除文档异常
 *
 * @author Richard
 * @since 2021-03-31
 */
public class EsDeleteDocException extends EsException {

    private static final long serialVersionUID = -4537490717671987605L;

    public EsDeleteDocException() {
        super("Document delete failed.");
    }

    public EsDeleteDocException(String message) {
        super(message);
    }
}
