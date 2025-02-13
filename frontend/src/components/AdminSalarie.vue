<template>
  <div class="p-8 max-w-4xl mx-auto">
    <h1 class="text-3xl font-bold mb-6 text-center">Gestion des Salariés</h1>

    <!-- Formulaire de création d'un salarié -->
    <div class="bg-white p-6 rounded shadow mb-8">
      <h2 class="text-2xl font-bold mb-4 text-center">Créer un Salarié</h2>
      <form @submit.prevent="submitForm">
        <!-- Nom -->
        <div class="mb-4">
          <label class="block mb-1 font-semibold">Nom</label>
          <input
            type="text"
            v-model="newSalarieName"
            class="w-full border rounded px-3 py-2 focus:ring focus:ring-blue-300"
            placeholder="Nom du salarié"
          />
        </div>
        <!-- Mot de passe -->
        <div class="mb-4">
          <label class="block mb-1 font-semibold">Mot de passe</label>
          <input
            type="password"
            v-model="newSalariePassword"
            class="w-full border rounded px-3 py-2 focus:ring focus:ring-blue-300"
            placeholder="Mot de passe du salarié"
          />
        </div>
        <!-- Sélection des compétences -->
        <div class="mb-4">
          <label class="block mb-1 font-semibold">Ajouter une compétence</label>
          <div class="flex flex-col space-y-2">
            <div class="flex space-x-2">
              <select v-model="newCompetenceId" class="border rounded px-3 py-2 w-full">
                <option value="">Sélectionner une compétence</option>
                <option v-for="comp in availableCompetences" :key="comp.id" :value="comp.id">
                  {{ comp.libelle }}
                </option>
              </select>

              <input
                type="number"
                v-model.number="newCompetenceNote"
                placeholder="Note (1-10)"
                class="w-24 border rounded px-3 py-2"
                min="1"
                max="10"
              />
            </div>
            <button
              type="button"
              @click="addCompetence"
              class="w-full bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition"
            >
              Ajouter compétence
            </button>
          </div>
        </div>

        <!-- Liste des compétences ajoutées -->
        <div v-if="selectedCompetences.length" class="mb-4">
          <h3 class="font-bold mb-2">Compétences ajoutées :</h3>
          <ul class="space-y-1">
            <li
              v-for="(comp, index) in selectedCompetences"
              :key="index"
              class="flex justify-between items-center bg-gray-100 p-2 rounded"
            >
              <span>
                {{ getCompetenceLabel(comp.id) }} - Note: {{ comp.note }}
              </span>
              <button
                type="button"
                @click="removeCompetence(index)"
                class="text-red-500 font-bold"
              >
                X
              </button>
            </li>
          </ul>
        </div>

        <button
          type="submit"
          :disabled="!isFormValid || creating"
          class="w-full bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600 transition disabled:bg-green-300"
        >
          <span v-if="creating">Création...</span>
          <span v-else>Créer le salarié</span>
        </button>
      </form>
    </div>

    <!-- Liste des salariés -->
    <div class="bg-white p-6 rounded shadow">
      <h2 class="text-2xl font-bold mb-4 text-center">Liste des Salariés</h2>
      <div v-if="loadingSalaries" class="text-center text-gray-600">Chargement...</div>
      <div v-else>
        <ul class="space-y-4">
          <li
            v-for="salarie in salaries"
            :key="salarie.id"
            class="p-4 bg-gray-100 rounded"
          >
            <div class="font-bold text-lg">{{ salarie.nom }}</div>
            <div v-if="salarie.competences.length">
              <p class="text-sm text-gray-700 mt-1">Compétences:</p>
              <ul class="list-disc pl-4">
                <li
                  v-for="comp in salarie.competences"
                  :key="comp.id"
                  class="text-gray-700 text-sm"
                >
                  {{ comp.label }} (Note: {{ comp.interest }})
                </li>
              </ul>
            </div>
          </li>
        </ul>
        <div v-if="!salaries.length" class="text-gray-500 text-center">
          Aucun salarié trouvé.
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { adminService } from '@/services/adminService';
import { besoinService } from '@/services/besoinService';

export default {
  name: 'AdminSalaries',
  data() {
    return {
      newSalarieName: '',
      newSalariePassword: '', // Nouveau champ pour le mot de passe
      newCompetenceId: '',
      newCompetenceNote: null,
      selectedCompetences: [],
      availableCompetences: [],
      salaries: [],
      creating: false,
      loadingSalaries: false,
    };
  },
  computed: {
    isFormValid() {
      return this.newSalarieName && this.newSalariePassword && this.selectedCompetences.length > 0;
    },
  },
  methods: {
    addCompetence() {
      if (!this.newCompetenceId || !this.newCompetenceNote) return;
      const exists = this.selectedCompetences.find(
        (comp) => comp.id === parseInt(this.newCompetenceId, 10)
      );
      if (exists) {
        exists.note = parseInt(this.newCompetenceNote, 10);
      } else {
        this.selectedCompetences.push({
          id: parseInt(this.newCompetenceId, 10),
          note: parseInt(this.newCompetenceNote, 10),
        });
      }
      this.newCompetenceId = '';
      this.newCompetenceNote = null;
    },
    removeCompetence(index) {
      this.selectedCompetences.splice(index, 1);
    },
    getCompetenceLabel(id) {
      const comp = this.availableCompetences.find((c) => c.id === id);
      return comp ? comp.label : id;
    },
    async submitForm() {
      if (!this.isFormValid) return;
      this.creating = true;
      try {
        const payload = {
          nom: this.newSalarieName,
          password: this.newSalariePassword, // Envoi du mot de passe dans le payload
          competences: this.selectedCompetences,
        };
        console.log('Création du salarié:', payload);
        await adminService.createSalarie(payload);
        // Réinitialisation des champs du formulaire après succès
        this.newSalarieName = '';
        this.newSalariePassword = '';
        this.selectedCompetences = [];
        this.fetchSalaries();
      } catch (error) {
        console.error('Erreur lors de la création du salarié:', error);
      } finally {
        this.creating = false;
      }
    },
    async fetchSalaries() {
      this.loadingSalaries = true;
      try {
        this.salaries = await adminService.getSalaries();
      } catch (error) {
        console.error('Erreur lors de la récupération des salariés:', error);
      } finally {
        this.loadingSalaries = false;
      }
    },
  },
  created() {
    besoinService.getCompetences().then((competences) => {
      this.availableCompetences = competences;
      console.log(this.availableCompetences);
    });
    this.fetchSalaries();
  },
};
</script>

<style scoped>
.bg-white {
  max-width: 100%;
}

button {
  transition: all 0.2s ease-in-out;
}
</style>
