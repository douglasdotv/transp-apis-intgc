package br.com.dv.integracaoapitransport.app.service;

import br.com.dv.integracaoapitransport.app.model.fastcommerce.request.FastcommerceRequest;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FastcommerceResponse;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.FreightQuoteResponse;
import br.com.dv.integracaoapitransport.app.model.fastcommerce.response.ShippingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {

    private final JadlogService jadlogService;
    private final CarriersService carriersService;
    private final List<FastcommerceResponse> fcResponses;

    public MainService(JadlogService jadlogService, CarriersService carriersService) {
        this.jadlogService = jadlogService;
        this.carriersService = carriersService;
        this.fcResponses = new ArrayList<>();
    }

    public FastcommerceResponse handleRequest(FastcommerceRequest fcRequest) {
        // Jadlog
        var jadlogRequest = jadlogService.convertFastcommerceRequestToJadlogRequest(fcRequest);
        var jadlogFcResponse = jadlogService.callJadlogApi(jadlogRequest);
        fcResponses.add(jadlogFcResponse);

        // Carriers
        var carriersRequest = carriersService.convertFastcommerceRequestToCarriersRequest(fcRequest);
        var carriersFcResponse = carriersService.callCarriersApi(carriersRequest);
        fcResponses.add(carriersFcResponse);

        var shippingServices = new ArrayList<ShippingService>();
        fcResponses.forEach(
                response -> shippingServices.addAll(response.freightQuoteResponse().shippingServices())
        );

        FastcommerceResponse finalFcResponse = new FastcommerceResponse(new FreightQuoteResponse(
                0,
                "",
                false,
                false,
                shippingServices
        ));

        // For next requests
        fcResponses.clear();

        return finalFcResponse;
    }

}
