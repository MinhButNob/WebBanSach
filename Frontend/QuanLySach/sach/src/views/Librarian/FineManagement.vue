<template>
  <div class="container-fluid py-4">
    <h1 class="mb-4">Quan ly tien phat</h1>

    <!-- Filter & Tim kiem -->
    <div class="row mb-4">
      <div class="col-12 col-md-8">
        <input
          v-model="searchQuery"
          type="text"
          class="form-control"
          placeholder="Tim kiem theo ten hoac email..."
        />
      </div>
      <div class="col-12 col-md-4">
        <select v-model="statusFilter" class="form-select">
          <option value="">Tat ca trang thai</option>
          <option value="Chua thanh toan">Chua thanh toan</option>
          <option value="Partial">Thanh toan mot phan</option>
          <option value="Da thanh toan">Da thanh toan</option>
        </select>
      </div>
    </div>

    <!-- Statistics -->
    <div class="row mb-4">
      <div class="col-12 col-md-4 mb-3">
        <div class="card bg-danger-subtle border-danger border-2">
          <div class="card-body text-center">
            <h6 class="card-subtitle text-muted mb-2">Tong tien phat chua thanh toan</h6>
            <h3 class="card-title mb-0">{{ totalUnpaidAmount.toLocaleString() }} VND</h3>
          </div>
        </div>
      </div>
      <div class="col-12 col-md-4 mb-3">
        <div class="card bg-warning-subtle border-warning border-2">
          <div class="card-body text-center">
            <h6 class="card-subtitle text-muted mb-2">So luong tien phat chua thanh toan</h6>
            <h3 class="card-title mb-0">{{ unpaidFineCount }}</h3>
          </div>
        </div>
      </div>
      <div class="col-12 col-md-4 mb-3">
        <div class="card bg-success-subtle border-success border-2">
          <div class="card-body text-center">
            <h6 class="card-subtitle text-muted mb-2">Thu trong thang nay</h6>
            <h3 class="card-title mb-0">{{ collectedThisMonth.toLocaleString() }} VND</h3>
          </div>
        </div>
      </div>
    </div>

    <!-- Tien phat Table -->
    <div class="card mb-4">
      <div class="card-header bg-primary text-white">
        <h5 class="mb-0">Danh sach tien phat</h5>
      </div>
      <div class="table-responsive">
        <table class="table table-hover mb-0">
          <thead class="table-light">
            <tr>
              <th>Nguoi dung</th>
              <th>Ly do phat</th>
              <th>So tien</th>
              <th>Ngay lap</th>
              <th>Han tra</th>
              <th>Trang thai</th>
              <th>Thao tac</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="fine in filteredFineList" :key="fine.id">
              <td>
                <strong>{{ fine.userName }}</strong
                ><br />
                <small class="text-muted">{{ fine.email }}</small>
              </td>
              <td>{{ fine.reason }}</td>
              <td>
                <strong>{{ fine.amount.toLocaleString() }} VND</strong>
              </td>
              <td>{{ fine.issuedDate }}</td>
              <td>{{ fine.dueDate }}</td>
              <td>
                <span
                  :class="[
                    'badge',
                    fine.status === 'Chua thanh toan'
                      ? 'bg-danger'
                      : fine.status === 'Partial'
                        ? 'bg-warning'
                        : 'bg-success',
                  ]"
                >
                  {{ fine.status }}
                </span>
              </td>
              <td>
                <button
                  v-if="fine.status !== 'Da thanh toan'"
                  class="btn btn-sm btn-primary me-2"
                  @click="openPaymentForm(fine)"
                >
                  Thanh toan
                </button>
                <button class="btn btn-sm btn-info" @click="viewDetails(fine)">Chi tiet</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Payment Modal -->
    <div v-if="showPaymentForm" class="modal d-block" style="background: rgba(0, 0, 0, 0.5)">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5>Thanh toan tien phat</h5>
            <button type="button" class="btn-close" @click="showPaymentForm = false"></button>
          </div>
          <div class="modal-body">
            <div v-if="selectedFine" class="mb-3">
              <p><strong>Nguoi dung:</strong> {{ selectedFine.userName }}</p>
              <p><strong>Ly do:</strong> {{ selectedFine.reason }}</p>
              <p>
                <strong>So tien phat:</strong>
                <strong>{{ selectedFine.amount.toLocaleString() }} VND</strong>
              </p>
            </div>

            <div class="mb-3">
              <label class="form-label"
                >Phuong thuc thanh toan <span class="text-danger">*</span></label
              >
              <select v-model="paymentForm.method" class="form-select">
                <option value="">Chon phuong thuc...</option>
                <option value="Cash">Tien mat</option>
                <option value="Card">The tin dung/Ghi no</option>
                <option value="Bank Transfer">Chuyen khoan ngan hang</option>
              </select>
            </div>

            <div class="mb-3">
              <label class="form-label"
                >So tien thanh toan <span class="text-danger">*</span></label
              >
              <input v-model.number="paymentForm.amount" type="number" class="form-control" />
              <small class="text-muted">
                De trong tro thanh toan toan bo ({{ selectedFine?.amount.toLocaleString() }} VND)
              </small>
            </div>

            <div class="mb-3">
              <label class="form-label">So tham khao</label>
              <input
                v-model="paymentForm.reference"
                type="text"
                class="form-control"
                placeholder="e.g., Ma giao dich"
              />
            </div>

            <div class="mb-3">
              <label class="form-label">Ghi chu</label>
              <textarea v-model="paymentForm.notes" class="form-control" rows="2"></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button @click="showPaymentForm = false" type="button" class="btn btn-secondary">
              Huy
            </button>
            <button @click="confirmPayment" type="button" class="btn btn-success">
              Xac nhan thanh toan
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Details Modal -->
    <div v-if="showDetails" class="modal d-block" style="background: rgba(0, 0, 0, 0.5)">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5>Chi tiet tien phat</h5>
            <button type="button" class="btn-close" @click="showDetails = false"></button>
          </div>
          <div v-if="selectedFine" class="modal-body">
            <dl class="row">
              <dt class="col-sm-4">Nguoi dung:</dt>
              <dd class="col-sm-8">{{ selectedFine.userName }}</dd>

              <dt class="col-sm-4">Email:</dt>
              <dd class="col-sm-8">{{ selectedFine.email }}</dd>

              <dt class="col-sm-4">So tien phat:</dt>
              <dd class="col-sm-8">
                <strong>{{ selectedFine.amount.toLocaleString() }} VND</strong>
              </dd>

              <dt class="col-sm-4">Ly do:</dt>
              <dd class="col-sm-8">{{ selectedFine.reason }}</dd>

              <dt class="col-sm-4">Sach:</dt>
              <dd class="col-sm-8">{{ selectedFine.bookTitle }}</dd>

              <dt class="col-sm-4">Ngay lap:</dt>
              <dd class="col-sm-8">{{ selectedFine.issuedDate }}</dd>

              <dt class="col-sm-4">Han tra:</dt>
              <dd class="col-sm-8">{{ selectedFine.dueDate }}</dd>

              <dt class="col-sm-4">Trang thai:</dt>
              <dd class="col-sm-8">
                <span
                  :class="[
                    'badge',
                    selectedFine.status === 'Da thanh toan' ? 'bg-success' : 'bg-danger',
                  ]"
                >
                  {{ selectedFine.status }}
                </span>
              </dd>
            </dl>
          </div>
          <div class="modal-footer">
            <button @click="showDetails = false" type="button" class="btn btn-primary">Dong</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const searchQuery = ref('')
const statusFilter = ref('')
const showPaymentForm = ref(false)
const showDetails = ref(false)
const selectedFine = ref(null)

const finesList = ref([
  {
    id: 1,
    userName: 'Nguyen Van A',
    email: 'student@school.edu.vn',
    bookTitle: 'Design Patterns',
    reason: 'Qua han 14 days',
    amount: 140000,
    issuedDate: '2024-01-16',
    dueDate: '2024-02-15',
    status: 'Chua thanh toan',
  },
  {
    id: 2,
    userName: 'Pham Thu Ha',
    email: 'ha@school.edu.vn',
    bookTitle: 'Clean Code',
    reason: 'Book damaged',
    amount: 250000,
    issuedDate: '2024-01-18',
    dueDate: '2024-02-10',
    status: 'Chua thanh toan',
  },
  {
    id: 3,
    userName: 'Le Minh Duc',
    email: 'duc@school.edu.vn',
    bookTitle: 'Refactoring',
    reason: 'Late return 5 days',
    amount: 50000,
    issuedDate: '2024-01-20',
    dueDate: '2024-02-05',
    status: 'Partial',
  },
  {
    id: 4,
    userName: 'Tran Kim Loan',
    email: 'loan@school.edu.vn',
    bookTitle: 'Vue.js Up and Running',
    reason: 'Qua han 7 days',
    amount: 70000,
    issuedDate: '2024-01-22',
    dueDate: '2024-02-20',
    status: 'Da thanh toan',
  },
])

const paymentForm = ref({
  method: '',
  amount: null,
  reference: '',
  notes: '',
})

const filteredFineList = computed(() => {
  return finesList.value.filter((fine) => {
    const matchSearch =
      fine.userName.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      fine.email.toLowerCase().includes(searchQuery.value.toLowerCase())

    const matchStatus = !statusFilter.value || fine.status === statusFilter.value

    return matchSearch && matchStatus
  })
})

const totalUnpaidAmount = computed(() => {
  return finesList.value
    .filter((f) => f.status === 'Chua thanh toan' || f.status === 'Partial')
    .reduce((sum, f) => sum + f.amount, 0)
})

const unpaidFineCount = computed(() => {
  return finesList.value.filter((f) => f.status === 'Chua thanh toan' || f.status === 'Partial')
    .length
})

const collectedThisMonth = computed(() => {
  return finesList.value
    .filter((f) => f.status === 'Da thanh toan')
    .reduce((sum, f) => sum + f.amount, 0)
})

const openPaymentForm = (fine) => {
  selectedFine.value = fine
  paymentForm.value = {
    method: '',
    amount: fine.amount,
    reference: '',
    notes: '',
  }
  showPaymentForm.value = true
}

const confirmPayment = () => {
  if (!paymentForm.value.method) {
    alert('Please select payment method')
    return
  }

  const amount = paymentForm.value.amount || selectedFine.value.amount
  if (amount <= 0) {
    alert('Invalid payment amount')
    return
  }

  if (selectedFine.value) {
    if (amount >= selectedFine.value.amount) {
      selectedFine.value.status = 'Da thanh toan'
    } else {
      selectedFine.value.status = 'Partial'
    }

    alert(`✓ Payment of ${amount.toLocaleString()} VND confirmed`)
  }

  showPaymentForm.value = false
  paymentForm.value = {
    method: '',
    amount: null,
    reference: '',
    notes: '',
  }
}

const viewDetails = (fine) => {
  selectedFine.value = fine
  showDetails.value = true
}
</script>
