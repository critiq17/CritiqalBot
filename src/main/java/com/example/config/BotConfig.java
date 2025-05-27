package com.example.config;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class BotConfig {

    @ConfigProperty(name = "bot.token")
    String token;

    @ConfigProperty(name = "bot.username")
    String username;

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }
}


