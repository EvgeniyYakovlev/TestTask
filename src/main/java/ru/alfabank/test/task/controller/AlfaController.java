package ru.alfabank.test.task.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.alfabank.test.task.Service.AlfaServiceSearch;
import ru.alfabank.test.task.Service.AlfaServiceTag;
import ru.alfabank.test.task.enums.Days;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ru.alfabank.test.task.enums.Days.TODAY;
import static ru.alfabank.test.task.enums.Days.YESTERDAY;


@Controller
public class AlfaController {

    @Autowired
    AlfaServiceTag alfaServiceTag;

    @Autowired
    AlfaServiceSearch alfaServiceSearch;

    @GetMapping(value = "gif-by-tag/last-change/exchange-rate/currency/{shot-name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getExchangeRateGifByTag(@PathVariable("shot-name") String base) throws IOException {
        try {
            return alfaServiceTag.getGifByExchangeRate(getDay(TODAY), getDay(YESTERDAY), base);
        }
        catch (Exception e) {
            return getErrorGif();
        }
    }

    @GetMapping(value = "gif-by-search/last-change/exchange-rate/currency/{shot-name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getExchangeRateGifBySearch(@PathVariable("shot-name") String base) throws IOException {
        try {
            return alfaServiceSearch.getGifByExchangeRate(getDay(TODAY), getDay(YESTERDAY), base);
        }
        catch (Exception e) {
            return getErrorGif();
        }
    }

    private ResponseEntity<byte[]> getErrorGif() throws IOException{
        InputStream in = getClass()
                .getResourceAsStream("/gif/error.gif");
        if (in != null) {
            return new ResponseEntity<>(IOUtils.toByteArray(in), HttpStatus.INTERNAL_SERVER_ERROR);
        } else throw new IOException("File not found");
    }

    private String getDay(Days day){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = format.format(date.getTime());
        String yesterday = format.format(date.getTime() - 24*60*60*1000);
        if (day.equals(TODAY)) {
            return today;
        }
        else if (day.equals(YESTERDAY)) {
            return yesterday;
        }
        else return null;
    }
}
