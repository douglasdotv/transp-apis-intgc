package br.com.dv.integracaoapitransport.app.exception;

public class JadlogApiException extends RuntimeException {

    public JadlogApiException(String message) {
        super(message);
    }

    public JadlogApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
