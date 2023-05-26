package br.com.dv.integracaoapijadlog.app;

import br.com.dv.integracaoapijadlog.app.model.FreteRequest;
import br.com.dv.integracaoapijadlog.app.model.FreteResponse;
import br.com.dv.integracaoapijadlog.app.model.JadlogRequest;
import br.com.dv.integracaoapijadlog.app.model.JadlogResponse;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    public JadlogRequest transformToJadlogRequest(FreteRequest freteRequest) {
        // TODO: Implement this method
        return null;
    }

    public JadlogResponse callJadlogApi(JadlogRequest jadlogRequest) {
        // TODO: Implement this method
        return null;
    }

    public FreteResponse constructResponse(JadlogResponse jadlogResponse) {
        // TODO: Implement this method
        return null;
    }

}
