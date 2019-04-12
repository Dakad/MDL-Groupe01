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

export function getTeam() {
  return Vue.http.get('api/team').then(function(response) {
    return response.body;
  });
}