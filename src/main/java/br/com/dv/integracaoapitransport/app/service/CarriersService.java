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

import java.util.List;

@Service
public class CarriersService {

    private final String token = System.getenv("CARRIERS_TOKEN");

    /**
     * Convert FastcommerceRequest to CarriersRequest
     *
     * @param fastcommerceRequest The FastcommerceRequest object to be converted into a CarriersRequest.
     * @return CarriersRequest The converted CarriersRequest object.
     */
    public CarriersRequest convertFastcommerceRequestToCarriersRequest(FastcommerceRequest fastcommerceRequest) {
        return new CarriersRequest(fastcommerceRequest);
    }

    /**
     * Call Carriers API
     *
     * @param carriersRequest The CarriersRequest object to be sent to Carriers API.
     * @return CarriersResponse The CarriersResponse object returned from Carriers API.
     */
    public CarriersResponse callCarriersApi(CarriersRequest carriersRequest) {
        String url = "http://api.carriers.com.br/client/Carriers/Rates/" + carriersRequest.cepDest();

        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();

        CarriersResponse response = webClient.post()
                .body(BodyInserters.fromValue(carriersRequest))
                .retrieve()
                .bodyToMono(CarriersResponse.class)
                .block();

        if (response == null || response.status().equals("erro")) {
            throw new CarriersApiException(response != null ? response.errors().get(0).message() : null);
        }

        return response;
    }

    /**
     * Build FastcommerceResponse
     *
     * @param carriersResponse The CarriersResponse object to be converted into a FastcommerceResponse.
     * @return FastcommerceResponse The converted FastcommerceResponse object.
     */
    public FastcommerceResponse buildFastcommerceResponse(CarriersResponse carriersResponse) {
        return new FastcommerceResponse(buildFreightQuoteResponse(carriersResponse));
    }

    /**
     * Build FreightQuoteResponse
     *
     * @param carriersResponse The CarriersResponse object to be converted into a FreightQuoteResponse.
     * @return FreightQuoteResponse The converted FreightQuoteResponse object.
     */
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
