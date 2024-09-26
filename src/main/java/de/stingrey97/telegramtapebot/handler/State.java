package de.stingrey97.telegramtapebot.handler;

public enum State {
    FIRST_TIME_ON_SERVER,
    LOGGED_OUT,

    // Registered user branch
    VALIDATE_USERNAME,
    VALIDATE_PIN,

    // New user branch
    AWAITING_DSGVO,
    AWAITING_ACTIVATION_CODE,
    REGISTER_USERNAME,
    REGISTER_PIN,

    // Default
    LOGGED_IN,
    GET_TITLE,
    GET_SUBJECT,
    REPLY_BY_TAPES,
    REPLY_FOR_TAPES,

    // Admin
    ADMIN,
    GET_INPUT,
    CONFIRM_INPUT,

    // Other
    ERROR_RETRIEVING_STATE,
}
