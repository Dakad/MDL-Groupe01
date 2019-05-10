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
  return Vue.http.get('/api/article/' + reference + '/bookmarked').then(res => res.body)
}

export function postBookmark(reference){
  return Vue.http.post('/api/article/' + reference + '/bookmark').then(res => res.body)
}

export function deleteBookmark(reference){
  return Vue.http.delete('/api/article/' + reference + '/bookmark').then(res => res.body)
}
