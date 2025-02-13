<template>
  <div class="bg-white p-8 rounded-lg shadow-sm max-w-md mx-auto">
    <h2 class="text-2xl font-bold text-slate-800 mb-6 text-center">
      {{ isLogin ? 'Connexion' : 'Inscription' }}
    </h2>

    <form @submit.prevent="handleSubmit" class="space-y-4">
      <div class="space-y-1">
        <label class="block text-sm font-medium text-slate-800">
          Votre nom
        </label>
        <input
          type="text"
          v-model="nom"
          class="w-full px-4 py-2 rounded-md border-2 border-gray-600 text-slate-900 outline-none transition-colors"
        />
      </div>

      <div class="space-y-1">
        <label class="block text-sm font-medium text-slate-800">
          Mot de passe
        </label>
        <input
          type="password"
          v-model="password"
          class="w-full px-4 py-2 rounded-md border-2 border-gray-600 text-slate-900 outline-none transition-colors"
        />
      </div>

      <button
        :disabled="!isFormValid || isLoading"
        type="submit"
        :class="[
          'w-full px-4 py-2 rounded-md transition-colors text-white',
          isFormValid ? 'bg-blue-500 hover:bg-blue-600' : 'bg-blue-300 cursor-not-allowed'
        ]"
      >
        <span v-if="isLoading" class="inline-block animate-spin mr-2">⌛</span>
        <span v-else>{{ isLogin ? 'Se connecter' : "S'inscrire" }}</span>
      </button>
    </form>

    <div class="mt-6 text-center">
      <button
        @click="toggleForm"
        class="text-blue-600 hover:text-blue-800 text-sm font-medium px-8 py-2"
      >
        {{ isLogin ? "Pas encore de compte ? S'inscrire" : "Déjà un compte ? Se connecter" }}
      </button>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from '@/stores/authStore';

export default {
  name: 'AuthForm',
  data() {
    return {
      nom: '',
      password: '',
      isLoading: false,
      authStore: useAuthStore()
    };
  },
  computed: {
    isLogin() {
      return this.$route.path === '/login';
    },
    isFormValid() {
      return this.nom && this.password;
    }
  },
  methods: {
    async handleSubmit() {
      this.isLoading = true;
      try {
        if (this.isLogin) {
          await this.authStore.login({ nom: this.nom, password: this.password });
        } else {
          await this.authStore.register({ nom: this.nom, password: this.password });
        }
        this.$router.push('/');
      } catch (error) {
        console.error("An error occurred:", error);
      } finally {
        this.isLoading = false;
      }
    },
    toggleForm() {
      if (this.isLogin) {
        this.$router.push('/register');
      } else {
        this.$router.push('/login');
      }
    }
  }
};
</script>

<style scoped>
</style>
