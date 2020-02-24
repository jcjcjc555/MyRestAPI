package com.example.demo.util;

import org.springframework.web.client.RestTemplate;

public class RestTemplateSingleton {

    private static RestTemplate restTemplate;

    private RestTemplateSingleton() {}

    public static RestTemplate getter() {

        if (restTemplate == null) {
            synchronized (RestTemplateSingleton.class) {
                if (restTemplate == null) {
                    restTemplate = new RestTemplate();
                }
            }
        }
        return restTemplate;

    }
}
