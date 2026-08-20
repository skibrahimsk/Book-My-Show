package com.bookmyshow;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AppSmokeTest {

    @Test
    public void bookMyShowApplicationShouldBeReachable() throws Exception {
        String appUrl = System.getenv("APP_URL");

        Assert.assertNotNull(
            appUrl,
            "APP_URL environment variable must be configured in Jenkins"
        );

        HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(appUrl))
            .timeout(Duration.ofSeconds(30))
            .GET()
            .build();

        HttpResponse<String> response = client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );

        Assert.assertTrue(
            response.statusCode() >= 200 && response.statusCode() < 400,
            "Application returned HTTP status: " + response.statusCode()
        );

        Assert.assertFalse(
            response.body().isBlank(),
            "Application returned an empty response"
        );
    }
}
