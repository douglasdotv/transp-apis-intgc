package br.com.dv.integracaoapitransport.app.service;

import br.com.dv.integracaoapitransport.app.model.fastcommerce.request.FastcommerceRequest;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FastcommerceResponse;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FreightQuoteResponse;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.ShippingService;
import br.com.dv.integracaoapitransport.app.model.transport.jadlog.request.JadlogRequest;
import br.com.dv.integracaoapitransport.app.model.transport.jadlog.response.JadlogResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class JadlogService {

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
    public FastcommerceResponse callJadlogApi(JadlogRequest jadlogRequest) {
        String url = "https://www.jadlog.com.br/embarcador/api/frete/valor";

        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();

        JadlogResponse response = webClient.post()
                .body(BodyInserters.fromValue(jadlogRequest))
                .retrieve()
                .bodyToMono(JadlogResponse.class)
                .block();

        if (response == null || response.frete().get(0).vltotal() == null) {
            if (response != null) {
                return buildErrorResponse(response);
            }
        }

        return buildFastcommerceResponse(response);
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

    /** Build error response
     *
     * @param response The JadlogResponse object, which is necessary to build the FreightQuoteResponse.
     * @return FastcommerceResponse The converted FastcommerceResponse object.
     */
    private FastcommerceResponse buildErrorResponse(JadlogResponse response) {
        FreightQuoteResponse freightQuoteResponse = new FreightQuoteResponse(
                Math.toIntExact(response.error().id()),
                response.error().descricao(),
                false,
                false,
                new ArrayList<>()
        );
        return new FastcommerceResponse(freightQuoteResponse);
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
                jadlogResponse.frete().get(0).prazo() + " dias"
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
