<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { demoAccounts } from '../data/mockData'
import { loginAsEmail } from '../stores/auth'

const router = useRouter()

const form = reactive({
  email: demoAccounts[0].email,
  password: '',
})

const selectDemoAccount = (email) => {
  form.email = email
}

const handleLogin = () => {
  loginAsEmail(form.email)
  router.push('/dashboard')
}
</script>

<template>
  <div class="row justify-content-center align-items-center min-vh-100 py-4">
    <div class="col-12 col-md-8 col-lg-6 col-xl-5">
      <div class="card">
        <div class="card-body p-4">
          <h1 class="h4 mb-3">Dang nhap thu vien</h1>
          <p class="text-muted mb-4">Chọn tài khoản demo hoặc nhập email để đăng nhập.</p>

          <div class="mb-3">
            <label class="form-label">Tài khoản demo</label>
            <div class="d-grid gap-2">
              <button
                v-for="account in demoAccounts"
                :key="account.email"
                type="button"
                class="btn btn-outline-secondary text-start"
                @click="selectDemoAccount(account.email)"
              >
                {{ account.role }} - {{ account.email }}
              </button>
            </div>
          </div>

          <form class="row g-3" @submit.prevent="handleLogin">
            <div class="col-12">
              <label class="form-label">Email</label>
              <input
                v-model="form.email"
                type="email"
                class="form-control"
                placeholder="you@example.com"
                required
              />
            </div>

            <div class="col-12">
              <label class="form-label">Mat khau</label>
              <input
                v-model="form.password"
                type="password"
                class="form-control"
                placeholder="Mat khau"
                required
              />
            </div>

            <div class="col-12 d-grid">
              <button type="submit" class="btn btn-primary">Đăng nhập</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>




