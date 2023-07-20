package com.example.demobot

import lombok.AllArgsConstructor
import lombok.Data
import lombok.Value
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component

@Component
class BotConfig {
    companion object{
        val userName  = "Suppurtjon_bot"
        val token = "6162651353:AAGFkSOKUqf7dK5qRkQZ2WhZl6xaQsRZQqA"
    }
}