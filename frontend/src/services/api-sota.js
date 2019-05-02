// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';

// -------------------------------------------------------------------
// Properties

// -------------------------------------------------------------------
// Exports

export function ping() {
  return Vue.http.get('api/zen').then(resp => resp.body != null);
}

export function getSota() {
  return Vue.http.get('api/sota', sota).then(function(response) {
    return response.body;
  });
}

export function createSota(sota) {
  return Vue.http.post('api/sota', sota).then(res => res.body);
}
