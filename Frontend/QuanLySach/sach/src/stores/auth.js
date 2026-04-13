// src/stores/auth.js
import { reactive } from 'vue'

export const authState = reactive({
  currentUser: null,
  token: null,
  isAuthenticated: false,
})

// Khởi tạo từ localStorage
export const initAuth = () => {
  const token = localStorage.getItem('token')
  const userStr = localStorage.getItem('user')

  if (token && userStr) {
    try {
      authState.currentUser = JSON.parse(userStr)
      authState.token = token
      authState.isAuthenticated = true
    } catch (e) {
      console.warn('Invalid auth data in localStorage. Resetting auth state.', e)
      authState.currentUser = null
      authState.token = null
      authState.isAuthenticated = false
      localStorage.removeItem('token')
      localStorage.removeItem('user')
    }
  }
}

// Đăng nhập
export const login = (userData, token) => {
  authState.currentUser = userData
  authState.token = token
  authState.isAuthenticated = true
  localStorage.setItem('token', token)
  localStorage.setItem('user', JSON.stringify(userData))
}

// Đăng xuất - QUAN TRỌNG: phải reset hoàn toàn
export const logout = () => {
  authState.currentUser = null
  authState.token = null
  authState.isAuthenticated = false
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  // Không gọi router.push ở đây, để component gọi
}

initAuth()
