import Vue from 'vue';
import Router from 'vue-router';

import NotFound from './pages/NotFound.vue';
import Accueil from './pages/Accueil.vue';
import About from './pages/About.vue';
import Resultat from './pages/Resultat.vue';
import Article from './pages/Article';
import Sota from './pages/Sota';
import Profil from './pages/Profil';
import SotAHelper from "./pages/SotAHelper";

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    { name: 'accueil', path: '/', component: Accueil },

    { name: 'about', path: '/about', component: About },

    { name: 'resultat', path: '/result', component: Resultat },

    { name: '404', path: '/404', component: NotFound },

    { name: 'articleDetails', path: '/article/:reference', component: Article },

    { name: 'sota', path: '/sota', component: Sota },

    { name: 'profil', path: '/profil', component: Profil },

    { name: 'sotaHelper', path: '/sotahelper', component: SotAHelper},

    { name: '', path: '*', redirect: '/404' }
  ]
});
