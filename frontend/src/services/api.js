// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';
import { getAuthToken } from './storage';

// -------------------------------------------------------------------
// Properties
const KEY_AUTH_TOKEN = 'AUTH_TOKEN';
const KEY_USERNAME = 'AUTH_USERNAME';
const SEARCH_FOR_VALUES = ['all', 'sotas', 'authors', 'user', 'articles'];

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
  const isSearchValid = SEARCH_FOR_VALUES.includes(searchQuery['only']);
  if (!isSearchValid) {
    searchQuery['only'] = SEARCH_FOR_VALUES[0];
  }
  return Vue.http.get('/api/search', { params: searchQuery }).then(res => res.body);
}

export function getTags(searchTerm){
  return Vue.http.get('/api/tags?k='+searchTerm).then(res =>res.body);
}

export function getArticleByReference(reference) {
  return Vue.http.get('/api/article/' + reference).then(res => res.body);
}

export function getArticlesByCategories(categories) {
  if (categories.length <= 0) {
    return Promise.reject();
  }
  return Vue.http
    .get('/api/article', {
      params: {
        category: categories.join(',')
      }
    })
    .then(res => res.body);
}

export function exportAsJson(data, filename) {
  if (filename && filename.endsWith('.json')) {
    filename += '.json';
  }
  const fileToSave = new Blob([JSON.stringify(data, undefined, 2)], {
    type: 'application/json'
  });
  return URL.createObjectURL(fileToSave);
}

export function exportAsBibtex(data, filename) {
  if (filename && filename.endsWith('.bib')) {
    filename += '.bib';
  }
  const fileToSave = new Blob([data], {
    type: 'application/x-bibtex'
  });
  return URL.createObjectURL(fileToSave);
}
