package br.com.dv.integracaoapitransport.app.model.transport.jadlog.request;

import br.com.dv.integracaoapitransport.app.model.fastcommerce.request.FastcommerceRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record JadlogRequest(@JsonProperty("frete") List<FreteRequest> frete) {

    public JadlogRequest(FastcommerceRequest fastcommerceRequest) {
        this(List.of(
                new FreteRequest(
                        fastcommerceRequest.freightQuoteRequest().fromCEP().replace("-", ""),
                        fastcommerceRequest.freightQuoteRequest().toCEP().replace("-", ""),
                        null,
                        fastcommerceRequest.freightQuoteRequest().totalWeight(),
                        System.getenv("cnpj"),
                        System.getenv("conta"),
                        null,
                        3,
                        "D",
                        "N",
                        fastcommerceRequest.freightQuoteRequest().totalAmount(),
                        0
                )
        ));
    }

}
