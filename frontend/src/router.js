import Vue from 'vue';
import Router from 'vue-router';
import accueilapp from './components/AccueilVue.vue';
import register from './components/Register.vue';
import login from './components/Login.vue';

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      component: accueilapp,
    },
    {
      path: '/register',
      component: register
    },
    {path: '/login',
    component: login

    },
    {
      path: '*',
      redirect: '/',
    },
  ],
});
