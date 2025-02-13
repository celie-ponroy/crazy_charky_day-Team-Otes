import { API_URL } from "./config"

export const authService = {
  async register(userData) {
    const response = await fetch(`${API_URL}/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        nom: userData.nom,
        password: userData.password
      })
    })

    if (!response.ok) {
      const error = await response.json()
      throw new Error(error.message || 'Erreur lors de l\'inscription')
    }

    return await response.json()
  },

  async login(credentials) {
    const response = await fetch(`${API_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        nom: credentials.nom,
        password: credentials.password
      })
    })

    if (!response.ok) {
      const error = await response.json()
      throw new Error(error.message || 'Email ou mot de passe incorrect')
    }

    const data = await response.json()
    console.log('Login response:', data)
    return data
  }
}
