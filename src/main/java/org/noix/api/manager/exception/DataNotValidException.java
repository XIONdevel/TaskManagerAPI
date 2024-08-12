package org.noix.api.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataNotValidException extends RuntimeException {
    public DataNotValidException(String message) {
        super(message);
    }

    public DataNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotValidException(Throwable cause) {
        super(cause);
    }

    public DataNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DataNotValidException() {
    }
}
