package br.com.dv.integracaoapitransport.app.model.transport.jadlog.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record JadlogResponse(@JsonProperty("frete") List<FreteResponse> frete, JadlogError error) {
}
