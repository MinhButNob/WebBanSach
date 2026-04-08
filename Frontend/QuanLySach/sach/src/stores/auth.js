import { reactive } from 'vue'
import { users } from '../data/mockData'

const storageKey = 'library-demo-user-email'

const resolveUser = (email) => users.find((user) => user.email === email) ?? users[0]

const storedEmail = typeof window !== 'undefined' ? window.localStorage.getItem(storageKey) : null

export const authState = reactive({
  currentUser: resolveUser(storedEmail),
})

export const loginAsEmail = (email) => {
  authState.currentUser = resolveUser(email)
  if (typeof window !== 'undefined') {
    window.localStorage.setItem(storageKey, authState.currentUser.email)
  }
}

export const logout = () => {
  authState.currentUser = users[0]
  if (typeof window !== 'undefined') {
    window.localStorage.removeItem(storageKey)
  }
}
