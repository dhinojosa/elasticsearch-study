package com.evolutionnext;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ClusterHealthTest {

    Logger logger = LoggerFactory.getLogger(ClusterHealthTest.class);

    @Test
    void testClusterHealth() throws URISyntaxException, IOException,
        InterruptedException {
        logger.debug("Getting Health from Cluster");
        String uri = "/_cluster/health";

        HttpClient httpClient =
            HttpClient.newHttpClient();
        HttpRequest httpRequest =
            HttpRequest
                .newBuilder(new URI("http://localhost:9200/%s".formatted(uri)))
                .GET()
                .build();
        HttpResponse<String> httpResponse =
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String body = httpResponse.body();
        System.out.println(body);
    }
}
