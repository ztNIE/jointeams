import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import ElementPlus from 'element-plus';
import './style/index.scss'
import * as Icons from '@element-plus/icons-vue'

// event bus
import mitt from 'mitt';
const emitter = mitt();

const app = createApp(App)
Object.keys(Icons).forEach((key) => {
    app.component(key, Icons[key]);
});
app.config.globalProperties.$emitter = emitter;
app.use(ElementPlus)
app.use(router)
app.mount('#app')
