import axios from 'axios';
import { useAuthStore } from '@/stores/authStore';
import { API_URL_OPTI } from './config';
import { API_URL } from './config';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

export const apiOpti = axios.create({
    baseURL: API_URL_OPTI,
    headers: {
      'Content-Type': 'application/json'
    }
  });

api.interceptors.request.use(
  config => {
    const authStore = useAuthStore();
    if (authStore.token) {
      config.headers.Authorization = `Bearer ${authStore.token}`;
    }
    return config;
  },
  error => Promise.reject(error)
);

export default api;
