package br.com.dv.integracaoapitransport.app.model.fastcommerce.request;

import java.util.List;

public record FreightQuoteRequest(
        String fromCEP,
        String toCEP,
        double totalAmount,
        double totalWeight,
        boolean hasBulky,
        CalculationParameters calculationParameters,
        List<Product> products
) {
}
