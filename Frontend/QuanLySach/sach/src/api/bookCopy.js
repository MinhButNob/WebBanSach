import axiosClient from './axios'

export const bookCopyApi = {
  getAll(params) {
    return axiosClient.get('/book-copies', { params })
  },
  getById(id) {
    return axiosClient.get(`/book-copies/${id}`)
  },
  create(payload) {
    return axiosClient.post('/book-copies', payload)
  },
  update(id, payload) {
    return axiosClient.put(`/book-copies/${id}`, payload)
  },
  delete(id) {
    return axiosClient.delete(`/book-copies/${id}`)
  },
}
