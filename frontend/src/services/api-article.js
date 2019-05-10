// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';

// Mine
import { getAuthHeaders } from './api';

// -------------------------------------------------------------------
// Properties

// -------------------------------------------------------------------
// Exports

export function getArticleByReference(reference) {
  return Vue.http.get('/api/article/' + reference).then(res => res.body);
}

export function createArticle(article) {
  const headers = getAuthHeaders();
  return Vue.http.post('/api/article', article, headers).then(res => res.body);
}

export function getBookmarked(reference) {
  const headers = getAuthHeaders();
  return Vue.http.get('/api/article/' + reference + '/bookmarked', headers).then(res => res.body)
}

export function postBookmark(reference){
  const headers = getAuthHeaders();
  return Vue.http.post('/api/article/' + reference + '/bookmark',{}, headers).then(res => res.body)
}

export function deleteBookmark(reference){
  const headers = getAuthHeaders();
  return Vue.http.delete('/api/article/' + reference + '/bookmark', headers).then(res => res.body)
}
