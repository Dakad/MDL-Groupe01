// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';
// -------------------------------------------------------------------
// Properties

const Storage = localStorage;

const KEY_AUTH_TOKEN = 'AUTH_TOKEN';
const KEY_USERNAME = 'AUTH_USERNAME';

// -------------------------------------------------------------------
// Exports

export { KEY_AUTH_TOKEN, KEY_USERNAME };

export function isInStorage(key) {
  return Storage.hasOwnProperty(key);
}

export function getFromStorage(key) {
  return JSON.parse(Storage.getItem(key));
}

export function addToStorage(key, item) {
  Storage.setItem(key, JSON.stringify(item));
}

export function removeFromStorage(key) {
  Storage.removeItem(key);
}

export function getAuthToken() {
  return getFromStorage(KEY_AUTH_TOKEN);
}

/**
 * Retrieve the list either from the CacheStorage or directly through the API.
 * @param {String} url API url where to get the list.
 * @returns {Promise} containing the resolved list.
 */
export function getList(url) {
  const key = 'API_LIST_FOR_' + url.replace(/\//g, '').toUpperCase();
  if (isInStorage(key)) {
    return Promise.resolve(getFromStorage(key));
  } else {
    return Vue.http.get(url).then(({ data }) => {
      addToStorage(key, data);
      return data;
    });
  }
}
