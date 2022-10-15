import {createApp} from 'vue'
import App from './App.vue'
import router from './router'

import ElementPlus from 'element-plus';
import './style/index.scss'
import * as Icons from '@element-plus/icons-vue'
import {VueReCaptcha} from "vue-recaptcha-v3";

// event bus
import mitt from 'mitt';
import store from "@/store";

const emitter = mitt();

const app = createApp(App)
Object.keys(Icons).forEach((key) => {
    app.component(key, Icons[key]);
});

app.config.globalProperties.$emitter = emitter;
app.config.globalProperties.$router = router;

app.use(ElementPlus)
app.use(router)
app.use(store)
app.use(VueReCaptcha, {
    siteKey: '6LfAYoEiAAAAAOGxLAzWHcCUbDQn7MItNk4cm2Jn'
})

app.mount('#app')
