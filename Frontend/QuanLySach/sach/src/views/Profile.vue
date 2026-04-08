<script setup>
import { reactive, ref } from 'vue'
import { authState } from '../stores/auth'
import { loanHistory } from '../data/mockData'

const editMode = ref(false)

const form = reactive({
  name: authState.currentUser.name,
  email: authState.currentUser.email,
  phone: authState.currentUser.phone,
  address: authState.currentUser.address,
})

const toggleSuaMode = () => {
  editMode.value = !editMode.value
}
</script>

<template>
  <div>
    <div class="d-flex justify-content-between align-items-start flex-wrap gap-3 mb-4">
      <div>
        <h1 class="h3 fw-bold mb-1">Ho so</h1>
        <p class="text-muted mb-0">Xem va cap nhat thong tin nguoi dung.</p>
      </div>
      <button type="button" class="btn btn-outline-primary" @click="toggleSuaMode">
        {{ editMode ? 'Luu thong tin' : 'sua thong tin' }}
      </button>
    </div>

    <div class="row g-4">
      <div class="col-12 col-xl-8">
        <div class="card border-0 shadow-sm">
          <div class="card-header bg-white border-0 py-3">
            <h2 class="h5 mb-0">Thong tin ca nhan</h2>
          </div>
          <div class="card-body">
            <div class="row g-3">
              <div class="col-12 col-md-6">
                <label class="form-label">Ten</label>
                <input v-model="form.name" type="text" class="form-control" :disabled="!editMode" />
              </div>
              <div class="col-12 col-md-6">
                <label class="form-label">Email</label>
                <input
                  v-model="form.email"
                  type="email"
                  class="form-control"
                  :disabled="!editMode"
                />
              </div>
              <div class="col-12 col-md-6">
                <label class="form-label">Dien thoai</label>
                <input
                  v-model="form.phone"
                  type="text"
                  class="form-control"
                  :disabled="!editMode"
                />
              </div>
              <div class="col-12 col-md-6">
                <label class="form-label">Dia chi</label>
                <input
                  v-model="form.address"
                  type="text"
                  class="form-control"
                  :disabled="!editMode"
                />
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="col-12 col-xl-4">
        <div class="card border-0 shadow-sm mb-4">
          <div class="card-header bg-white border-0 py-3">
            <h2 class="h5 mb-0">Thanh vien</h2>
          </div>
          <div class="card-body">
            <div class="mb-3">
              <div class="text-muted small">So the thanh vien</div>
              <div class="fw-semibold">{{ authState.currentUser.cardNumber }}</div>
            </div>
            <div class="row g-3">
              <div class="col-6">
                <div class="text-muted small">Hieu luc tu ngay</div>
                <div class="fw-semibold">{{ authState.currentUser.validFrom }}</div>
              </div>
              <div class="col-6">
                <div class="text-muted small">Hieu luc den ngay</div>
                <div class="fw-semibold">{{ authState.currentUser.validTo }}</div>
              </div>
              <div class="col-6">
                <div class="text-muted small">Toi da muon sach</div>
                <div class="fw-semibold">{{ authState.currentUser.maxLoans }}</div>
              </div>
              <div class="col-6">
                <div class="text-muted small">So du tien phat</div>
                <div class="fw-semibold text-danger">
                  {{ authState.currentUser.penaltyBalance.toLocaleString() }} VND
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="card border-0 shadow-sm">
          <div class="card-header bg-white border-0 py-3">
            <h2 class="h5 mb-0">Tom tat lich su muon</h2>
          </div>
          <div class="card-body">
            <div class="d-flex justify-content-between mb-2">
              <span class="text-muted">Tong so tra</span>
              <span class="fw-semibold">{{ loanHistory.length }}</span>
            </div>
            <div class="d-flex justify-content-between mb-2">
              <span class="text-muted">Da tra dung han</span>
              <span class="fw-semibold">{{
                loanHistory.filter((loan) => loan.fineAmount === 0).length
              }}</span>
            </div>
            <div class="d-flex justify-content-between">
              <span class="text-muted">Bi phat</span>
              <span class="fw-semibold">{{
                loanHistory.filter((loan) => loan.fineAmount > 0).length
              }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
