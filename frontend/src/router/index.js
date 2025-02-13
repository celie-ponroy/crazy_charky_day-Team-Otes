import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/components/Home.vue'
import Besoins from '@/components/ListeBesoins.vue'
import AddBesoin from '@/components/BesoinForm.vue'

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
    },
    {
      path: '/besoins/add',
      name: 'addBesoin',
      component: AddBesoin
    }
  ]
})

export default router
