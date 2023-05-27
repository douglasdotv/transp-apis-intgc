package br.com.dv.integracaoapitransport.app.model.fastcommerce.request;

public record CalculationParameters(
        int giftPacking,
        double packagePrice,
        double fixedFreight,
        double insurancePercentage,
        double additionalValue,
        double additionalWeight,
        int additionalDays,
        double freightMultiplier,
        boolean freeFreightForZeroWeight,
        int partnerId,
        double partnerFreightDiscount
) {
}