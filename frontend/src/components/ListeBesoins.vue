<script>
import BesoinCard from './BesoinCard.vue';
import { gameService } from '@/services/besoinService';

export default {
  components: {
    BesoinCard
  },
  data() {
    return {
      besoins: [
        {
          id: 1,
          client: 'Victor',
          libelle: 'Jai besoin de cela',
          competence: 'BR',
          date: '2023-10-01'
        },
        {
          id: 2,
          client: 'Matias',
          libelle: 'Jai besoin de ceci',
          competence: 'JD',
          date: '2023-10-01'
        },
        {
          id: 3,
          client: 'Victor',
          libelle: 'Jai besoin decela',
          competence: 'BR',
          date: '2023-10-01'
        },
        {
          id: 4,
          client: 'Matias',
          libelle: 'Jai besoin de ceci',
          competence: 'JD',
          date: '2023-10-01'
        }
      ]
    };
  },
  mounted() {
    // gameService.getAll().then(besoins => {
    //   this.besoins = besoins;
    // });
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
