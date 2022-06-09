package ru.alfabank.test.task.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;

@FeignClient(name="BinaryObjectByUrlClient", url = "https://this-is-a-placeholder.com")
public interface BinaryObjectByUrlClient {
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.IMAGE_GIF_VALUE)
    ResponseEntity<byte[]> get(URI baseUrl);
}
