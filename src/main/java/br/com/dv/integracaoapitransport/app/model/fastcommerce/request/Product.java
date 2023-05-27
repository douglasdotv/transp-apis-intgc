package br.com.dv.integracaoapitransport.app.model.fastcommerce.request;

public record Product(
        int id,
        String sku,
        String name,
        String category,
        String barcode,
        String filters,
        String color,
        String simpleDescriptor1,
        String simpleDescriptor2,
        String simpleDescriptor3,
        String specialDescriptor1,
        String specialDescriptor2,
        String specialDescriptor3,
        int quantity,
        double unitValue,
        double unitWeight,
        boolean bulky,
        double unitHeight,
        double unitLength,
        double unitWidth
) {
}
