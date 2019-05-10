// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';

import { getAuthHeaders } from './api';

// -------------------------------------------------------------------
// Properties

// -------------------------------------------------------------------
// Exports

export function ping() {
  return Vue.http.get('/api/zen').then(resp => resp.body != null);
}


export function getSota(reference) {
  return Vue.http.get('/api/sota/' + reference).then(function(response) {
    return response.body;
  });
}

export function createSota(sota) {
  const headers = getAuthHeaders();
  return Vue.http.post('/api/sota', sota, headers).then(res => res.body);
}

export function sotaPostBookmark(reference) {
  const headers = getAuthHeaders();
  return Vue.http.post('/api/sota/' + reference + "/bookmark",{} , headers).then(res => res.body);
}

export function sotaGetBookmark(reference) {
  const headers = getAuthHeaders();
  return Vue.http.get('/api/sota/' + reference + "/bookmarked" , headers).then(res => res.body);
}

export function sotaDeleteBookmark(reference) {
  const headers = getAuthHeaders();
  return Vue.http.delete('/api/sota/' + reference + "/bookmark" , headers).then(res => res.body);
}
