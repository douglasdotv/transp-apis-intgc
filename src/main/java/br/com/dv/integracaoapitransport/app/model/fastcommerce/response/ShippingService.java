package br.com.dv.integracaoapitransport.app.model.fastcommerce.response;

import java.math.BigDecimal;

public record ShippingService(String serviceName, BigDecimal servicePrice, String serviceNotes) {
}
