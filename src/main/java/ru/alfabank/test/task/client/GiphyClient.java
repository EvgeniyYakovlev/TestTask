package ru.alfabank.test.task.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="GiphyClient", url = "${giphy.url}")
public interface GiphyClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "${giphy.url.search.value}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    String getRandomGifInfoBySearch(@RequestParam("q") String search,
                                        @RequestParam("offset") Integer offset);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "${giphy.url.random.value}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    String getRandomGifInfoByTag(@RequestParam("tag") String tag);
}
