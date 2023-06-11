package br.com.dv.integracaoapitransport.app.service;

import br.com.dv.integracaoapitransport.app.model.fastcommerce.request.FastcommerceRequest;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FastcommerceResponse;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FreightQuoteResponse;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.ShippingService;
import br.com.dv.integracaoapitransport.app.model.transport.correios.CorreiosPrazoResponse;
import br.com.dv.integracaoapitransport.app.model.transport.correios.CorreiosPrecoResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CorreiosService {

    private final String token = System.getenv("CORREIOS_TOKEN");

    public FastcommerceResponse callCorreiosApi(FastcommerceRequest fcRequest) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://apihom.correios.com.br")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();

        CorreiosPrazoResponse responsePrazo = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/prazo/v1/nacional/{coProduto}")
                        .queryParam("cepOrigem", fcRequest.freightQuoteRequest().fromCEP())
                        .queryParam("cepDestino", fcRequest.freightQuoteRequest().toCEP())
                        .build(fcRequest.freightQuoteRequest().products().get(0).id()))
                .retrieve()
                .bodyToMono(CorreiosPrazoResponse.class)
                .block();

        CorreiosPrecoResponse responsePreco = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/preco/v1/nacional/{coProduto}")
                        .queryParam("cepOrigem", fcRequest.freightQuoteRequest().fromCEP())
                        .queryParam("cepDestino", fcRequest.freightQuoteRequest().toCEP())
                        .build(fcRequest.freightQuoteRequest().products().get(0).id()))
                .retrieve()
                .bodyToMono(CorreiosPrecoResponse.class)
                .block();

        if (responsePrazo == null || responsePreco == null) {
            String errorMessage = "Erro ao consultar API Correios";
            return buildErrorResponse(errorMessage);
        }

        return buildFastcommerceResponse(responsePrazo, responsePreco);
    }

    public FastcommerceResponse buildFastcommerceResponse(
            CorreiosPrazoResponse responsePrazo,
            CorreiosPrecoResponse responsePreco) {
        return new FastcommerceResponse(buildFreightQuoteResponse(responsePrazo, responsePreco));
    }

    private FastcommerceResponse buildErrorResponse(String errorMessage) {
        FreightQuoteResponse freightQuoteResponse = new FreightQuoteResponse(
                -1,
                errorMessage,
                false,
                false,
                new ArrayList<>()
        );

        return new FastcommerceResponse(freightQuoteResponse);
    }

    private FreightQuoteResponse buildFreightQuoteResponse(
            CorreiosPrazoResponse responsePrazo,
            CorreiosPrecoResponse responsePreco) {
        ShippingService shippingService = new ShippingService(
                "Correios",
                new BigDecimal(responsePreco.pcFinal()),
                responsePrazo.prazoEntrega() + " dias"
        );

        return new FreightQuoteResponse(
                0,
                "",
                false,
                false,
                List.of(shippingService)
        );
    }

}