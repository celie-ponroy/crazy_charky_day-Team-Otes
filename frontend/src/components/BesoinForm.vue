<script>
import { gameService } from '@/services/besoinService';
export default {
    data() {
        return {
            nomClient: '',
            description: '',
            selectedComp: '',
            competences: [
                {
                    id: 1,
                    libelle: 'BR',
                    description: 'Compétences de bricolage'
                },
                {
                    id: 2,
                    libelle: 'JD',
                    description: 'Compétences de jardinage'
                },
            ],
        };
    },
    mounted() {
        // gameService.getCompetences().then((competences) => {
        //     this.competences = competences;
        // });
    },
    methods: {
        soumettreFormulaire() {
            console.log({
                nom: this.nomClient,
                description: this.description,
                competence: this.selectedComp,
            });
            // gameService.addBesoin({
            //     nom: this.nomClient,
            //     description: this.description,
            //     competence: this.selectedComp,
            // }).then(() => {
            //     this.$router.push('/besoins');
            // });
        }
    },
    computed: {
        isFormValid() {
            return this.nomClient && this.description && this.selectedComp;
        }
    }
}
</script>

<template>
    <div class="bg-white p-8 rounded-lg shadow-sm max-w-md mx-auto">
        <h2 class="text-2xl font-bold text-slate-800 mb-6 text-center">
            J'ai besoin
        </h2>

        <form @submit.prevent="soumettreFormulaire" class="space-y-4">
            <div class="space-y-1">
                <label class="block text-sm font-medium text-slate-800">
                    Votre nom
                </label>
                <input type="text" v-model="nomClient"
                    class="w-full px-4 py-2 rounded-md border-2 border-gray-600 text-slate-900 outline-none transition-colors" />
            </div>

            <div class="space-y-1">
                <label class="block text-sm font-medium text-slate-800">
                    Votre besoin
                </label>
                <input type="text" v-model="description"
                    class="w-full px-4 py-2 rounded-md border-2 border-gray-600 text-slate-900 outline-none transition-colors" />
            </div>

            <div class="space-y-1">
                <label class="block text-sm font-medium text-slate-800">
                    Compétence demandée
                </label>
                <select v-model="selectedComp"
                    class="w-full px-4 py-2 rounded-md border-2 border-gray-600 text-slate-900 outline-none transition-colors">
                    <option v-for="competence in competences" :key="competence.id" :value="competence.libelle">
                        {{ competence.libelle }}
                    </option>
                </select>
            </div>

            <button :disabled="!isFormValid" type="submit"
                :class="['w-full px-4 py-2 rounded-md transition-colors', isFormValid ? 'bg-blue-500 hover:bg-blue-600' : 'bg-blue-300 cursor-not-allowed']"
                class="text-white">
                Soumettre
            </button>
        </form>
    </div>
</template>
