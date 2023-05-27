package br.com.dv.integracaoapitransport.app.controller;

import br.com.dv.integracaoapitransport.app.model.fastcommerce.request.FastcommerceRequest;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FastcommerceResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MyController {

    private final br.com.dv.integracaoapitransport.app.service.MainService mainService;

    public MyController(br.com.dv.integracaoapitransport.app.service.MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping
    public FastcommerceResponse handleRequest(@RequestBody FastcommerceRequest fastcommerceRequest) {
        return mainService.handleRequest(fastcommerceRequest);
    }

}
