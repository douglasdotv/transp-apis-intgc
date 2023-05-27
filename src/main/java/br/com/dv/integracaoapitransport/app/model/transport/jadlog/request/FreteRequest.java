package br.com.dv.integracaoapitransport.app.model.transport.jadlog.request;

public record FreteRequest(
        String cepori,
        String cepdes,
        String frap,
        double peso,
        String cnpj,
        String conta,
        String contrato,
        int modalidade,
        String tpentrega,
        String tpseguro,
        double vldeclarado,
        double vlcoleta
) {
}
