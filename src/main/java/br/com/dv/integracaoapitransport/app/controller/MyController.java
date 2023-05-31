package br.com.dv.integracaoapitransport.app.controller;

import br.com.dv.integracaoapitransport.app.model.fastcommerce.request.FastcommerceRequest;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FastcommerceResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public String handleRequest(Model model) {
        model.addAttribute("message", "Home page is working!");
        return "index";
    }

}
