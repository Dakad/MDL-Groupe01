import Vue from 'vue';
import App from './App.vue';
import router from './router';

import VueResource from 'vue-resource';
import VueMaterial from 'vue-material';
import VueCarousel from 'vue-carousel';
import BootstrapVue from 'bootstrap-vue';

Vue.use(VueResource);
Vue.use(BootstrapVue);
Vue.use(VueMaterial);
Vue.use(VueCarousel);

Vue.config.productionTip = false;

new Vue({
  el: '#app',
  router,
  render: h => h(App)
});
