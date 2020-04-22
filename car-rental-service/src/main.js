import Vue from 'vue'
import App from './App.vue'
import router from "./routes";
import {store} from "./store"

import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
Vue.use(BootstrapVue)

import uk from 'vee-validate/dist/locale/uk';
import VeeValidate, {Validator, ValidationObserver, ValidationProvider} from 'vee-validate';
Vue.use(VeeValidate, { fieldsBagName: 'veeFields', errorBagName: 'veeErrors'});
Vue.component("ValidationObserver", ValidationObserver);
Vue.component("ValidationProvider", ValidationProvider);
Validator.localize('uk', uk);

Vue.config.productionTip = true

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
})