package com.base2art.eventSourcedDataAccess;

public class DataAccessWriterException extends Exception {

    public DataAccessWriterException() {
    }

    public DataAccessWriterException(final String message) {
        super(message);
    }

    public DataAccessWriterException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DataAccessWriterException(final Throwable cause) {
        super(cause);
    }

    public DataAccessWriterException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
