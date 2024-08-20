package com.example.demo.bot;

import com.example.demo.handlers.UpdateHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Slf4j
@Data
@Component
public class CommerceValerikaBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    @Value("${TELEGRAM_BOT_TOKEN}")
    String botToken;
    @Value("${TELEGRAM_BOT_NAME}")
    private String botName;

    @Autowired
    private List<UpdateHandler> handlers;

    public CommerceValerikaBot() {
        this.botName = "botName";
    }

    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(List<Update> updates) {
        LongPollingSingleThreadUpdateConsumer.super.consume(updates);
    }

    @Override
    public void consume(Update update) {
        handlers.forEach(handler -> handler.handleUpdate(update));
    }
}
