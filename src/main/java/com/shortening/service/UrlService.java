package com.shortening.service;

import com.shortening.controller.dto.URLResponseDTO;
import com.shortening.exception.UrlNotFoundException;
import com.shortening.models.UrlEntity;
import com.shortening.repository.UrlRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public UrlEntity createUrlShort(String url){
        String code = UUID.randomUUID().toString().substring(0, 6);

        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setUrl(url);
        urlEntity.setShortUrl(code);
        urlEntity.setCreated(LocalDateTime.now());

        urlRepository.save(urlEntity);

        return urlEntity;

    }


    public void deleteUrl(String shortUrl){
        UrlEntity entity = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException(shortUrl));

        urlRepository.delete(entity);
    }

    public UrlEntity getUrlByShortUrl(String shortUrl)  {

        UrlEntity url = urlRepository.findByShortUrl(shortUrl).orElseThrow(() -> new UrlNotFoundException(shortUrl));

        url.setAccessCount(url.getAccessCount() + 1);

        urlRepository.save(url);

        return url;
    }

    public URLResponseDTO getStats(String shortUrl){
        UrlEntity url = urlRepository.findByShortUrl(shortUrl).orElseThrow(() -> new UrlNotFoundException(shortUrl));

        URLResponseDTO urlResponseDTO = new URLResponseDTO();
        urlResponseDTO.setUrl(url.getUrl());
        urlResponseDTO.setShortUrl(url.getShortUrl());
        urlResponseDTO.setAccessCount(url.getAccessCount());

        return urlResponseDTO;
    }


    public UrlEntity updateUrl(String shortUrl, String url) {

        UrlEntity urlEntity = getUrlByShortUrl(shortUrl);

        urlEntity.setUrl(url);
        urlEntity.setModified(LocalDateTime.now());

        urlRepository.save(urlEntity);

        return urlEntity;
    }

}
