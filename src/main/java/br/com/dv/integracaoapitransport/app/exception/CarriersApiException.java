package br.com.dv.integracaoapitransport.app.exception;

public class CarriersApiException extends RuntimeException {

    public CarriersApiException(String message) {
        super(message);
    }

    public CarriersApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
