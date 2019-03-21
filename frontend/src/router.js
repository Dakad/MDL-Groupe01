import Vue from 'vue';
import Router from 'vue-router';
import accueilapp from './components/AccueilVue.vue';
import research from './components/Research.vue';

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
      path: '*',
      redirect: '/',
    },
    {
      path : '/result',
      component: research,
    }
  ],
});
