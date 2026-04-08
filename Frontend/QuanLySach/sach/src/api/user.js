import axiosClient from './axios'

export const userApi = {
  getAll() {
    return axiosClient.get('/users')
  },
  getById(id) {
    return axiosClient.get(`/users/${id}`)
  },
  create(userData) {
    return axiosClient.post('/users', userData)
  },
  update(id, userData) {
    return axiosClient.put(`/users/${id}`, userData)
  },
  delete(id) {
    return axiosClient.delete(`/users/${id}`)
  },
  getProfile() {
    return axiosClient.get('/users/profile')
  },
  updateProfile(profileData) {
    return axiosClient.put('/users/profile', profileData)
  },
}
