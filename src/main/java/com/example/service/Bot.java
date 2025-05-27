package com.example.service;

import com.example.commands.BenchPressCommandHandler;
import com.example.commands.BjuCommanderHandler;
import com.example.config.BotConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;


@ApplicationScoped
public class Bot extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory.getLogger(Bot.class);
    SendMessage sendMessage = new SendMessage();

    @Inject
    BotConfig config;

    @Inject
    BenchPressCommandHandler benchPressCommandHandler;
    @Inject
    BjuCommanderHandler bjuCommanderHandler;

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            Chat chat = update.getMessage().getChat();
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String command = messageText.split(" ")[0].split("@")[0];

            String userFirstName = update.getMessage().getFrom().getFirstName();
            String userLastName = update.getMessage().getFrom().getLastName();
            String username = update.getMessage().getFrom().getUserName();


            switch (command) {
                case "/start":
                   sendMessage(chatId, "Hello " + userFirstName);
                   log.info(userFirstName, username);
                   break;

                case "/bench_press":
                    log.info(userFirstName, username);
                    String[] parts = messageText.split(" ");
                    String[] args = Arrays.copyOfRange(parts, 1, parts.length);
                    String response = benchPressCommandHandler.handle(args, chatId);
                    sendMessage(chatId, response);
                    break;

                case "/bju":
                    log.info(userFirstName);
                    String[] bjuParts = messageText.split(" ");
                    String[] bjuArgs = Arrays.copyOfRange(bjuParts, 1, bjuParts.length);
                    String bjuResponse = bjuCommanderHandler.handle(bjuArgs, chatId);
                    sendMessage(chatId, bjuResponse);
            }
        }

    }

    public void sendMessage(long chatId, String testToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(testToSend);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
