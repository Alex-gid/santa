package dev.cher.santa.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Data
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.owner}")
    private Long ownerId;

    @Value("${cron.nonscheduled}")
    private String sheduled;

    @Value("${cron.scheduledTest}")
    private String sheduledTest;
}
