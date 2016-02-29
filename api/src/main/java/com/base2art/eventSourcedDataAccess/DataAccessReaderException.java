package com.base2art.eventSourcedDataAccess;

public class DataAccessReaderException extends Exception {

    public DataAccessReaderException() {
    }

    public DataAccessReaderException(final String message) {
        super(message);
    }

    public DataAccessReaderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DataAccessReaderException(final Throwable cause) {
        super(cause);
    }

    public DataAccessReaderException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
