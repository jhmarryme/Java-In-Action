package com.jhmarryme.rabbit.api.exception;


import java.io.Serial;

public class MessageRunTimeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8651828913888663267L;

    public MessageRunTimeException() {
        super();
    }

    public MessageRunTimeException(String message) {
        super(message);
    }

    public MessageRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }
}
