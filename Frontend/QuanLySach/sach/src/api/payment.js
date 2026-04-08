import axiosClient from './axios'

export const paymentApi = {
  getAll(params) {
    return axiosClient.get('/payments', { params })
  },
  getMine() {
    return axiosClient.get('/payments/me')
  },
  create(payload) {
    return axiosClient.post('/payments', payload)
  },
  confirm(id) {
    return axiosClient.patch(`/payments/${id}/confirm`)
  },
}
