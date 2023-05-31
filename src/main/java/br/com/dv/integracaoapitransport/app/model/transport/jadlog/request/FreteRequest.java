package br.com.dv.integracaoapitransport.app.model.transport.jadlog.request;

import java.math.BigDecimal;

public record FreteRequest(
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
        BigDecimal vlcoleta
) {
}
