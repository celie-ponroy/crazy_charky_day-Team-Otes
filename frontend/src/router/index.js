import { createRouter, createWebHistory } from 'vue-router'
import ListeBesoins from '@/views/ListeBesoins.vue'
import BesoinForm from '@/views/BesoinForm.vue'
import { useAuthStore } from '@/stores/authStore'
import Profile from '@/views/Profile.vue'
import AuthForm from '@/components/AuthForm.vue'
import AdminPage from '@/components/AdminSalarie.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path : '/profil',
      name : 'profil',
      component : Profile,
    },
    {
      path: '/',
      name: 'home',
      component: ListeBesoins
    },
    {
      path: '/besoins/add',
      name: 'addBesoin',
      component: BesoinForm
    },
    {
      path: '/login',
      name: 'Login',
      component: AuthForm
    },
    {
      path: '/register',
      name: 'Register',
      component: AuthForm
    },
    {
      path: '/admin',
      name: 'admin',
      component: AdminPage,
      meta: { requiresAdmin: true }
    }
  ]
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  if (to.meta.requiresAuth && !authStore.isLoggedIn) {
    next('/Connexion')
  } 

  if (to.meta.requiresAdmin) {
    if (!authStore.isLoggedIn || (authStore.user && authStore.user.role !== 2)) {
      return next('/');
    }
  }

  next()
})

export default router
