package br.com.dv.integracaoapitransport.app.model.transport.carriers.request;

import br.com.dv.integracaoapitransport.app.model.fastcommerce.request.FastcommerceRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CarriersRequest(
        @JsonProperty("ValorDeclarado")
        BigDecimal valorDeclarado,
        Double peso,
        Double altura,
        Double largura,
        Double comprimento,
        @JsonIgnore
        String cepDest
) {

    public CarriersRequest(FastcommerceRequest fastcommerceRequest) {
        this(
                fastcommerceRequest.freightQuoteRequest().totalAmount(),
                fastcommerceRequest.freightQuoteRequest().totalWeight(),
                0.0,
                0.0,
                0.0,
                fastcommerceRequest.freightQuoteRequest().toCEP().replace("-", "")
        );
    }

}
