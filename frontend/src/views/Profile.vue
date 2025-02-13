<template>
  <div class="p-8 max-w-4xl mx-auto">
    <h1 class="text-3xl font-bold mb-6 text-center">Profil du Salarié</h1>
    <div v-if="!isSalarie" class="text-center text-gray-600">
      Ce profil n'est disponible que pour les salariés.
    </div>
    <div v-else>
      <h2 class="text-2xl font-bold mb-4">Mes Services</h2>
      <div v-if="loading" class="text-center text-gray-600">Chargement...</div>
      <div v-else>
        <ul class="space-y-4">
          <li
            v-for="service in servicesDetails"
            :key="service.id"
            class="p-4 bg-gray-100 rounded"
          >
            <div class="font-bold text-lg">Client : {{ service.nomClient }}</div>
            <div class="text-gray-700">
              Besoin : {{ service.libelleBesoin }}
            </div>
            <div class="text-gray-600 text-sm">
              Date : {{ formatDate(service.timestamp) }}
            </div>
          </li>
        </ul>
        <div v-if="servicesDetails.length === 0" class="text-gray-500 text-center mt-4">
          Aucun service trouvé.
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from '@/stores/authStore';
import { serviceService } from '@/services/serviceService';
import { besoinService } from '@/services/besoinService';

export default {
  name: 'UserProfile',
  data() {
    return {
      servicesDetails: [],
      loading: false,
    };
  },
  computed: {
    authStore() {
      return useAuthStore();
    },
    isSalarie() {
      return this.authStore.user && this.authStore.user.role === 1;
    },
    userId() {
      return this.authStore.user ? this.authStore.user.id : null;
    }
  },
  methods: {
    async fetchServices() {
      // if (!this.userId) return;
      this.loading = true;
      try {
        const services = await serviceService.getServicesBySalarie(this.userId);

        const detailsPromises = services.map(async (service) => {
          const besoin = await besoinService.getBesoin(service.idbesoin);
          return {
            id: service.id,
            nomClient: besoin.nomClient,
            libelleBesoin: besoin.libelleBesoin,
            timestamp: besoin.timestamp,
          };
        });
        this.servicesDetails = await Promise.all(detailsPromises);
      } catch (error) {
        console.error("Erreur lors de la récupération des services :", error);
      } finally {
        this.loading = false;
      }
    },
    formatDate(timestamp) {
      const date = new Date(timestamp * 1000);
      return date.toLocaleDateString(undefined, {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      });
    }
  },
  created() {
    if (this.isSalarie) {
      this.fetchServices();
    }
  }
};
</script>

<style scoped>
</style>
