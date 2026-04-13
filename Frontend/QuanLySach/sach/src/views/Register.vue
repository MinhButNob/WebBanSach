<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const loading = ref(false)
const error = ref('')
const success = ref('')

const form = reactive({
  name: '',
  email: '',
  password: '',
  confirmPassword: '',
  membershipLevel: 'STANDARD'
})

const membershipOptions = [
  { value: 'STANDARD', label: 'Standard - Mượn tối đa 5 cuốn' },
  { value: 'PREMIUM', label: 'Premium - Mượn tối đa 10 cuốn' }
]

const validateForm = () => {
  if (!form.name) {
    error.value = 'Vui lòng nhập họ tên'
    return false
  }
  if (!form.email) {
    error.value = 'Vui lòng nhập email'
    return false
  }
  if (!form.password) {
    error.value = 'Vui lòng nhập mật khẩu'
    return false
  }
  if (form.password.length < 6) {
    error.value = 'Mật khẩu phải có ít nhất 6 ký tự'
    return false
  }
  if (form.password !== form.confirmPassword) {
    error.value = 'Mật khẩu xác nhận không khớp'
    return false
  }
  return true
}

const handleRegister = async () => {
  if (!validateForm()) return

  loading.value = true
  error.value = ''
  success.value = ''

  try {
    const response = await axios.post('http://localhost:8080/api/auth/register', {
      name: form.name,
      email: form.email,
      password: form.password,
      membershipLevel: form.membershipLevel
    })

    if (response.data) {
      success.value = 'Đăng ký thành công! Đang chuyển hướng đến trang đăng nhập...'
      
      // Tự động đăng nhập sau khi đăng ký
      setTimeout(() => {
        router.push('/login')
      }, 2000)
    }
  } catch (err) {
    console.error('Register error:', err)
    error.value = err.response?.data || 'Đăng ký thất bại. Vui lòng thử lại.'
  } finally {
    loading.value = false
  }
}

const goToLogin = () => {
  router.push('/login')
}
</script>

<template>
  <div class="row justify-content-center align-items-center min-vh-100 py-4">
    <div class="col-12 col-md-8 col-lg-6 col-xl-5">
      <div class="card shadow">
        <div class="card-body p-4">
          <h1 class="h4 mb-3 text-center">📝 Đăng ký tài khoản</h1>
          <p class="text-muted text-center mb-4">Tạo tài khoản mới để sử dụng dịch vụ</p>

          <!-- Hiển thị lỗi -->
          <div v-if="error" class="alert alert-danger alert-dismissible fade show" role="alert">
            {{ error }}
            <button type="button" class="btn-close" @click="error = ''"></button>
          </div>

          <!-- Hiển thị thành công -->
          <div v-if="success" class="alert alert-success alert-dismissible fade show" role="alert">
            {{ success }}
            <button type="button" class="btn-close" @click="success = ''"></button>
          </div>

          <form @submit.prevent="handleRegister">
            <div class="mb-3">
              <label class="form-label">Họ tên <span class="text-danger">*</span></label>
              <input
                v-model="form.name"
                type="text"
                class="form-control"
                placeholder="Nhập họ tên"
                required
              />
            </div>

            <div class="mb-3">
              <label class="form-label">Email <span class="text-danger">*</span></label>
              <input
                v-model="form.email"
                type="email"
                class="form-control"
                placeholder="you@example.com"
                required
              />
            </div>

            <div class="mb-3">
              <label class="form-label">Mật khẩu <span class="text-danger">*</span></label>
              <input
                v-model="form.password"
                type="password"
                class="form-control"
                placeholder="Ít nhất 6 ký tự"
                required
              />
            </div>

            <div class="mb-3">
              <label class="form-label">Xác nhận mật khẩu <span class="text-danger">*</span></label>
              <input
                v-model="form.confirmPassword"
                type="password"
                class="form-control"
                placeholder="Nhập lại mật khẩu"
                required
              />
            </div>

            <div class="mb-3">
              <label class="form-label">Gói thành viên</label>
              <select v-model="form.membershipLevel" class="form-select">
                <option v-for="opt in membershipOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </option>
              </select>
              <div class="form-text text-muted">
                Standard: Mượn tối đa 5 cuốn, Premium: Mượn tối đa 10 cuốn
              </div>
            </div>

            <div class="d-grid gap-2">
              <button type="submit" class="btn btn-primary" :disabled="loading">
                <span v-if="loading" class="spinner-border spinner-border-sm me-2"></span>
                {{ loading ? 'Đang xử lý...' : 'Đăng ký' }}
              </button>
              <button type="button" class="btn btn-link" @click="goToLogin">
                Đã có tài khoản? Đăng nhập ngay
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card {
  border-radius: 10px;
}
.btn-link {
  text-decoration: none;
}
</style>
