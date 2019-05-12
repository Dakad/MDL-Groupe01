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

Vue.filter('capitalize', function(value) {
  if (!value) return '';
  value = value.toString();
  return value.charAt(0).toUpperCase() + value.slice(1);
});

Vue.filter('pluralize', (nb, label) => `${nb} ${label}${nb > 1 ? 's' : ''}`);

Vue.filter('sizeHuman', function(size) {
  // Source : http://programanddesign.com/js/human-readable-file-size-in-javascript/
  if (!size || size <= 0) return 'O kB';
  const units = ['B', 'kB', 'MB'];
  let i = 0;
  while (size >= 1024) {
    size /= 1024;
    ++i;
  }
  return size.toFixed(2) + ' ' + units[i];
});

Vue.config.productionTip = false;

new Vue({
  el: '#app',
  router,
  render: h => h(App)
});
