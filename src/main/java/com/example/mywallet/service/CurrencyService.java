package com.example.mywallet.service;

import com.example.mywallet.dto.request.CurrencyExchangeRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.dto.response.CurrencyExchangeResponseDto;
import com.example.mywallet.entity.CurrencyEntity;
import com.example.mywallet.exception.RecordNotFound;
import com.example.mywallet.repository.CurrencyRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    @SneakyThrows
    @Scheduled(cron = "0 */1 * * * *")
    public void updateCurrencyBalance() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://cbu.uz/uz/arkhiv-kursov-valyut/json/"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        List<CurrencyEntity> currencies = mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(response.body(), new TypeReference<List<CurrencyEntity>>() {
        });
        currencies.stream()
                .limit(10)
                .forEach(currencyRepository::save);
    }

    public ApiResponse conversion(CurrencyExchangeRequestDto requestDto) {
        CurrencyEntity incomingCurrency = getById(requestDto.getIncomingCurrencyId());
        CurrencyEntity resultCurrency = getById(requestDto.getResultCurrencyId());
        double resultAmount = incomingCurrency.getRate() * requestDto.getAmount() / resultCurrency.getRate();
        CurrencyExchangeResponseDto responseDto = CurrencyExchangeResponseDto.builder()
                .enterVal(requestDto.getAmount())
                .exitVal(resultAmount)
                .incomingCurrencyCcy(incomingCurrency.getCcy())
                .resultCurrencyCcy(resultCurrency.getCcy())
                .build();
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                responseDto
        );
    }

    private CurrencyEntity getById(int id) {
        return currencyRepository.findById(id).orElseThrow(() -> new RecordNotFound("Currency not found"));
    }
}
