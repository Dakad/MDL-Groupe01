import Vue from 'vue';

export const EventBus = new Vue();

export const EVENT_USER_LOGOUT = 'user:logout';

export const EVENT_BYE_REDIRECTION = 'bye:redirection';