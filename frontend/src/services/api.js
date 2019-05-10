// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';
import { getAuthToken } from './storage';

// -------------------------------------------------------------------
// Properties
const KEY_AUTH_TOKEN = 'AUTH_TOKEN';
const KEY_USERNAME = 'AUTH_USERNAME';

// -------------------------------------------------------------------
// Exports

export function getAuthHeaders() {
  const authToken = getAuthToken();
  return {
    headers: {
      Authorization: authToken
    }
  };
}

export function ping() {
  return Vue.http.get('/api/zen').then(resp => resp.body != null);
}

export function getTeam() {
  return Vue.http.get('/api/team').then(function(response) {
    return response.body;
  });
}

export function getSearchResults(searchQuery) {
  return Vue.http.get('/api/search', { params: searchQuery }).then(res => res.body);
}

export function getArticleByReference(reference) {
  return Vue.http.get('/api/article/' + reference).then(res => res.body);
}

export function getArticlesByCategories(categories) {
  if (categories.length <= 0) {
    return;
  }

  return Vue.http
    .get('/api/article', {
      params: {
        category: categories.join(',')
      }
    })
    .then(res => res.body);
}
