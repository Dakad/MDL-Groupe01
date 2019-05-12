import Vue from 'vue';

export const EventBus = new Vue();

// -------------------------------------------------------------------
// EVENTS

export const EVENT_APP_PING_API = 'app:ping';

export const EVENT_USER_LOGGED = 'user:logged';

export const EVENT_USER_LOGOUT = 'user:logout';

export const EVENT_USER_SIGNIN = 'user:signin';

export const EVENT_BYE_REDIRECTION = 'bye:redirection';

export const EVENT_APP_MESSAGE = 'app:message';
