package com.example.demo.bot;

import com.example.demo.handlers.UpdateHandler;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@Component
public class MyBot extends TelegramLongPollingBot {

    private String botName;
    private String botToken;
    private String apiToken;


    @Autowired
    private List<UpdateHandler> handlers;

    public MyBot(
            @Value("${TELEGRAM_BOT_TOKEN}") String botToken,
            @Value("${TELEGRAM_BOT_NAME}") String botName,
            @Value("${API_TOKEN}") String token) {
        this.botToken = botToken;
        this.botName = botName;
        this.apiToken = token;
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        handlers.forEach(handler -> handleUpdate(handler, update));
    }

    private void handleUpdate(UpdateHandler handler, Update update) {
        //BotApiMethod<Message> message = handler.handleUpdate(update);
        Optional.ofNullable(handler.handleUpdate(update)).ifPresent(botMessage -> {
            try {
                execute(botMessage);
            } catch (TelegramApiException ex) {
                log.error("Bot Err: {}", ex.getMessage());
            }
        });
    }
}
