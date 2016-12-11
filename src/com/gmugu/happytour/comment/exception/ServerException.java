package com.gmugu.happytour.comment.exception;

/**
 * Created by mugu on 16-4-18.
 */
public class ServerException extends Exception {
    public ServerException() {
        super();
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable cause) {
        super(cause);

    }

//    @Override
//    public void printStackTrace() {
//        System.out.println(getMessage());
//    }
}
