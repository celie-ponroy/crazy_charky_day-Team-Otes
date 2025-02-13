<template>
    <div class="p-8 max-w-4xl mx-auto">
      <h1 class="text-3xl font-bold mb-6 text-center">Page d'administration</h1>
  
      <div class="flex justify-center space-x-4 mb-6">
        <button 
          @click="activeTab = 'salaries'"
          :class="[
            'px-4 py-2 rounded transition',
            activeTab === 'salaries' ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-700'
          ]"
        >
          Gestion des Salariés
        </button>
        <button 
          @click="activeTab = 'competences'"
          :class="[
            'px-4 py-2 rounded transition',
            activeTab === 'competences' ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-700'
          ]"
        >
          Gestion des Compétences
        </button>
        <button 
          @click="activeTab = 'affectation'"
          :class="[
            'px-4 py-2 rounded transition',
            activeTab === 'affectation' ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-700'
          ]"
        >
          Lancer l'affectation
        </button>
      </div>    
      <div v-if="activeTab === 'salaries'">
        <AdminSalarie />
      </div>
      <div v-else-if="activeTab === 'competences'">
        <AdminCompetences />
      </div>
      <div v-else-if="activeTab === 'affectation'">
        <AffectationForm />
      </div>
    </div>
  </template>
  
  <script>
  import AdminSalarie from '@/components/AdminSalarie.vue';
  import AdminCompetences from '@/components/AdminCompetences.vue';
  import AffectationForm from '@/components/AffectationForm.vue';
  
  export default {
    name: 'AdminPage',
    components: {
      AdminSalarie,
      AdminCompetences,
      AffectationForm,
    },
    data() {
      return {
        activeTab: 'salaries',
      };
    },
  };
  </script>
  
  <style scoped>
  button {
    transition: all 0.2s ease-in-out;
  }
  </style>
  