package com.example.worldcinema.ui.dialog

enum class AlertType {
    EMPTY_FIRST_NAME,
    EMPTY_LAST_NAME,
    EMPTY_EMAIL,
    EMPTY_PASSWORD,
    EMPTY_REPEAT_PASSWORD,

    EMPTY_CHAT_MESSAGE,

    EMPTY_COLLECTION_NAME,

    WRONG_EMAIL,
    WRONG_REPEAT_PASSWORD,

    AUTH_NOT_SUCCESS,
    REGISTER_NOT_SUCCESS,

    COLLECTION_WITH_FAVOURITE_NAME,

    DEFAULT,

    BAD_API,
    BAD_FIGMA,

    SERVER_ERROR,

    NO_PERMISSIONS
}