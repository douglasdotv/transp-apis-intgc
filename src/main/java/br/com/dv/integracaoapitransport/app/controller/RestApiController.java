package br.com.dv.integracaoapitransport.app.controller;

import br.com.dv.integracaoapitransport.app.model.fastcommerce.request.FastcommerceRequest;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FastcommerceResponse;
import br.com.dv.integracaoapitransport.app.service.MainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
public class RestApiController {

    private final MainService mainService;
    private final ObjectMapper objectMapper;

    public RestApiController(MainService mainService, ObjectMapper objectMapper) {
        this.mainService = mainService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/")
    public ResponseEntity<FastcommerceResponse> handleRequest(@RequestBody String payload) {
        payload = URLDecoder.decode(payload, StandardCharsets.UTF_8);

        String json = payload.trim();
        if (json.startsWith("quote")) {
            json = json.substring(6);
        }
        FastcommerceRequest fcRequest;
        try {
            fcRequest = objectMapper.readValue(json, FastcommerceRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(mainService.handleRequest(fcRequest));
    }

}
