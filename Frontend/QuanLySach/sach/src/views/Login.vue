<template>
  <div class="row justify-content-center align-items-center min-vh-100 py-4">
    <div class="col-12 col-md-8 col-lg-6 col-xl-5">
      <div class="card shadow">
        <div class="card-body p-4">
          <h1 class="h4 mb-3 text-center">📚 Đăng nhập Thư viện</h1>
          <p class="text-muted text-center mb-4">Vui lòng đăng nhập để tiếp tục</p>

          <!-- Hiển thị lỗi -->
          <div v-if="error" class="alert alert-danger alert-dismissible fade show" role="alert">
            {{ error }}
            <button type="button" class="btn-close" @click="error = ''"></button>
          </div>

          <form @submit.prevent="handleLogin">
            <div class="mb-3">
              <label class="form-label">Email</label>
              <input
                v-model="form.email"
                type="email"
                class="form-control"
                placeholder="you@example.com"
                required
              />
            </div>

            <div class="mb-3">
              <label class="form-label">Mật khẩu</label>
              <input
                v-model="form.password"
                type="password"
                class="form-control"
                placeholder="Nhập mật khẩu"
                required
              />
            </div>

            <div class="d-grid gap-2">
              <button type="submit" class="btn btn-primary" :disabled="loading">
                <span v-if="loading" class="spinner-border spinner-border-sm me-2"></span>
                {{ loading ? 'Đang xử lý...' : 'Đăng nhập' }}
              </button>
              <button type="button" class="btn btn-link" @click="goToRegister">
                Chưa có tài khoản? Đăng ký ngay
              </button>
            </div>
          </form>

          <hr class="my-4" />

          <div class="text-center">
            <p class="text-muted small mb-2">Tài khoản demo:</p>
            <div class="d-flex justify-content-center gap-2 flex-wrap">
              <button
                type="button"
                class="btn btn-sm btn-outline-secondary"
                @click="setDemoAccount('user@gmail.com', '123456')"
              >
                User
              </button>
              <button
                type="button"
                class="btn btn-sm btn-outline-secondary"
                @click="setDemoAccount('librarian@gmail.com', '123456')"
              >
                Librarian
              </button>
              <button
                type="button"
                class="btn btn-sm btn-outline-secondary"
                @click="setDemoAccount('admin@gmail.com', '123456')"
              >
                Admin
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { login as setAuthLogin } from '../stores/auth'

const router = useRouter()
const loading = ref(false)
const error = ref('')

const form = reactive({
  email: '',
  password: '',
})

const setDemoAccount = (email, password) => {
  form.email = email
  form.password = password
}

const goToRegister = () => {
  router.push('/register')
}

const handleLogin = async () => {
  if (!form.email || !form.password) {
    error.value = 'Vui lòng nhập đầy đủ email và mật khẩu'
    return
  }

  loading.value = true
  error.value = ''

  try {
    const response = await axios.post('http://localhost:8080/api/auth/login', {
      email: form.email,
      password: form.password,
    })

    if (response.data) {
      const userData = {
        id: response.data.id,
        name: response.data.name,
        email: response.data.email,
        role: response.data.roleName,
        roleId: response.data.roleId,
        membershipLevel: response.data.membershipLevel,
      }

      // Đồng bộ cả store + localStorage để tránh lệch trạng thái UI sau login
      setAuthLogin(userData, response.data.token)

      // Chuyển hướng theo role
      const roleName = response.data.roleName
      const roleId = response.data.roleId

      if (roleId === 3 || roleName === 'Admin') {
        router.push('/admin/books')
      } else if (roleId === 2 || roleName === 'Librarian') {
        router.push('/librarian/dashboard')
      } else {
        router.push('/dashboard')
      }
    }
  } catch (err) {
    console.error('Login error:', err)
    error.value = err.response?.data || 'Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.card {
  border-radius: 10px;
}
.btn-link {
  text-decoration: none;
}
</style>
