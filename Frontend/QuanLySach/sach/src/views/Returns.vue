<script setup>
import { computed, ref } from 'vue'
import { authState } from '../stores/auth'
import { getUserByEmailOrCard } from '../data/mockData'

const searchQuery = ref('student@school.edu.vn')
const selectedLoanIds = ref([1])

const selectedUser = computed(() => getUserByEmailOrCard(searchQuery.value))

const estimatedFine = computed(() =>
  selectedUser.value.loans
    .filter((loan) => selectedLoanIds.value.includes(loan.id) && loan.status === 'Qua han')
    .reduce((sum, loan) => sum + loan.fineAmount, 0),
)
</script>

<template>
  <div>
    <div class="d-flex justify-content-between align-items-start flex-wrap gap-3 mb-4">
      <div>
        <h1 class="h3 fw-bold mb-1">Tra sach</h1>
        <p class="text-muted mb-0">Quay tra sach voi tim kiem nhanh va xem truoc tien phat.</p>
      </div>
      <span class="badge text-bg-info text-dark px-3 py-2">
        Vai tro: {{ authState.currentUser.role }}
      </span>
    </div>

    <div v-if="authState.currentUser.role === 'Student'" class="alert alert-warning">
      Trang nay chi danh cho thu thu va quan tri.
    </div>

    <div class="card border-0 shadow-sm mb-4">
      <div class="card-body">
        <label class="form-label">Tim kiem theo email hoac so the thanh vien</label>
        <div class="input-group">
          <span class="input-group-text"><FaIcon name="fa-magnifying-glass" /></span>
          <input
            v-model="searchQuery"
            type="text"
            class="form-control"
            placeholder="student@school.edu.vn or LMS-1001"
          />
          <button type="button" class="btn btn-primary">Tim kiem</button>
        </div>
      </div>
    </div>

    <div class="row g-4">
      <div class="col-12 col-xl-4">
        <div class="card border-0 shadow-sm h-100">
          <div class="card-header bg-white border-0 py-3">
            <h2 class="h5 mb-0">Thong tin nguoi dung</h2>
          </div>
          <div class="card-body">
            <p class="mb-1 fw-semibold">{{ selectedUser.name }}</p>
            <p class="text-muted mb-2">{{ selectedUser.email }}</p>
            <p class="mb-2">
              <span class="text-muted">So the thanh vien:</span> {{ selectedUser.cardNumber }}
            </p>
            <p class="mb-0">
              <span class="text-muted">So du tien phat:</span>
              {{ selectedUser.penaltyBalance.toLocaleString() }} VND
            </p>
          </div>
        </div>
      </div>

      <div class="col-12 col-xl-8">
        <div class="card border-0 shadow-sm">
          <div
            class="card-header bg-white border-0 py-3 d-flex justify-content-between align-items-center"
          >
            <h2 class="h5 mb-0">Khoan dang muon</h2>
            <span class="badge text-bg-secondary">{{ selectedUser.loans.length }} ban sao</span>
          </div>
          <div class="table-responsive">
            <table class="table align-middle mb-0">
              <thead class="table-light">
                <tr>
                  <th></th>
                  <th>Ten sach</th>
                  <th>Han tra</th>
                  <th>Trang thai</th>
                  <th>Tien phat</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="loan in selectedUser.loans" :key="loan.id">
                  <td>
                    <input
                      v-model="selectedLoanIds"
                      :value="loan.id"
                      class="form-check-input"
                      type="checkbox"
                    />
                  </td>
                  <td class="fw-semibold">{{ loan.bookTitle }}</td>
                  <td>{{ loan.dueDate }}</td>
                  <td>
                    <span
                      :class="
                        loan.status === 'Qua han' ? 'badge text-bg-danger' : 'badge text-bg-success'
                      "
                    >
                      {{ loan.status }}
                    </span>
                  </td>
                  <td>{{ loan.fineAmount.toLocaleString() }} VND</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div
            class="card-footer bg-white border-0 p-3 d-flex justify-content-between align-items-center flex-wrap gap-3"
          >
            <div>
              <div class="text-muted small">Ước tính tien phat qua han</div>
              <div class="h5 mb-0">{{ estimatedFine.toLocaleString() }} VND</div>
            </div>
            <button type="button" class="btn btn-success">Xac nhan tra sach</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
