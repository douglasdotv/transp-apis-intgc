package br.com.dv.integracaoapitransport.app.service;

import br.com.dv.integracaoapitransport.app.exception.CarriersApiException;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.request.FastcommerceRequest;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FastcommerceResponse;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FreightQuoteResponse;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.ShippingService;
import br.com.dv.integracaoapitransport.app.model.transport.carriers.request.CarriersRequest;
import br.com.dv.integracaoapitransport.app.model.transport.carriers.response.CarriersResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class CarriersService {

    private final String token = System.getenv("CARRIERS_TOKEN");

    public CarriersRequest convertFastcommerceRequestToCarriersRequest(FastcommerceRequest fastcommerceRequest) {
        return new CarriersRequest(fastcommerceRequest);
    }

    public CarriersResponse callCarriersApi(CarriersRequest carriersRequest) {
        String url = "http://api.carriers.com.br/client/Carriers/Rates/" + carriersRequest.cepDest();

        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();

        try {
            return webClient.post()
                    .body(BodyInserters.fromValue(carriersRequest))
                    .retrieve()
                    .bodyToMono(CarriersResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new CarriersApiException("Error response from Jadlog API: " + e.getResponseBodyAsString()); // ?????
        }
    }

    public FastcommerceResponse buildFastcommerceResponse(CarriersResponse carriersResponse) {
        return new FastcommerceResponse(buildFreightQuoteResponse(carriersResponse));
    }

    private FreightQuoteResponse buildFreightQuoteResponse(CarriersResponse carriersResponse) {
        ShippingService shippingService = new ShippingService(
                "Carriers",
                carriersResponse.frete().get(0).preco(),
                carriersResponse.frete().get(0).prazo() + " dias"
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
