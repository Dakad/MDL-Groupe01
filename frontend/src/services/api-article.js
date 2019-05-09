// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';

// Mine
import { getAuthToken } from './storage';

// -------------------------------------------------------------------
// Properties

// -------------------------------------------------------------------
// Exports

export function getArticleByReference(reference) {
  return Vue.http.get('/api/article/' + reference).then(res => res.body);
}

export function createArticle(article) {
  const token = getAuthToken();
  // TODO Add the token in the request header to create the new article
  return Vue.http.post('api/article', article).then(res => res.body);
}
