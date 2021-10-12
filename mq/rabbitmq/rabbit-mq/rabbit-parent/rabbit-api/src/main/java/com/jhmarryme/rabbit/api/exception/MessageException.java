package com.jhmarryme.rabbit.api.exception;


import java.io.Serial;

public class MessageException extends Exception {

    @Serial
    private static final long serialVersionUID = 6347951066190728758L;

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

}
