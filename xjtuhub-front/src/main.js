import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from 'axios'
import VueAxios from 'vue-axios'
import 'highlight.js/styles/github.css'
import store from './stores/index.js'
import 'github-markdown-css/github-markdown.css';
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { VueShowdown } from 'vue-showdown'
import 'showdown-highlight'

const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.component('VueShowdown', VueShowdown)
app.use(ElementPlus)
app.use(createPinia())
app.use(router)
app.use(VueAxios, axios)
app.use(store)
app.mount('#app')
