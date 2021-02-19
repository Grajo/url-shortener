package com.example.urlshortener.service;

import com.example.urlshortener.dto.ShortenerRequest;
import com.example.urlshortener.repository.UrlRepository;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    @Autowired UrlRepository urlRepository;
    
    public String shortenUrl(ShortenerRequest request) {
        //generate short url. If it already exists generate a new key again.
        String shortUrl;

        do {
            shortUrl = createShortUrl();
        } while( !isShortUrlUnique(shortUrl) );

        //save shortUrl and realURL together, where shortUrl is the key
        urlRepository.save(shortUrl, request.getUrl());
        
        return shortUrl;
    }

    public String getRealURL(String shortUrl) {
        String url = urlRepository.findUrl(shortUrl);

        if (url == null) throw new RuntimeException("There is no URL for the provided shortUrl:" + shortUrl);

        return url;
    }

    private boolean isShortUrlUnique(String shortUrl) {
        return urlRepository.findUrl(shortUrl) == null;
    }

    private String createShortUrl() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('0', 'z')  //range covered: 0-9, A-Z and a-z
            .filteredBy(Character::isLetterOrDigit)
            .build();

        return generator.generate(7); //generates a String of 7 characters
    }
}
