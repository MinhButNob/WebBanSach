import axios from 'axios'

const API = 'http://localhost:8080/api/dashboard'

export const getAll = () => axios.get(API)

export const getActive = () => axios.get(`${API}/active`)

export const create = (data) => axios.post(API, data)

export const update = (id, data) => axios.put(`${API}/${id}`, data)

export const remove = (id) => axios.delete(`${API}/${id}`)
