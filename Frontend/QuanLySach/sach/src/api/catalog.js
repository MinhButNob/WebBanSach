import axiosClient from './axios'

const resourceApi = (resource) => ({
  getAll() {
    return axiosClient.get(`/catalog/${resource}`)
  },
  create(payload) {
    return axiosClient.post(`/catalog/${resource}`, payload)
  },
  update(id, payload) {
    return axiosClient.put(`/catalog/${resource}/${id}`, payload)
  },
  delete(id) {
    return axiosClient.delete(`/catalog/${resource}/${id}`)
  },
})

export const catalogApi = {
  authors: resourceApi('authors'),
  categories: resourceApi('categories'),
  publishers: resourceApi('publishers'),
  shelves: resourceApi('shelves'),
}
