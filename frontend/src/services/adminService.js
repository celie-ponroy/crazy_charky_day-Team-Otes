import api from '@/services/api';
import { apiOpti } from './api';
export const adminService = {
  createSalarie(payload) {
    console.log("payload", payload);
    console.log("api", api);
    return api.post('/users/salaries', payload)
      .then(response => response.data)
      .catch(error => {
        console.error("Erreur lors de la création du salarié:", error);
        throw error;
      });
  },
  getSalaries() {
    return api.get('/users/salaries')
      .then(response => response.data)
      .catch(error => {
        console.error("Erreur lors de la récupération des salariés:", error);
        throw error;
      });
  },    
  async launchAffectation(payload) {
    return apiOpti.post('/api/runCalcul', payload, {
        headers: {
          'Content-Type': 'application/json',
        }
      })
      .then(response => response.data)
      .catch(error => {
        console.error("Erreur lors du lancement de l'affectation:", error);
        throw error;
      });
  },
};
