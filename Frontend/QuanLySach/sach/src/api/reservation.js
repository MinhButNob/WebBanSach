import axiosClient from './axios'

export const reservationApi = {
  getAll(params) {
    return axiosClient.get('/reservations', { params })
  },
  getMine() {
    return axiosClient.get('/reservations/me')
  },
  create(payload) {
    return axiosClient.post('/reservations', payload)
  },
  updateStatus(id, status) {
    return axiosClient.patch(`/reservations/${id}/status`, { status })
  },
  cancel(id) {
    return axiosClient.delete(`/reservations/${id}`)
  },
}
