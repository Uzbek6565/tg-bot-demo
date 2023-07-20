package com.example.demobot

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender

interface CallbackQueryHandler{
    fun handle(callbackQuery : CallbackQuery, sender : AbsSender)
}

interface MessageHandler{
    fun handle(message: Message, sender: AbsSender)
}

@Service
class CallbackQueryHandlerImpl : CallbackQueryHandler{
    override fun handle(callbackQuery: CallbackQuery, sender: AbsSender) {

    }
}

@Service
class MessageHandlerImpl : MessageHandler{
    override fun handle(message: Message, sender: AbsSender) {
        val text = message.text
        val telegramUser = message.from
        val chatId = message.chatId
        val messageId = message.messageId
        val forwardFrom = message.forwardFrom

        val sendMessage = SendMessage()
        sendMessage.text = "Hi $telegramUser"
        sender.execute(sendMessage)

    }

}