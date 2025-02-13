<script>
  export default {
    name: 'BesoinCard',
    props: {
      besoin: {
        type: Object,
        required: true
      }
    },
    methods: {
      formatDate(dateString) {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(dateString).toLocaleDateString(undefined, options);
      },
      handleInterest() {
        alert(`Vous avez exprimé votre intérêt pour le besoin: ${this.besoin.libelle}`);
      }
    }
  };
</script>

<template>
    <div class="border border-gray-200 rounded-lg shadow-sm p-4 space-y-4">
      <div class="need-card-header">
        <h3>{{ besoin.libelle }}</h3>
        <p>{{ besoin.client }}</p>
      </div>
      <div class="need-card-body">
        <p>{{ besoin.description }}</p>
        <p><strong>Compétence requise:</strong> {{ besoin.competence }}</p>
        <p><strong>Date de création:</strong> {{ formatDate(besoin.date) }}</p>
      </div>
      <div class="need-card-footer">
        <button @click="handleInterest">Je suis intéressé</button>
      </div>
    </div>
  </template>
  