package com.example.config;

import com.example.service.Bot;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@ApplicationScoped
public class BotInitializer {

    private static final Logger log = LoggerFactory.getLogger(BotInitializer.class);
    private final Bot bot;

    @Inject
    public BotInitializer(Bot bot) {
        this.bot = bot;
    }

    public void onStart(@Observes StartupEvent event) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        log.info("Bot is starting with username: {}", bot.getBotUsername());

        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

}