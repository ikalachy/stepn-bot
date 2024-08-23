package com.example.demo.controllers;

import com.example.demo.services.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@RestController
public class ApiController {

    @Autowired
    BotService botService;

    @Autowired
    TelegramClient telegramClient = null;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @CrossOrigin(origins = {"*"})
    @RequestMapping(path = "/web-data", method = RequestMethod.POST)
    public String webData(@RequestBody OrderMessage message) {

        System.out.println(message);

        SendMessage messageSend = SendMessage // Create a message object
                .builder().chatId(message.queryId).text(message.toString()).build();

        try {
            telegramClient.execute(messageSend); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return "Ok!";
    }

    @RequestMapping(path = "/bot-updates", method = RequestMethod.POST)
    public String receiveUpdates(@RequestBody Update update) {
        botService.consume(update);
        return "Ok!";
    }

}
