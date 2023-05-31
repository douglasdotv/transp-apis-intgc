package br.com.dv.integracaoapitransport.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestViewController {

    @GetMapping("/")
    public String handleRequest(Model model) {
        model.addAttribute("message", "Home page is working!");
        return "index";
    }

}
