package ru.alfabank.test.task.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Setter
@Getter
public class ExchangeRateDto {
    private String disclaimer;
    private String license;
    private Long timestamp;
    private String base;
    private Map<String, Double> rates;

    public Double getCurrencyRate(String currency){
        return rates.get(currency);
    }

   public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
