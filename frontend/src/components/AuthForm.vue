<script>
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/authStore';

export default {
  props: {
    isLogin: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      formData: {
        nom: '',
        password: ''
      },
      isLoading: false
    };
  },
  methods: {
    async handleSubmit() {
      try {
        this.isLoading = true;

        if (this.isLogin) {
          await this.authStore.login({
            nom: this.formData.nom,
            password: this.formData.password
          });
        } else {
          await this.authStore.register({
            nom: this.formData.nom,
            password: this.formData.password
          });
        }

        this.$router.push('/');
      } catch (err) {
        console.error("An error occurred:", err);
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
  },
  setup() {
    const router = useRouter();
    const authStore = useAuthStore();

    return {
      router,
      authStore
    };
  }
};
</script>

<template>
  <div class="bg-white p-8 rounded-lg shadow-sm w-full max-w-md">
    <h2 class="text-2xl font-bold text-slate-800 mb-6 text-center">
      {{ isLogin ? 'Connexion' : 'Inscription' }}
    </h2>

    <form @submit.prevent="handleSubmit" class="space-y-4">
      <div class="space-y-1">
        <label class="block text-sm font-medium text-slate-700">
          Nom
        </label>
        <input
          type="nom"
          v-model="formData.nom"
          class="w-full px-4 py-2 rounded-md border-2 text-slate-900 outline-none transition-colors"
        />
      </div>

      <div class="space-y-1">
        <label class="block text-sm font-medium text-slate-700">
          Mot de passe
        </label>
        <input
          type="password"
          v-model="formData.password"
          class="w-full px-4 py-2 rounded-md border-2 text-slate-900 outline-none transition-colors"
        />
      </div>

      <button
        type="submit"
        :disabled="isLoading"
        class="w-full bg-indigo-600 hover:bg-indigo-700 disabled:bg-indigo-400 px-12 py-3 text-white rounded-md"
      >
        <template v-if="isLoading">
          <span class="inline-block animate-spin mr-2">⌛</span>
          Chargement...
        </template>
        <template v-else>
          {{ isLogin ? 'Se connecter' : "S'inscrire" }}
        </template>
      </button>
    </form>

    <div class="mt-6 text-center">
      <button
        @click="toggleForm"
        class="text-indigo-600 hover:text-indigo-800 text-sm font-medium px-8 py-2"
      >
        {{ isLogin ? "Pas encore de compte ? S'inscrire" : 'Déjà un compte ? Se connecter' }}
      </button>
    </div>
  </div>
</template>
