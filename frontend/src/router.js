import Vue from 'vue';
import Router from 'vue-router';

import NotFound from './pages/NotFound.vue';
import Accueil from './pages/Accueil.vue';
import About from './pages/About.vue';
import Resultat from './pages/Resultat.vue';
import Article from './pages/Article';
import SotaDetails from './pages/SotaDetails';
import Profil from './pages/Profil';
import SotaHelper from './pages/SotaHelper';

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

    { name: 'sotaDetails', path: '/sota', component: SotaDetails },

    { name: 'profil', path: '/profil', component: Profil },

    { name: 'sotaHelper', path: '/sotahelper', component: SotaHelper },

    { name: '', path: '*', redirect: '/404' }
  ]
});
