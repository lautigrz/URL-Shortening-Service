package com.shortening.controller;

import com.shortening.controller.dto.URLRequestDTO;
import com.shortening.controller.dto.URLResponseDTO;
import com.shortening.models.UrlEntity;
import com.shortening.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ShorteningController {

    private final UrlService urlService;

    public ShorteningController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shortening(@RequestBody URLRequestDTO urlRequestDTO) {
        UrlEntity urlEntity = urlService.createUrlShort(urlRequestDTO.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(response(urlEntity));

    }

    @GetMapping("/shorten/{shortUrl}")
    public ResponseEntity<Map<String,Object>> getUrl(@PathVariable String shortUrl) {
        UrlEntity urlEntity = urlService.getUrlByShortUrl(shortUrl);

        return ResponseEntity.status(HttpStatus.OK).body(response(urlEntity));

    }

    @PutMapping("/shorten/{shortUrl}")
    public ResponseEntity<Map<String,Object>> updateUrl(@PathVariable String shortUrl, @RequestBody URLRequestDTO urlRequestDTO) {

        UrlEntity urlEntity = urlService.updateUrl(shortUrl, urlRequestDTO.getUrl());

        return ResponseEntity.status(HttpStatus.OK).body(response(urlEntity));
    }

    @GetMapping("/shorten/{shortUrl}/stats")
    public ResponseEntity<URLResponseDTO> getStats(@PathVariable String shortUrl) {
        URLResponseDTO urlResponseDTO = urlService.getStats(shortUrl);
        return ResponseEntity.status(HttpStatus.OK).body(urlResponseDTO);
    }


    @DeleteMapping("/shorten/{shortUrl}")
    public ResponseEntity<?> deleteUrl(@PathVariable String shortUrl) {
        urlService.deleteUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/r/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {

        UrlEntity urlEntity = urlService.getUrlByShortUrl(shortUrl);

        URI uri = URI.create(urlEntity.getUrl());

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(uri)
                .build();
    }

    private Map<String, Object> response(UrlEntity urlEntity) {
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("url", urlEntity.getUrl());
        body.put("shortUrl", urlEntity.getShortUrl());
        body.put("created",urlEntity.getCreated());
        body.put("modified",urlEntity.getModified());
        return body;
    }

}
