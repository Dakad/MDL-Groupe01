import Vue from 'vue';
import Router from 'vue-router';

import { isLogged } from './services/api-user';

import NotFound from './pages/NotFound.vue';
import Accueil from './pages/Accueil.vue';
import About from './pages/About.vue';
import Resultat from './pages/Resultat.vue';
import Article from './pages/Article';
import Sota from './pages/Sota';
import Profil from './pages/Profil';

Vue.use(Router);

const routes = [
  { name: 'accueil', path: '/', component: Accueil },

  { name: 'about', path: '/about', component: About },

  { name: 'resultat', path: '/result', component: Resultat },

  { name: '404', path: '/404', component: NotFound },

  { name: 'articleDetails', path: '/article/:reference', component: Article },

  { name: 'sota', path: '/sota', component: Sota },

  { name: 'myProfile', path: '/profile', component: Profil, meta: { requiresAuth: true } },

  {
    name: 'userProfile',
    path: '/profile/:username',
    component: Profil,
    meta: { requiresAuth: true },
    props: true
  },

  { name: '', path: '*', redirect: '/404' }
];

const appRouter = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

appRouter.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // this route requires auth, check if logged in
    // if not, redirect to accueil page.
    debugger;
    if (!isLogged()) {
      next({
        name: 'accueil',
        query: { redirect: to.fullPath, action: 'login' }
      });
    } else {
      next();
    }
  } else {
    next(); // make sure to always call next()!
  }
});

export default appRouter;
