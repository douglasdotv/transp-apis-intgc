package br.com.dv.integracaoapitransport.app.model.transport.jadlog.response;

import java.math.BigDecimal;

public record FreteResponse(
        String cepori,
        String cepdes,
        String frap,
        Double peso,
        String cnpj,
        String conta,
        String contrato,
        Integer modalidade,
        String tpentrega,
        String tpseguro,
        BigDecimal vldeclarado,
        BigDecimal vlcoleta,
        Integer prazo,
        BigDecimal vltotal
) {
}
