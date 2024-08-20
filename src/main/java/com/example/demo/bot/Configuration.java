package com.example.demo.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Value("${TELEGRAM_BOT_TOKEN}")
    String botToken;

    @Bean
    public TelegramClient telegramClient() {
        TelegramClient telegramClient = new OkHttpTelegramClient(botToken);
        return telegramClient;
    }

}
