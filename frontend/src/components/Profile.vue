<script>
import { useAuthStore } from '@/stores/authStore';

export default {
  data() {
    return {
      user: null,
      isLoggedIn: false
    };
  },
  computed: {
    authStore() {
      return useAuthStore();
    }
  },
  created() {
    this.initializeProfile();
  },
  methods: {
    initializeProfile() {
      this.authStore.initializeFromStorage();
      this.user = this.authStore.user;
      this.isLoggedIn = this.authStore.isLoggedIn;
    }
  }
};
</script>

<template>
  <div class="bg-white p-8 rounded-lg shadow-sm w-full max-w-md mx-auto">
    <h2 class="text-2xl font-bold text-slate-800 mb-6 text-center">
      Profil Utilisateur
    </h2>

    <div v-if="isLoggedIn && user">
      <p class="text-lg font-semibold text-slate-700">
        ID Utilisateur: {{ user.id }}
      </p>
      <p class="text-lg font-semibold text-slate-700">
        Rôle: {{ user.role }}
      </p>
    </div>

    <div v-else class="text-center text-red-500">
      Vous devez être connecté pour voir votre profil.
    </div>
  </div>
</template>
