package ru.alfabank.test.task.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alfabank.test.task.dto.ExchangeRateDto;

@FeignClient(name="OpenExchangeRatesClient", url = "${openexchangerates.url}")
public interface OpenExchangeRatesClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "${openexchangerates.url.value}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ExchangeRateDto getExchangeRate(@PathVariable("date") String date,
                                    @RequestParam("symbols") String currency);
}
