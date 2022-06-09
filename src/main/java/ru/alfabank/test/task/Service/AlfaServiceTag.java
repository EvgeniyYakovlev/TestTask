package ru.alfabank.test.task.Service;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.alfabank.test.task.client.BinaryObjectByUrlClient;
import ru.alfabank.test.task.client.GiphyClient;
import ru.alfabank.test.task.client.OpenExchangeRatesClient;
import ru.alfabank.test.task.dto.ExchangeRateDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class AlfaServiceTag {

    @Value("${giphy.tag.broke}")
    String broke;

    @Value("${giphy.tag.rich}")
    String rich;

    @Autowired
    private OpenExchangeRatesClient openExchangeRatesClient;

    @Autowired
    private GiphyClient giphyClient;

    @Autowired
    private BinaryObjectByUrlClient binaryObjectByUrlClient;

    public ResponseEntity<byte[]> getGifByExchangeRate(String today, String yesterday, String currency) throws URISyntaxException {

        ExchangeRateDto todayExchRate = openExchangeRatesClient.getExchangeRate(today, currency);
        ExchangeRateDto yesterdayExchRate = openExchangeRatesClient.getExchangeRate(yesterday, currency);

        String gsonStr;
        if (todayExchRate.getCurrencyRate(currency) > yesterdayExchRate.getCurrencyRate(currency)) {
            gsonStr = giphyClient.getRandomGifInfoByTag(rich);
        }
        else {
            gsonStr =giphyClient.getRandomGifInfoByTag(broke);
        }
        List<String> url = JsonPath.parse(gsonStr).read("$..images.original.url");
        return binaryObjectByUrlClient.get(new URI(url.get(0)));
    }
}
