import axios from 'axios';
import api from '@/services/api';

export const competenceService = {
  async getCompetences() {
    return api.get('/competences')
      .then(response => response.data)
      .catch(error => {
        console.error("Erreur lors de la récupération des compétences:", error);
        throw error;
      });
  },
  async addCompetence(payload) {
    return api.post('/competences', payload)
      .then(response => response.data)
      .catch(error => {
        console.error("Erreur lors de l'ajout de la compétence:", error);
        throw error;
      });
  },
  async updateCompetence(id, payload) {
    return api.patch(`/competences/${id}`, payload)
      .then(response => response.data)
      .catch(error => {
        console.error("Erreur lors de la modification de la compétence:", error);
        throw error;
      });
  },
  async deleteCompetence(id) {
    return api.delete(`/competences/${id}`)
      .then(response => response.data)
      .catch(error => {
        console.error("Erreur lors de la suppression de la compétence:", error);
        throw error;
      });
  }
};
