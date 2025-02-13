import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/components/Home.vue'
import Besoins from '@/components/ListeBesoins.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/besoins',
      name: 'besoins',
      component: Besoins
    }
  ]
})

export default router
