package com.vsoares.infosw.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class SwapiServiceImpl implements SwapiService{

    private final String URL_SERVICO = "https://swapi.dev/api/planets/?search=";

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Cacheable("resultsSwapi")
    @Override
    public int getAppearances(String name) {
        int appearances = 0;
        name.replace(" ", "%20");

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL_SERVICO+name))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if(response.statusCode() == 200){
            JSONObject obj = new JSONObject(response.body());
            int countResults = obj.getInt("count");
            if(countResults == 1) {
                JSONArray results = obj.getJSONArray("results");
                JSONObject planetResult = (JSONObject) results.get(0);
                appearances = planetResult.getJSONArray("films").length();
            }
        }
        return appearances;
    }
}
