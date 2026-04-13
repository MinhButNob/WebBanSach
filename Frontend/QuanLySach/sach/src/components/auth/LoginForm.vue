<template>
  <div class="login-container">
    <div class="login-card">
      <h2 class="text-center mb-4">📚 Đăng Nhập Thư Viện</h2>

      <form @submit.prevent="handleLogin">
        <div class="mb-3">
          <label for="email" class="form-label">Email</label>
          <input
            v-model="form.email"
            type="email"
            class="form-control"
            id="email"
            placeholder="Nhập email của bạn"
            required
          />
        </div>

        <div class="mb-3">
          <label for="password" class="form-label">Mật khẩu</label>
          <input
            v-model="form.password"
            type="password"
            class="form-control"
            id="password"
            placeholder="Nhập mật khẩu của bạn"
            required
          />
        </div>

        <div v-if="error" class="alert alert-danger" role="alert">
          {{ error }}
        </div>

        <button type="submit" class="btn btn-primary w-100" :disabled="loading">
          {{ loading ? 'Đang đăng nhập...' : 'Đăng Nhập' }}
        </button>
      </form>

      <div class="text-center mt-3">
        <p class="mb-0">Chưa có tài khoản? <router-link to="/register">Đăng ký</router-link></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../../api/auth'

const router = useRouter()
const form = ref({ email: '', password: '' })
const error = ref('')
const loading = ref(false)

const handleLogin = async () => {
  error.value = ''
  loading.value = true

  try {
    const response = await login(form.value)
    localStorage.setItem('token', response.token)
    localStorage.setItem('user', JSON.stringify(response.user))

    // Redirect based on role
    if (response.user.role === 'ADMIN') {
      router.push('/admin/users')
    } else if (response.user.role === 'LIBRARIAN') {
      router.push('/librarian/dashboard')
    } else {
      router.push('/home')
    }
  } catch (err) {
    error.value = err.response?.data?.message || 'Đăng nhập thất bại'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  background: white;
  border-radius: 10px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
  padding: 40px;
  width: 100%;
  max-width: 400px;
}

.login-card h2 {
  color: #333;
  font-weight: 600;
}

.form-label {
  font-weight: 500;
  color: #555;
}

.btn-primary {
  background-color: #667eea;
  border: none;
  padding: 10px;
  font-weight: 600;
}

.btn-primary:hover:not(:disabled) {
  background-color: #764ba2;
}

.btn-primary:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

a {
  color: #667eea;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style>
