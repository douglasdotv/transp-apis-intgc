package br.com.dv.integracaoapitransport.app.controller;

import br.com.dv.integracaoapitransport.app.model.fastcommerce.request.FastcommerceRequest;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FastcommerceResponse;
import br.com.dv.integracaoapitransport.app.service.MainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    private final MainService mainService;

    public RestApiController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping("/api")
    public ResponseEntity<FastcommerceResponse> handleRequest(@RequestBody FastcommerceRequest fastcommerceRequest) {
        return ResponseEntity.ok(mainService.handleRequest(fastcommerceRequest));
    }

}
