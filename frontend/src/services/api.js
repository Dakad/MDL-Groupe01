// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';

// -------------------------------------------------------------------
// Properties
const SEARCH_FOR = ['all', 'sotas', 'authors', 'user', 'articles'];

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
  const isSearchValid = SEARCH_FOR.includes(searchQuery['only']);
  if (!isSearchValid) {
    searchQuery['only'] = SEARCH_FOR[0];
  }
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
