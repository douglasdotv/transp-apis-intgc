package br.com.dv.integracaoapitransport.app.model.transport.carriers.response;

import java.math.BigDecimal;

public record Frete(String modalidade, Integer prazo, BigDecimal preco) {
}
