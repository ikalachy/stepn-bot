package com.example.demo.handlers;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
//@Component
public class CommandHandler implements UpdateHandler {

    @Override
    public SendMessage handleUpdate(Update update) {
        final Message message = update.getMessage();
        if (message != null && message.isCommand()) {
            log.info("Command: {}", message);
        }
        return null;
    }
}
