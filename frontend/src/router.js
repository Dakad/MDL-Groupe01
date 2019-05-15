import Vue from 'vue';
import Router from 'vue-router';

import { isLogged } from './services/api-user';

import NotFound from './pages/NotFound.vue';
import Accueil from './pages/Accueil.vue';
import About from './pages/About.vue';
import Resultat from './pages/Resultat.vue';
import Article from './pages/Article';
import SotaDetails from './pages/SotaDetails';
import Profil from './pages/Profil';
import SotaHelper from './pages/SotaHelper';
import ProfileUpdate from './pages/ProfileUpdate';
import Subscriptions from './pages/Subscriptions';

Vue.use(Router);

const routes = [
  {
    name: 'accueil',
    path: '/',
    component: Accueil,
    meta: {
      title: 'Froggosaur - Home'
    }
  },

  {
    name: 'about',
    path: '/about',
    component: About,
    meta: {
      title: 'Froggosaur - About'
    }
  },

  {
    name: 'resultat',
    path: '/result',
    component: Resultat,
    meta: {
      title: 'Froggosaur - Result'
    }
  },

  {
    name: 'sotaDetails',
    path: '/sota/:reference',
    component: SotaDetails,
    props: true,
    meta: {
      title: 'Froggosaur - SotaDetails'
    }
  },

  {
    name: 'articleDetails',
    path: '/article/:reference',
    component: Article,
    props: true,
    meta: {
      title: 'Froggosaur - Article details'
    }
  },

  {
    name: 'myProfile',
    path: '/profile',
    component: Profil,
    meta: { requiresAuth: true, title: 'Froggosaur - My profile' }
  },

  {
    name: 'updateProfile',
    path: '/update/profile',
    component: ProfileUpdate,
    meta: { requiresAuth: true, title: 'Froggosaur - Update my profile' }
  },

  {
    name: 'userProfile',
    path: '/profile/:username',
    component: Profil,
    meta: { requiresAuth: true, title: 'Froggosaur - User profile' },
    props: true
  },

  {
    name: 'sotaHelper',
    path: '/sotahelper',
    component: SotaHelper,
    meta: { requiresAuth: true, title: 'Froggosaur - Sota-helper' }
  },

  {
    name: 'subscriptions',
    path: '/subscriptions',
    component: Subscriptions,
    meta: { requiresAuth: true, title: 'Froggosaur - Subscriptions' }
  },

  {
    name: '404',
    path: '/404',
    component: NotFound,
    meta: {
      title: 'Froggosaur - Page not found'
    }
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
    if (!isLogged()) {
      next({
        name: 'accueil',
        query: { action: 'login', redirect: to.fullPath }
      });
    } else {
      next();
    }
  } else {
    next(); // make sure to always call next()!
  }
});

export default appRouter;
