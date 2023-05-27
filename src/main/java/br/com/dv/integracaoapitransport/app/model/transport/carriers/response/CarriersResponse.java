package br.com.dv.integracaoapitransport.app.model.transport.carriers.response;

import java.util.List;

public record CarriersResponse(String status, String cep, List<Frete> frete) {
}
