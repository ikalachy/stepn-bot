package com.example.demo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MyBot extends TelegramLongPollingBot {

    private String endpoint;
    private String botToken;
    private String apiToken;

    @Autowired
    private StatsService statsService;

    public MyBot(
            @Value("${TELEGRAM_BOT_TOKEN}") String botToken,
            @Value("${API_URL}") String endpoint,
            @Value("${API_TOKEN}") String token) {
        this.botToken = botToken;
        this.endpoint = endpoint;
        this.apiToken = token;
    }

    @Override
    public String getBotUsername() {
        return "razam_music_chart_bot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        final Message message = update.getMessage();
        final Message post = update.getChannelPost();
        if (message != null && message.isCommand()) {

            List<String> options = new ArrayList<>();
            options.add("Yes");
            options.add("No");

            // Let's just assume we get the chatMessage as a parameter. For example from the message received, or from a database
            SendPoll ourPoll = new SendPoll(String.valueOf(message.getChatId()), "Some Question", options);
            execute(ourPoll);


            log.info("will do command ");
        }

        if (post != null && post.getText() != null) {
            try {
                Double gstAmount = Double.valueOf(post.getText());

                SendMessage
                        response = new SendMessage(String.valueOf(post.getChatId()), "Great results! adding -> " + post.getText());

                if (statsService != null) {
                    RunResult result = RunResult.builder()
                            .gst(gstAmount)
                            .owner("Bot").energy(0.6).token(apiToken).build();

                    statsService.recordStats(result);
                }

                execute(response);
            } catch (Exception ex) {
                System.out.println("Err: " + ex.getMessage());
                SendMessage
                        err = new SendMessage(String.valueOf(post.getChatId()),
                        "Opps, err: " + post.getText());
                execute(err);

            }

        }
    }
}
