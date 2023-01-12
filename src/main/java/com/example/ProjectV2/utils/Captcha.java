package com.example.ProjectV2.utils;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

public class Captcha {
    private static final String url = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    private static final String secretKey = "6Lf4APIjAAAAAMXCXsBZjX9LHslPdkLQtc0wJvsg";
    private final String requestUrl;

    public Captcha(String captchaValue) {
        requestUrl = String.format(url, secretKey, captchaValue);
    }

    public Boolean isValid() {
        CaptchaResponse response = WebClient.create()
                .get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(CaptchaResponse.class) //to mono object
                .block(); //moto to object
        return response.success;
    }
}

@Data
class CaptchaResponse {
    Boolean success;
    String challenge_ts;
    String hostname;
}
