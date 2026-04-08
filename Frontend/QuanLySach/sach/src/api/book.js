import axiosClient from './axios'

export const bookApi = {
  getAll(params) {
    return axiosClient.get('/books', { params })
  },
  getById(id) {
    return axiosClient.get(`/books/${id}`)
  },
  search(keyword) {
    return axiosClient.get('/books/search', { params: { keyword } })
  },
  getByCategory(categoryId) {
    return axiosClient.get(`/books/category/${categoryId}`)
  },
  getTopBorrowed(limit = 10) {
    return axiosClient.get('/books/top-borrowed', { params: { limit } })
  },
  create(bookData) {
    return axiosClient.post('/books', bookData)
  },
  update(id, bookData) {
    return axiosClient.put(`/books/${id}`, bookData)
  },
  delete(id) {
    return axiosClient.delete(`/books/${id}`)
  },
}
