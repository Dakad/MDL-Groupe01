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

export function getSota() {
  return Vue.http.get('/api/sota', sota).then(function(response) {
    return response.body;
  });
}

export function createSota(sota) {
  const headers = getAuthHeaders();
  return Vue.http.post('/api/sota', sota, headers).then(res => res.body);
}
