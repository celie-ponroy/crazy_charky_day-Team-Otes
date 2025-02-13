const API_URL = 'http://localhost:2000'

export const authService = {
  async register(userData) {
    const response = await fetch(`${API_URL}/auth/register`, {
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
    const response = await fetch(`${API_URL}/auth/login`, {
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
