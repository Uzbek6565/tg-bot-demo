package com.example.demobot

import java.io.Serializable


enum class Role() {
    ADMIN, CLIENT, OPERATOR
}

enum class Language: Serializable {
    UZ, RU, EN
}

enum class MessageType() {
    REQUEST, RESPONSE
}

enum class UserState() {
    START,CHOOSE_LANG,SHARE_PHONE_NUMBER,QUESTION, DELETE_PHONE_NUMBER,GET_ONE_OPERATOR, ALL_OPERATORS, STATUS_OPERATOR, ANSWER_STATE
}

enum class ContentType {
    TEXT, PHOTO, VIDEO, DOCUMENT, STICKER, AUDIO, VOICE, FORWARD, GIF
}

enum class ErrorCode(val code: Int) {
    OPERATOR_NOT_FOUND(100),
    USER_NOT_FOUND(101),
}