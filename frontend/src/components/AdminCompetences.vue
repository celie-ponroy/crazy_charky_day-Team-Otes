<template>
    <div class="p-8 max-w-4xl mx-auto">
      <h1 class="text-3xl font-bold mb-6 text-center">Gestion des Compétences</h1>
      
      <!-- Formulaire d'ajout de compétence -->
      <div class="bg-white p-6 rounded shadow mb-8">
        <h2 class="text-2xl font-bold mb-4 text-center">Ajouter une Compétence</h2>
        <form @submit.prevent="submitNewCompetence">
          <div class="mb-4">
            <label class="block mb-1 font-semibold">Libellé</label>
            <input
              type="text"
              v-model="newCompetence.libelle"
              class="w-full border rounded px-3 py-2 focus:ring focus:ring-blue-300"
              placeholder="Libellé de la compétence"
              required
            />
          </div>
          <div class="mb-4">
            <label class="block mb-1 font-semibold">Description</label>
            <textarea
              v-model="newCompetence.description"
              class="w-full border rounded px-3 py-2 focus:ring focus:ring-blue-300"
              placeholder="Description de la compétence"
              required
            ></textarea>
          </div>
          <button
            type="submit"
            class="w-full bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600 transition"
          >
            Ajouter
          </button>
        </form>
      </div>
      
      <!-- Liste des compétences -->
      <div class="bg-white p-6 rounded shadow">
        <h2 class="text-2xl font-bold mb-4 text-center">Liste des Compétences</h2>
        <div v-if="loading" class="text-center text-gray-600 mb-4">Chargement...</div>
        <div v-else>
          <ul class="space-y-4">
            <li
              v-for="competence in competences"
              :key="competence.id"
              class="p-4 bg-gray-100 rounded flex justify-between items-center"
            >
              <div class="w-full">
                <div v-if="editingCompetenceId === competence.id">
                  <input
                    type="text"
                    v-model="editedCompetence.libelle"
                    class="w-full border rounded px-3 py-2 mb-2"
                    placeholder="Libellé"
                  />
                  <textarea
                    v-model="editedCompetence.description"
                    class="w-full border rounded px-3 py-2"
                    placeholder="Description"
                  ></textarea>
                </div>
                <div v-else>
                  <div class="font-bold text-lg">{{ competence.libelle }}</div>
                  <div class="text-gray-700">{{ competence.description }}</div>
                </div>
              </div>
              <div class="flex space-x-2 ml-4">
                <template v-if="editingCompetenceId === competence.id">
                  <button
                    @click="saveEdit(competence.id)"
                    class="bg-blue-500 text-white px-3 py-2 rounded hover:bg-blue-600 transition"
                  >
                    Sauvegarder
                  </button>
                  <button
                    @click="cancelEdit"
                    class="bg-gray-500 text-white px-3 py-2 rounded hover:bg-gray-600 transition"
                  >
                    Annuler
                  </button>
                </template>
                <template v-else>
                  <button
                    @click="editCompetence(competence)"
                    class="bg-yellow-500 text-white px-3 py-2 rounded hover:bg-yellow-600 transition"
                  >
                    Modifier
                  </button>
                  <button
                    @click="deleteCompetence(competence.id)"
                    class="bg-red-500 text-white px-3 py-2 rounded hover:bg-red-600 transition"
                  >
                    Supprimer
                  </button>
                </template>
              </div>
            </li>
          </ul>
          <div v-if="!competences.length" class="text-gray-500 text-center mt-4">
            Aucune compétence trouvée.
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { besoinService } from '@/services/besoinService';
import { competenceService } from '@/services/competenceService';

  export default {
    name: 'AdminCompetences',
    data() {
      return {
        newCompetence: {
          libelle: '',
          description: ''
        },
        competences: [],
        loading: false,
        editingCompetenceId: null,
        editedCompetence: {
          libelle: '',
          description: ''
        }
      };
    },
    methods: {
      async fetchCompetences() {
        this.loading = true;
        try {
          this.competences = await besoinService.getCompetences();
          console.log(this.competences);
        } catch (error) {
          console.error("Erreur lors de la récupération des compétences:", error);
        } finally {
          this.loading = false;
        }
      },
      async submitNewCompetence() {
        try {
          const payload = {
            libelle: this.newCompetence.libelle,
            description: this.newCompetence.description
          };
          await competenceService.addCompetence(payload);
          this.newCompetence.libelle = '';
          this.newCompetence.description = '';
          this.fetchCompetences();
        } catch (error) {
          console.error("Erreur lors de l'ajout de la compétence:", error);
        }
      },
      editCompetence(competence) {
        this.editingCompetenceId = competence.id;
        this.editedCompetence.libelle = competence.libelle;
        this.editedCompetence.description = competence.description;
      },
      cancelEdit() {
        this.editingCompetenceId = null;
        this.editedCompetence.libelle = '';
        this.editedCompetence.description = '';
      },
      async saveEdit(id) {
        try {
          const payload = {
            libelle: this.editedCompetence.libelle,
            description: this.editedCompetence.description
          };
          await competenceService.updateCompetence(id, payload);
          this.editingCompetenceId = null;
          this.fetchCompetences();
        } catch (error) {
          console.error("Erreur lors de la modification de la compétence:", error);
        }
      },
      async deleteCompetence(id) {
        try {
          await competenceService.deleteCompetence(id);
          this.fetchCompetences();
        } catch (error) {
          console.error("Erreur lors de la suppression de la compétence:", error);
        }
      }
    },
    created() {
      this.fetchCompetences();
    }
  };
  </script>
  
  <style scoped>
  button {
    transition: all 0.2s ease-in-out;
  }
  </style>
  