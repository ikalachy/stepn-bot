package com.example.demo.handlers;

import com.example.demo.model.RunResult;
import com.example.demo.services.StatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class StepnPostHandler implements UpdateHandler {

    @Value("${API_TOKEN}")
    String apiToken;

    @Autowired
    private StatsService statsService;

    @Override
    public BotApiMethod<Message> handleUpdate(Update update) {
        final Message post = update.getChannelPost();

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

                return response;
            } catch (Exception ex) {
                System.out.println("Err: " + ex.getMessage());
                return new SendMessage(String.valueOf(post.getChatId()),
                        "Opps, err: " + post.getText());
            }

        }
        return null;
    }
}
