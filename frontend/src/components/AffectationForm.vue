<template>
    <div class="bg-white p-6 rounded shadow max-w-md mx-auto">
      <h2 class="text-2xl font-bold mb-4 text-center">Lancer l'affectation</h2>
      <form @submit.prevent="submitAffectation">
        <div class="mb-4">
          <label class="block mb-1 font-semibold">Date de réalisation</label>
          <input
            type="date"
            v-model="affectationDate"
            class="w-full border rounded px-3 py-2 focus:ring focus:ring-blue-300"
            required
          />
        </div>
        <button
          type="submit"
          :disabled="!affectationDate || loading"
          class="w-full bg-indigo-500 text-white px-4 py-2 rounded hover:bg-indigo-600 transition"
        >
          <span v-if="loading">En cours...</span>
          <span v-else>Lancer l'affectation</span>
        </button>
      </form>
    </div>
  </template>
  
  <script>
  import { adminService } from '@/services/adminService';
  
  export default {
    name: 'AffectationForm',
    data() {
      return {
        affectationDate: '',
        loading: false,
      };
    },
    methods: {
      async submitAffectation() {
        if (!this.affectationDate) return;
        this.loading = true;
        try {
          const payload = { date: this.affectationDate };
          await adminService.launchAffectation(payload);
          alert("Affectation lancée avec succès !");
          this.affectationDate = '';
        } catch (error) {
          console.error("Erreur lors du lancement de l'affectation :", error);
          alert("Erreur lors du lancement de l'affectation.");
        } finally {
          this.loading = false;
        }
      }
    }
  };
  </script>
  
  <style scoped>
  </style>
  