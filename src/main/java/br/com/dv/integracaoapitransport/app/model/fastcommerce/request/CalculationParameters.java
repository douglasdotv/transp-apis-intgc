package br.com.dv.integracaoapitransport.app.model.fastcommerce.request;

public record CalculationParameters(
        Integer giftPacking,
        Double packagePrice,
        Double fixedFreight,
        Double insurancePercentage,
        Double additionalValue,
        Double additionalWeight,
        Integer additionalDays,
        Double freightMultiplier,
        boolean freeFreightForZeroWeight,
        Integer partnerId,
        Double partnerFreightDiscount
) {
}