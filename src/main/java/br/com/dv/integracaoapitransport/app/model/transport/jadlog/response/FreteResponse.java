package br.com.dv.integracaoapitransport.app.model.transport.jadlog.response;

public record FreteResponse(
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
        double vlcoleta,
        int prazo,
        double vltotal
) {
}
