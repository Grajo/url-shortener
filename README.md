URL Shortener - Java Service built with Spring Boot, and Redis.

UrlController.java - A Spring Boot Controller responsible for: 
    a. Serving an endpoint to shorten URL 
    b. Redirect shortened URL to the original URL 
UrlRepository - Java class responsible for abstracting Redis read/write logic
UrlService.java - Java class used to abstract URL Shortening and URL Retrieval process


To run:
1. Start up Redis' Server on cmd terminal (If needed please follow the installation guide https://divyanshushekhar.com/how-to-install-redis-on-windows-10/)
    redis-server

2. Open your favourite IDE and run UrlShortenerApplication.java


By default the Server will run on localhost:8080/api/shortener 
To test, send POST Request to localhost:8080/api/shortener with a body of type application/json with body 
    {
    'url' : '<INSERT URL>'
    }


