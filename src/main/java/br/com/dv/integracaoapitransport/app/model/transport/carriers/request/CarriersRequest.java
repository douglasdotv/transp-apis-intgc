package br.com.dv.integracaoapitransport.app.model.transport.carriers.request;

import br.com.dv.integracaoapitransport.app.model.fastcommerce.request.FastcommerceRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CarriersRequest(
        @JsonProperty("ValorDeclarado")
        double valorDeclarado,
        double peso,
        double altura,
        double largura,
        double comprimento,
        @JsonIgnore
        String cepDest
) {

    public CarriersRequest(FastcommerceRequest fastcommerceRequest) {
        this(
                fastcommerceRequest.freightQuoteRequest().totalAmount(),
                fastcommerceRequest.freightQuoteRequest().totalWeight(),
                100,
                10,
                10,
                fastcommerceRequest.freightQuoteRequest().toCEP().replace("-", "")
        );
    }

}
