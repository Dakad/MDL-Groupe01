import Vue from 'vue';
import Router from 'vue-router';

import NotFound from './pages/NotFound.vue';
import Accueil from './pages/AccueilVue.vue';
// import Resultat from './pages/Resultat.vue';

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    { name: 'accueil', path: '/', component: Accueil },

    // { name: 'resultat', path: '/result', component: Resultat },

    { name: '404', path: '/404', component: NotFound },

    { name: '', path: '*', redirect: '/404' }
  ]
});
