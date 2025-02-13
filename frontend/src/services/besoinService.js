import { API_URL } from "./config";
import { useAuthStore } from '@/stores/authStore';


export const besoinService = {
  async getCompetences() {
    const token = useAuthStore().token;
    const response = await fetch(`${API_URL}/competences`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        ...(token && { 'Authorization': `Bearer ${token}` }),
      },
    }); 

    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Erreur lors de la récupération des compétences');
    }

    const result = await response.json();
    console.log('Récupération des compétences', result);
    return result;
  },

  async getBesoins() {
    const token = useAuthStore().token;
    const response = await fetch(`${API_URL}/besoins`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        ...(token && { 'Authorization': `Bearer ${token}` }),
      },
      mode: 'cors',
    });

    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Erreur lors de la récupération des besoins');
    }

    const result = await response.json();
    console.log('Récupération des besoins', result);
    return result;
  },

  async addBesoin(besoin) {
    const token = useAuthStore().token;
    const response = await fetch(`${API_URL}/besoins`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        ...(token && { 'Authorization': `Bearer ${token}` }),
      },
      body: JSON.stringify(besoin),
      mode: 'cors',
    });

    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || "Erreur lors de l'ajout du besoin");
    }

    const result = await response.json();
    console.log('Ajout du besoin', result);
    return result;
  },
};