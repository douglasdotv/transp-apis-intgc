package br.com.dv.integracaoapijadlog.app;

import br.com.dv.integracaoapijadlog.app.model.FreteRequest;
import br.com.dv.integracaoapijadlog.app.model.FreteResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MyController {

    private final MyService service;

    public MyController(MyService service) {
        this.service = service;
    }

    @PostMapping
    public FreteResponse handleRequest(@RequestBody FreteRequest freteRequest) {
        var jadlogRequest = service.transformToJadlogRequest(freteRequest);
        var jadlogResponse = service.callJadlogApi(jadlogRequest);
        return service.constructResponse(jadlogResponse);
    }

}
