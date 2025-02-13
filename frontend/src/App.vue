<template>
  <div class="min-h-screen bg-gray-100">
    <header class="bg-[#375a69] shadow-md text-white px-6 py-6 mb-8 font-semibold">
      <nav class="mx-auto flex space-x-4 items-center justify-between">
        <div>
          <RouterLink to="/" class="text-2xl hover:text-[#b2bf00]">PerfectMatch</RouterLink>
        </div>
        <div class="space-x-4">
          <RouterLink to="/besoins/add" class="hover:text-[#b2bf00]">Ajouter un besoin</RouterLink>
          <RouterLink
            v-if="!authStore.isLoggedIn"
            to="/login"
            class="hover:text-[#b2bf00]"
          >
            Se connecter
          </RouterLink>
          <a
            v-else
            @click="handleLogout"
            class="cursor-pointer hover:text-[#b2bf00]"
          >
            Se déconnecter
          </a>
          <RouterLink to="/profil" class="hover:text-[#b2bf00]">Profil</RouterLink>
          <RouterLink
            v-if="authStore.user && authStore.user.role === 2"
            to="/admin"
            class="hover:text-[#b2bf00]"
          >
            Admin
          </RouterLink>
        </div>
      </nav>
    </header>
    <main class="mx-auto px-4 py-8">
      <RouterView />
    </main>
  </div>
</template>

<script>
import { useAuthStore } from '@/stores/authStore';
import { useRouter } from 'vue-router';

export default {
  name: 'LayoutComponent',
  data() {
    return {
      authStore: useAuthStore(),
      router: useRouter()
    };
  },
  methods: {
    handleLogout() {
      this.authStore.logout(this.router);
    }
  }
};
</script>

<style scoped>
</style>
