package com.evolutionnext;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ElasticSearchPut {

    Logger logger = LoggerFactory.getLogger(ElasticSearchPut.class);

    @Test
    public void testSearchName() throws IOException {

        Base64.Encoder encoder = Base64.getEncoder();
        logger.debug("Connecting to the elastic search server");
        URL url = new URL("http://54.212.31.19/elasticsearch/blogs");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String userCredentials = "user:WOlBHb5SXs05";
        String basicAuth = "Basic " + new String(encoder.encode(userCredentials.getBytes()));

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", basicAuth);

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseMessage());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        br.lines().forEach(System.out::println);

        conn.disconnect();
    }


    @Test
    public void testSearch() throws IOException {

        String json = """
            {"user":"kimchy","postDate":"2013-02-12",
             "message":"trying out Elasticsearch 2"}""";

        Base64.Encoder encoder = Base64.getEncoder();
        URL url = new URL("http://54.212.31.19/elasticsearch/_search?q=pony");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String userCredentials = "user:WOlBHb5SXs05";
        String basicAuth = "Basic " + new String(encoder.encode(userCredentials.getBytes()));

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", basicAuth);


        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseMessage());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        br.lines().forEach(System.out::println);

        conn.disconnect();
    }

    @Test
    public void testCreateID() throws IOException {

        String json = """
            {
              "settings": {
                "number_of_replicas": 1,
                "number_of_shards": 1,
                "analysis": {},
                "refresh_interval": "1s"
              },
              "mappings": {
                "blog": {
                  "properties": {
                    "title": {
                      "type": "text",
                      "analyzer": "english"
                    }
                  }
                }
              }
            }
            """;

        Base64.Encoder encoder = Base64.getEncoder();
        URL url = new URL("http://54.212.31.19/elasticsearch/");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String userCredentials = "user:WOlBHb5SXs05";
        String basicAuth = "Basic " + new String(encoder.encode(userCredentials.getBytes()));

        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", basicAuth);

        conn.connect();

        OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream());
        wr.append(json);
        wr.flush();
        wr.close();

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseMessage());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        br.lines().forEach(System.out::println);

        conn.disconnect();
    }

    @Test
    public void testCreateDocument() throws IOException {

        String json = """
            {"title" : "Queen was a great rock band","author" : "Montel Williams","content" : "Figaro"}""";

        Base64.Encoder encoder = Base64.getEncoder();
        URL url = new URL("http://54.212.31.19/elasticsearch/blogs/currentevents");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String userCredentials = "user:WOlBHb5SXs05";
        String basicAuth = "Basic " + new String(encoder.encode(userCredentials.getBytes()));

        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", basicAuth);

        conn.connect();

        OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream());
        wr.append(json);
        wr.flush();
        wr.close();

        System.out.println(conn.getResponseCode());
        System.out.println(conn.getResponseMessage());

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        br.lines().forEach(System.out::println);

        conn.disconnect();
    }
}
