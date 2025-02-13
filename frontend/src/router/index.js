import { createRouter, createWebHistory } from 'vue-router'
import Besoins from '@/components/ListeBesoins.vue'
import AddBesoin from '@/components/BesoinForm.vue'
import { useAuthStore } from '@/stores/authStore'
import LoginView from '@/views/LoginView.vue'
import RegisterView from '@/views/RegisterView.vue'
import Profile from '@/components/Profile.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/register',
      name: 'register',
      component : RegisterView
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path : '/profil',
      name : 'profil',
      component : Profile,
    },
    {
      path: '/',
      name: 'home',
      component: Besoins
    },
    {
      path: '/besoins/add',
      name: 'addBesoin',
      component: AddBesoin
    }
  ]
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  if (to.meta.requiresAuth && !authStore.isLoggedIn) {
    next('/login')
  } else {
    next()
  }
})

export default router
