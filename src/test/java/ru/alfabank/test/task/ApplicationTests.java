package ru.alfabank.test.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.alfabank.test.task.Service.AlfaServiceSearch;
import ru.alfabank.test.task.Service.AlfaServiceTag;
import ru.alfabank.test.task.client.BinaryObjectByUrlClient;
import ru.alfabank.test.task.client.GiphyClient;
import ru.alfabank.test.task.client.OpenExchangeRatesClient;
import ru.alfabank.test.task.dto.ExchangeRateDto;


import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class ApplicationTests {

	@MockBean
	private BinaryObjectByUrlClient binaryObjectByUrlClient;

	@MockBean
	private GiphyClient giphyClient;

	@MockBean
	private OpenExchangeRatesClient openExchangeRatesClient;

	@Autowired
	private AlfaServiceTag alfaServiceTag;

	@Autowired
	private AlfaServiceSearch alfaServiceSearch;

	private ExchangeRateDto todayRateDto;
	private ExchangeRateDto yesterdayRateDto;
	private String today;
	private String yesterday;

	@BeforeEach
	public void setUp() throws URISyntaxException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		today = format.format(date.getTime());
		yesterday = format.format(date.getTime() - 24*60*60*1000);

		todayRateDto = new ExchangeRateDto();
		yesterdayRateDto = new ExchangeRateDto();

		Mockito.when(openExchangeRatesClient.getExchangeRate(today, "RUB"))
				.thenReturn(todayRateDto);
		Mockito.when(openExchangeRatesClient.getExchangeRate(yesterday, "RUB"))
				.thenReturn(yesterdayRateDto);

		Mockito.when(giphyClient.getRandomGifInfoByTag("rich")).thenReturn("{images:{original:{url:\"/static/rich.gif\"}}}");
		Mockito.when(giphyClient.getRandomGifInfoByTag("broke")).thenReturn("{images:{original:{url:\"/static/broke.gif\"}}}");

		Mockito.when(giphyClient.getRandomGifInfoBySearch(eq("rich"), any(Integer.class))).thenReturn("{images:{original:{url:\"/static/rich.gif\"}}}");
		Mockito.when(giphyClient.getRandomGifInfoBySearch(eq("broke"), any(Integer.class))).thenReturn("{images:{original:{url:\"/static/broke.gif\"}}}");

		URI uriRich = new URI("/static/rich.gif");
		URI uriBroke = new URI("/static/broke.gif");

		ResponseEntity<byte[]> responseEntityRich = new ResponseEntity<>(HttpStatus.OK);
		ResponseEntity<byte[]> responseEntityBroke = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		Mockito.when(binaryObjectByUrlClient.get(uriRich)).thenReturn(responseEntityRich);
		Mockito.when(binaryObjectByUrlClient.get(uriBroke)).thenReturn(responseEntityBroke);
	}

	@Test
	void alfaServiceTagTest() throws URISyntaxException {

		Map<String, Double> todayRate = new HashMap<>();
		todayRateDto.setRates(todayRate);
		Map<String, Double> yesterdayRate = new HashMap<>();
		yesterdayRateDto.setRates(yesterdayRate);

		todayRate.put("RUB", 11.0);
		yesterdayRate.put("RUB", 10.0);
		ResponseEntity<byte[]> responseRich = alfaServiceTag.getGifByExchangeRate(today, yesterday, "RUB");
		assertEquals(HttpStatus.OK, responseRich.getStatusCode());

		todayRate.put("RUB", 10.0);
		yesterdayRate.put("RUB", 10.0);
		ResponseEntity<byte[]> responseBroke1 = alfaServiceTag.getGifByExchangeRate(today, yesterday, "RUB");
		assertEquals(HttpStatus.BAD_REQUEST, responseBroke1.getStatusCode());

		todayRate.put("RUB", 10.0);
		yesterdayRate.put("RUB", 11.0);
		ResponseEntity<byte[]> responseBroke2 = alfaServiceTag.getGifByExchangeRate(today, yesterday, "RUB");
		assertEquals(HttpStatus.BAD_REQUEST, responseBroke2.getStatusCode());
	}

	@Test
	void alfaServiceTagSearch() throws URISyntaxException {

		Map<String, Double> todayRate = new HashMap<>();
		todayRateDto.setRates(todayRate);
		Map<String, Double> yesterdayRate = new HashMap<>();
		yesterdayRateDto.setRates(yesterdayRate);

		todayRate.put("RUB", 11.0);
		yesterdayRate.put("RUB", 10.0);
		ResponseEntity<byte[]> responseRich = alfaServiceSearch.getGifByExchangeRate(today, yesterday, "RUB");
		assertEquals(HttpStatus.OK, responseRich.getStatusCode());

		todayRate.put("RUB", 10.0);
		yesterdayRate.put("RUB", 10.0);
		ResponseEntity<byte[]> responseBroke1 = alfaServiceSearch.getGifByExchangeRate(today, yesterday, "RUB");
		assertEquals(HttpStatus.BAD_REQUEST, responseBroke1.getStatusCode());

		todayRate.put("RUB", 10.0);
		yesterdayRate.put("RUB", 11.0);
		ResponseEntity<byte[]> responseBroke2 = alfaServiceSearch.getGifByExchangeRate(today, yesterday, "RUB");
		assertEquals(HttpStatus.BAD_REQUEST, responseBroke2.getStatusCode());
	}
}
