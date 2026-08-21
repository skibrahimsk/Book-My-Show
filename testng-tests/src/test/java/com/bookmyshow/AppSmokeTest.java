package com.bookmyshow;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;

public class AppSmokeTest {

    @Test
    public void bookMyShowApplicationShouldBeReachable() throws Exception {
        String appUrl = System.getenv("APP_URL");

        Assert.assertNotNull(
            appUrl,
            "APP_URL environment variable must be configured in Jenkins"
        );

        Process process = new ProcessBuilder(
            "curl",
            "--fail",
            "--silent",
            "--show-error",
            "--retry", "5",
            "--retry-delay", "5",
            "--retry-all-errors",
            "--connect-timeout", "15",
            "--max-time", "60",
            appUrl
        ).redirectErrorStream(true).start();

        String response = new String(
            process.getInputStream().readAllBytes(),
            StandardCharsets.UTF_8
        );

        int exitCode = process.waitFor();

        Assert.assertEquals(
            exitCode,
            0,
            "Application request failed: " + response
        );

        Assert.assertFalse(
            response.isBlank(),
            "Application returned an empty response"
        );
    }
}
