package br.com.dv.integracaoapitransport.app.model.fastcommerce.response;

import java.util.List;

public record FreightQuoteResponse(
        Integer err,
        String errDescr,
        boolean useContingency,
        boolean useLower,
        List<ShippingService> shippingServices
) {
}