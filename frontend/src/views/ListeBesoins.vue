<script>
import BesoinCard from '@/components/BesoinCard.vue';
import { besoinService } from '@/services/besoinService';

export default {
  components: {
    BesoinCard
  },
  data() {
    return {
      besoins: []
    };
  },
  mounted() {
    besoinService.getBesoins().then((besoins) => {
      this.besoins = besoins;
      console.log(besoins);
    });
  }
};
</script>

<template>
  <div>
    <h2 class="text-2xl text-gray-800 font-bold text-center relative mb-6">
      La liste des besoins
      <span class="block w-40 h-1 bg-gray-500 mt-2 mx-auto"></span>
    </h2>
  </div>
  <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 p-6">
    <BesoinCard v-for="besoin in besoins" :key="besoin.id" :besoin="besoin" />
  </div>
</template>
