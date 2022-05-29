package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.facilities.TelegramHttpClientBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
public class StatsService {

    private final CloseableHttpClient httpClient;
    @Value("${API_URL}")
    String endpoint;

    public StatsService() {
        this.httpClient = TelegramHttpClientBuilder.build(new DefaultBotOptions());
    }

    public String recordStats(RunResult runResult) throws JsonProcessingException, UnsupportedEncodingException {
        HttpPost httppost = new HttpPost(endpoint);

        StringEntity postingString = new StringEntity(new JsonMapper().writeValueAsString(runResult));

        httppost.setEntity(postingString);

        String result = null;
        try {
            result = sendHttpPostRequest(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String sendHttpPostRequest(HttpPost httppost) throws IOException {
        try (CloseableHttpResponse response = httpClient.execute(httppost)) {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
    }
}
