package com.example.urlshortener.controller;

import java.util.regex.Pattern;

import javax.validation.Valid;

import com.example.urlshortener.dto.ShortenerRequest;
import com.example.urlshortener.service.UrlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/shortener")
public class UrlController {
    
    @Autowired UrlService urlService;
    @Value("${server.host}") private String serverHost;
    @Value("${server.port}") private String serverPort;

    // Url Pattern validator taken from https://www.geeksforgeeks.org/check-if-an-url-is-valid-or-not-using-regular-expression/
    private static final String IS_URL_VALID = "((http|https)://)(www.)?" // starts with http or https, followed by :// and must containafter that www.
                                        + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                                        + "{2,256}\\.[a-z]" //followed of subdomain of length (2,256)
                                        + "{2,6}\\b([-a-zA-Z0-9@:%"
                                        + "._\\+~#?&//=]*)"; //last part contains top level domain like .com, .org etc.

    private static final Pattern URL_PATTERN = Pattern.compile(IS_URL_VALID);

    @PostMapping
    public ResponseEntity<String> shortenUrl(@RequestBody @Valid ShortenerRequest request) throws Exception {
        //check if what is being passed is a real URL 
        if ( !URL_PATTERN.matcher(request.getUrl()).matches()) {
            throw new Exception("That's not a URL, please provide a valid one.");
        }

        String shortUrl = urlService.shortenUrl(request);
        return ResponseEntity.ok(serverHost + ":" + serverPort + "/api/shortener/" + shortUrl);
    }

    @GetMapping(value="/{shortUrl}")
    public RedirectView getMethodName(@PathVariable String shortUrl) {
        String realUrl = urlService.getRealURL(shortUrl);
        RedirectView redirecTO = new RedirectView();
        redirecTO.setUrl(realUrl);

        return redirecTO;
    }
    
}
