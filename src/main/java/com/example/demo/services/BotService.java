package com.example.demo.services;

import com.example.demo.handlers.UpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class BotService {

    @Autowired
    private List<UpdateHandler> handlers;

    public void consume(Update update) {
        handlers.forEach(handler -> handler.handleUpdate(update));
    }
}
