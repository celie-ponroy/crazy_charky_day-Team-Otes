import { API_URL } from './config';

export const serviceService = {
  async getServicesBySalarie(salarieId) {
    const token = localStorage.getItem('token');
    const response = await fetch(`${API_URL}/services?id=${salarieId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        ...(token && { 'Authorization': `Bearer ${token}` }),
      },
      mode: 'cors',
    });

    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Erreur lors de la récupération des services');
    }

    const result = await response.json();
    console.log('Récupération des services pour salarie', result);
    return result;
  },
};
