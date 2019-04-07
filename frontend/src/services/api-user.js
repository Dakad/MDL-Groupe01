// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';

// Mine
import { addToStorage, isInStorage, removeFromStorage } from './storage';

// -------------------------------------------------------------------
// Properties

const KEY_AUTH_TOKEN = 'AUTH_TOKEN';

// -------------------------------------------------------------------
// Exports

export function isLogged() {
  return isInStorage(KEY_AUTH_TOKEN);
}

export function logout() {
  removeFromStorage(KEY_AUTH_TOKEN);
}

export function postLogin(credentials) {
  return Vue.http.post('api/login', credentials).then(res => {
    let token = res.headers.get('authorizations');
    if (!token) {
      token = res.body['auth_token_type'] + res.body['auth_token'];
    }
    addToStorage(KEY_AUTH_TOKEN, token);
    return res.json();
  });
}

export function postSignin(data) {
  return Vue.http.post('api/signin', data);
}
