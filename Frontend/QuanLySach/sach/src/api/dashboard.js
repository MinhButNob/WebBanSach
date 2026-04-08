import axiosClient from './axios'

export const dashboardApi = {
  getStats() {
    return axiosClient.get('/dashboard/stats')
  },
  getRecentLoans() {
    return axiosClient.get('/dashboard/recent-loans')
  },
  getTopBooks() {
    return axiosClient.get('/dashboard/top-books')
  },
  getOverdueStats() {
    return axiosClient.get('/dashboard/overdue-stats')
  },
}
