/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.exception;

public class SshException extends Exception {
    private static final long serialVersionUID = -1341103851548488820L;

    public SshException() {
        super();
    }

    public SshException(String message, Throwable cause) {
        super(message, cause);
    }

    public SshException(String message) {
        super(message);
    }

    public SshException(Throwable cause) {
        super(cause);
    }
}
