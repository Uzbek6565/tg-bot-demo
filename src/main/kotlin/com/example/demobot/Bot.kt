package com.example.demobot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class SupportBot(private val callbackQueryHandler: CallbackQueryHandler,
private val messageHandler: MessageHandler
) : TelegramLongPollingBot(BotConfig.token) {


    override fun getBotUsername() = BotConfig.userName

    override fun onUpdateReceived(update: Update) {
        when {
            update.hasCallbackQuery() -> callbackQueryHandler.handle(update.callbackQuery, this)
            update.hasMessage() -> messageHandler.handle(update.message, this)
        }
    }

}