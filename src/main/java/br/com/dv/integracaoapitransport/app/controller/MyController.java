package br.com.dv.integracaoapitransport.app.controller;

import br.com.dv.integracaoapitransport.app.model.fastcommerce.request.FastcommerceRequest;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FastcommerceResponse;
import br.com.dv.integracaoapitransport.app.service.MainService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MyController {

    private final MainService mainService;

    public MyController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping
    public ResponseEntity<FastcommerceResponse> handleRequest(@RequestBody FastcommerceRequest fastcommerceRequest) {
        return ResponseEntity.ok(mainService.handleRequest(fastcommerceRequest));
    }

    @GetMapping
    public String handleRequest(Model model) {
        model.addAttribute("message", "Home page is working!");
        return "index";
    }

}
