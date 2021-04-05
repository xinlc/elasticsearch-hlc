package com.github.xinlc.eshlc.core.exception;

/**
 * ES 更新文档异常
 *
 * @author Richard
 * @since 2021-03-31
 */
public class EsUpdateDocException extends EsException {

    private static final long serialVersionUID = -4705089901814375408L;

    public EsUpdateDocException() {
        super("Document update failed.");
    }

    public EsUpdateDocException(String message) {
        super(message);
    }
}
