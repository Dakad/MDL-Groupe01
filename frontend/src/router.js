import Vue from 'vue';
import Router from 'vue-router';

import NotFound from './pages/NotFound.vue';
import Accueil from './pages/Accueil.vue';
import Resultat from './pages/Resultat.vue';
import Article from './pages/Article';
import Sota from'./pages/Sota';


Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    { name: 'accueil', path: '/', component: Accueil },

    { name: 'resultat', path: '/result', component: Resultat },

    { name: '404', path: '/404', component: NotFound },


    { name: 'Article', path: '/article', component: Article },

    { name: 'Sota', path: '/Sota', component: Sota },

    { name: '', path: '*', redirect: '/404' }

  ]
});
