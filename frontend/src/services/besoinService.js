const API_URL = 'http://localhost:2000'

export const gameService = {
  async getCompetences() {
    const response = await fetch(`${API_URL}/competences`, {
      headers: {
        'Content-Type': 'application/json',
      },
      mode: 'cors',
    })

    if (!response.ok) {
      const error = await response.json()
      throw new Error(error.message || 'Erreur lors de la récupération des compétences')
    }

    const result = await response.json()
    console.log('Récupération des compétences', result)
    return result
  },

  async getBesoins() {
    const response = await fetch(`${API_URL}/besoins`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      mode: 'cors',
    })

    if (!response.ok) {
      const error = await response.json()
      throw new Error(error.message || 'Erreur lors de la récupération des besoins')
    }

    const result = await response.json()
    console.log('Récupération des besoins', result)
    return result
  },

  async addBesoin(besoin) {
    const response = await fetch(`${API_URL}/besoins`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(besoin),
      mode: 'cors',
    })

    if (!response.ok) {
      const error = await response.json()
      throw new Error(error.message || "Erreur lors de l'ajout du besoin")
    }

    const result = await response.json()
    console.log('Ajout du besoin', result)
    return result
  },
}
