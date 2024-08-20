package com.example.demo.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Slf4j
@Component
public class CommandHandler implements UpdateHandler {

    @Override
    public void handleUpdate(Update update) {
        final Message message = update.getMessage();
        if (message != null && message.isCommand()) {
            log.info("Command: {}", message.isCommand());
        }
    }
}
