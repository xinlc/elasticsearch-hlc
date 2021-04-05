package com.github.xinlc.eshlc.core.exception;

/**
 * ES 文档没找到
 *
 * @author Richard
 * @since 2021-03-31
 */
public class EsDocNotFoundException extends EsException {

    private static final long serialVersionUID = 2130119691304985909L;

    public EsDocNotFoundException() {
        super("Document not found.");
    }

    public EsDocNotFoundException(String message) {
        super(message);
    }
}
