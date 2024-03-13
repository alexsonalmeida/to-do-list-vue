import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import { createRouter, createWebHashHistory } from 'vue-router';

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import ListTask from './pages/ListTask.vue'
import ListContent from './pages/ListContent.vue'

const vuetify = createVuetify({
  components,
  directives,
})

const routes = [
  { path: '/', component: ListContent },
  { path: '/ListTask/:id', component: ListTask},
]

const router = createRouter({
  history:  createWebHashHistory(),
  routes,
})

createApp(App).use(router).use(vuetify).mount('#app')
