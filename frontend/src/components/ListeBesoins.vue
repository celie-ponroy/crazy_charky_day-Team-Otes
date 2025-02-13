<script>
  import BesoinCard from './BesoinCard.vue';
  
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
            description: 'jai besoin de cela',
            competence: 'BR',
            date: '2023-10-01'
          },
          {
            id: 2,
            client: 'Matias',
            description: 'Jai besoin de ceci',
            competence: 'JD',
            date: '2023-10-01'
          }
        ]
      };
    }
  };
  </script>


<template>
    <div>
      <BesoinCard v-for="besoin in besoins" :key="besoin.id" :besoin="besoin" />
    </div>
</template>
  
  
  