// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';

// -------------------------------------------------------------------
// Properties

// -------------------------------------------------------------------
// Exports

export function getArticleByReference(reference) {
  return Vue.http.get('/api/article/' + reference).then(res => res.body);
}

export function createArticle(article) {
  return Vue.http.post('api/article', article).then(res => res.body);
}
