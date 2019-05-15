// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';

// Mine
import {
  addToStorage,
  getFromStorage,
  isInStorage,
  removeFromStorage,
  KEY_AUTH_TOKEN,
  KEY_USERNAME
} from './storage';

import { getAuthHeaders } from './api';

// -------------------------------------------------------------------
// Properties

// -------------------------------------------------------------------
// Exports

export function isLogged() {
  return isInStorage(KEY_AUTH_TOKEN);
}

export function logout() {
  removeFromStorage(KEY_AUTH_TOKEN);
}

export function postLogin(credentials) {
  return Vue.http.post('/api/login', credentials).then(res => {
    let token = res.headers.get('authorization');
    if (!token) {
      token = res.body['auth_token_type'] + res.body['auth_token'];
    }
    addToStorage(KEY_AUTH_TOKEN, token);
    addToStorage(KEY_USERNAME, credentials['username']);
    return res.json();
  });
}

export function postSignin(data) {
  return Vue.http.post('/api/signin', data);
}

export function getProfileBase(username) {
  if (username == undefined) {
    username = getFromStorage(KEY_USERNAME);
  }
  return Vue.http.get('/api/user/' + username + '/profile/base').then(response => response.body);
}

export function getBookmark(username) {
  if (username == undefined) {
    username = getFromStorage(KEY_USERNAME);
  }
  return Vue.http
    .get('/api/user/' + username + '/profile/bookmarks')
    .then(response => response.body);
}

export function getProfileInfoPro(username) {
  if (username == undefined) {
    username = getFromStorage(KEY_USERNAME);
  }
  return Vue.http.get('/api/user/' + username + '/profile/pro').then(response => response.body);
}

export function getProfileSota(username) {
  if (username == undefined) {
    username = getFromStorage(KEY_USERNAME);
  }
  return Vue.http.get('/api/user/' + username + '/profile/sota').then(response => response.body);
}


export function postModificationProfile(data) {
  const headers = getAuthHeaders();
  return Vue.http.post('/api/user/profile/update', data, headers).then(response => response.body);
}

export function getProfileSocial(username) {
  if (username == undefined) {
    username = getFromStorage(KEY_USERNAME);
  }
  return Vue.http.get('/api/user/' + username + '/profile/social').then(response => response.body);

  export function postFollow(username) {
  const headers = getAuthHeaders();
  return Vue.http.post('/api/user/' + username + '/follow',{}, headers).then(response => response.body);
}

export function postUnFollow(username) {
  const headers = getAuthHeaders();
  return Vue.http.post('/api/user/' + username + '/unfollow',{}, headers).then(response => response.body);
}

export function getFollow(username) {
  const headers = getAuthHeaders();
  return Vue.http.get('/api/user/' + username + '/followed', headers).then(response => response.body);
}
