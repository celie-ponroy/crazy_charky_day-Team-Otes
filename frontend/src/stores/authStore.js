import { defineStore } from 'pinia'
import { authService } from '@/services/authService'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    isLoggedIn: false,
    user: null,
    token: null,
    error: null,
  }),

  persist: {
    key: 'auth',
    storage: localStorage,
    paths: ['isLoggedIn', 'user', 'token'],
  },

  actions: {
    async login(credentials) {
      this.error = null;
      try {
        const response = await authService.login(credentials);
        if (response?.success && response.data) {
          const decodedToken = this.decodeJWT(response.data.accessToken);
          if (!decodedToken) {
            throw new Error('Token invalide');
          }
          this.token = response.data.accessToken;
          this.user = {
            id: decodedToken.sub,
            role: decodedToken.role,
          };
          this.isLoggedIn = true;
        }
      } catch (error) {
        this.error = error.message;
        throw error;
      }
    },

    decodeJWT(token) {
      if (!token) return null;
      try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(
          atob(base64)
            .split('')
            .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
            .join('')
        );
        return JSON.parse(jsonPayload);
      } catch (error) {
        console.error('Erreur lors du décodage du token:', error);
        return null;
      }
    },

    async register(userData) {
      this.error = null;
      try {
        await authService.register(userData);
        await this.login({
          nom: userData.nom,
          password: userData.password,
        });
      } catch (error) {
        this.error = error.message;
        throw error;
      }
    },

    logout(router) {
      this.isLoggedIn = false;
      this.user = null;
      this.token = null;
      this.error = null;
      if (router) {
        router.push('/');
      }
    },

  },
});