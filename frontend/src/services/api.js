// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';

// -------------------------------------------------------------------
// Properties

// -------------------------------------------------------------------
// Exports

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
