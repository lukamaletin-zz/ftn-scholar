import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';

Vue.config.productionTip = false

// Bootstrap:
import BootstrapVue from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
Vue.use(BootstrapVue);

// Toastr:
import Toastr from 'vue-toastr';
import 'vue-toastr/src/vue-toastr.scss';
Vue.use(Toastr);

var vm = new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

vm.$toastr.defaultProgressBar = false;
vm.$toastr.defaultPosition = 'toast-bottom-right';
