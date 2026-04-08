import axiosClient from './axios'

export const authApi = {
  login(credentials) {
    return axiosClient.post('/auth/login', credentials)
  },
  register(userData) {
    return axiosClient.post('/auth/register', userData)
  },
  logout() {
    return axiosClient.post('/auth/logout')
  },
  getCurrentUser() {
    return axiosClient.get('/auth/me')
  },
}




