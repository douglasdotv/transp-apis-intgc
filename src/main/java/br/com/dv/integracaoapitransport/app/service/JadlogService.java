package br.com.dv.integracaoapitransport.app.service;

import br.com.dv.integracaoapitransport.app.model.fastcommerce.request.FastcommerceRequest;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FastcommerceResponse;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FreightQuoteResponse;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.ShippingService;
import br.com.dv.integracaoapitransport.app.model.transport.jadlog.request.JadlogRequest;
import br.com.dv.integracaoapitransport.app.model.transport.jadlog.response.JadlogResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class JadlogService {

    private final Logger log = LoggerFactory.getLogger(JadlogService.class);
    private final String token = System.getenv("JADLOG_TOKEN");

    /**
     * Convert FastcommerceRequest to JadlogRequest
     *
     * @param fastcommerceRequest The FastcommerceRequest object to be converted into a JadlogRequest.
     * @return JadlogRequest The converted JadlogRequest object.
     */
    public JadlogRequest convertFastcommerceRequestToJadlogRequest(FastcommerceRequest fastcommerceRequest) {
        return new JadlogRequest(fastcommerceRequest);
    }

    /**
     * Call Jadlog API
     *
     * @param jadlogRequest The JadlogRequest object to be sent to Jadlog API.
     * @return JadlogResponse The JadlogResponse object returned from Jadlog API.
     */
    public JadlogResponse callJadlogApi(JadlogRequest jadlogRequest) {
        String url = "https://www.jadlog.com.br/embarcador/api/frete/valor";

        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();

        try {
            return webClient.post()
                    .body(BodyInserters.fromValue(jadlogRequest))
                    .retrieve()
                    .bodyToMono(JadlogResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error response from Jadlog API: " + e.getResponseBodyAsString());
            throw e;
        }
    }

    /**
     * Build FastcommerceResponse from JadlogResponse
     *
     * @param jadlogResponse The JadlogResponse object to be converted into a FastcommerceResponse.
     * @return FastcommerceResponse The converted FastcommerceResponse object.
     */
    public FastcommerceResponse buildFastcommerceResponse(JadlogResponse jadlogResponse) {
        return new FastcommerceResponse(buildFreightQuoteResponse(jadlogResponse));
    }

    /**
     * Build FreightQuoteResponse
     *
     * @param jadlogResponse The JadlogResponse object, which is necessary to build the FreightQuoteResponse.
     * @return FreightQuoteResponse The converted FreightQuoteResponse object.
     */
    private FreightQuoteResponse buildFreightQuoteResponse(JadlogResponse jadlogResponse) {
        ShippingService shippingService = new ShippingService(
                "Jadlog",
                jadlogResponse.frete().get(0).vltotal(),
                jadlogResponse.frete().get(0).prazo() + " dias" // + dias sfd\herj
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
