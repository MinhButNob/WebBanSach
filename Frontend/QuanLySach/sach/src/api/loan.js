import axiosClient from './axios'

export const loanApi = {
  getUserLoans(userId) {
    return axiosClient.get(`/loans/user/${userId}`)
  },
  getCurrentLoans() {
    return axiosClient.get('/loans/current')
  },
  getLoanHistory() {
    return axiosClient.get('/loans/history')
  },
  borrow(bookIds) {
    return axiosClient.post('/loans/borrow', { bookIds })
  },
  returnBook(loanDetailId) {
    return axiosClient.put(`/loans/return/${loanDetailId}`)
  },
  renew(loanDetailId, days) {
    return axiosClient.post(`/loans/renew/${loanDetailId}`, { days })
  },
  getOverdue() {
    return axiosClient.get('/loans/overdue')
  },
}
