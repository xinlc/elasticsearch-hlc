package com.github.xinlc.eshlc.core.exception;

/**
 * ES 添加文档异常
 *
 * @author Richard
 * @since 2021-03-30
 */
public class EsSaveDocException extends EsException {

    private static final long serialVersionUID = -1173771097001814096L;

    public EsSaveDocException() {
        super("Document add failed.");
    }

    public EsSaveDocException(String message) {
        super(message);
    }
}
