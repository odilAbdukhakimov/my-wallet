package com.example.mywallet.tests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://cbu.uz/uz/arkhiv-kursov-valyut/json/"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        List<Currency> currencies = mapper.readValue(response.body(), new TypeReference<>() {
        });
        System.out.println(currencies.get(0).getCcyNmUz());

    }
}
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class Currency {
    private int id;
    @JsonProperty("Ccy")
    private String ccy;
    @JsonProperty("CcyNm_UZ")
    private String CcyNmUz;
    @JsonProperty("Rate")
    private double rate;
}
