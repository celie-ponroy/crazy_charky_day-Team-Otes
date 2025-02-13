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
        <p>Proposé par : <strong>{{ besoin.client }}</strong></p>
        <p class="mt-2"><strong>Compétence requise:</strong> {{ besoin.competence }}</p>
        <p class="mt-2"><strong>Date de création:</strong> {{ formatDate(besoin.date) }}</p>
      </div>
    </div>
    <div class="p-4 bg-gray-50 border-t border-gray-200 text-center">
      <button
        @click="handleInterest"
        class="px-4 py-2 bg-[#b2bf00] text-white rounded-md hover:bg-[#b2bf0] cursor-pointer"
      >
        Je suis intéressé
      </button>
    </div>
  </div>
</template>
