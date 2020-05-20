import Vue from 'vue'
import App from './App.vue'
import router from './routes'
import { store } from './store'

import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import uk from 'vee-validate/dist/locale/uk'
import VeeValidate, {
    ValidationObserver,
    ValidationProvider,
    Validator,
} from 'vee-validate'
import vWow from 'v-wow'
import VueLodash from 'vue-lodash'
import lodash from 'lodash'

Vue.use(BootstrapVue);

Vue.use(VeeValidate, { fieldsBagName: "veeFields", errorBagName: "veeErrors" });
Vue.component("ValidationObserver", ValidationObserver);
Vue.component("ValidationProvider", ValidationProvider);
Validator.localize("uk", uk);

Vue.use(vWow);

Vue.use(VueLodash, { name: "iodash", lodash: lodash });

Vue.config.productionTip = true;

new Vue({
  el: "#app",
  router,
  store,
  render: (h) => h(App),
});
