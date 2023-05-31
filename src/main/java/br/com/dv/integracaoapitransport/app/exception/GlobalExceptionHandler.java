package br.com.dv.integracaoapitransport.app.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public String handleBadRequest(Model model) {
        String errorMessage = "Bad request. Cause: " + HttpClientErrorException.BadRequest.class.getName();
        model.addAttribute("error", errorMessage);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Model model) {
        String errorMessage = "An error occurred. Cause: " + Exception.class.getName();
        model.addAttribute("error", errorMessage);
        return "error";
    }

    @ExceptionHandler(JadlogApiException.class)
    public String handleJadlogApiException(JadlogApiException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }

    @ExceptionHandler(CarriersApiException.class)
    public String handleCarriersApiException(CarriersApiException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }

}
