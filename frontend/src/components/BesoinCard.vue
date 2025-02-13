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
      alert(`Vous avez exprimé votre intérêt pour ce besoin`);
    }
  }
};
</script>

<template>
  <div class=" bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl">
    <div class="p-6">
      <div class="text-lg font-semibold text-gray-800">
        {{ besoin.libelle }}
      </div>
      <div class="mt-4 text-gray-600">  
        <p>Proposé par : <strong>{{ besoin.nomClient }}</strong></p>
        <p class="mt-2"><strong>Compétence requise:</strong> {{ besoin.libelleBesoin }}</p>
        <p class="mt-2"><strong>Date de création:</strong> {{ formatDate(besoin.timestamp * 1000  ) }}</p>
      </div>
    </div>
  </div>
</template>
