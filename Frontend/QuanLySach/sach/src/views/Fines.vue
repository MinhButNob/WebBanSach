<script setup>
import { computed, ref } from 'vue'
import { fineList } from '../data/mockData'

const showPaymentModal = ref(false)
const selectedFine = ref(fineList[0])
const paymentMethod = ref('Cash')

const unpaidCount = computed(
  () => fineList.filter((fine) => fine.status === 'Chua thanh toan').length,
)

const openPaymentModal = (fine) => {
  selectedFine.value = fine
  showPaymentModal.value = true
}

const closePaymentModal = () => {
  showPaymentModal.value = false
}
</script>

<template>
  <div>
    <div class="d-flex justify-content-between align-items-start flex-wrap gap-3 mb-4">
      <div>
        <h1 class="h3 fw-bold mb-1">Tien phat</h1>
        <p class="text-muted mb-0">Theo doi cac khoan phat da thanh toan va chua thanh toan.</p>
      </div>
      <span class="badge text-bg-warning text-dark px-3 py-2">
        {{ unpaidCount }} chua thanh toan
      </span>
    </div>

    <div class="card border-0 shadow-sm">
      <div class="table-responsive">
        <table class="table align-middle mb-0">
          <thead class="table-light">
            <tr>
              <th>Ten sach</th>
              <th>Ly do phat</th>
              <th>So tien</th>
              <th>Ngay lap</th>
              <th>Han tra</th>
              <th>Trang thai</th>
              <th class="text-end">Thao tac</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="fine in fineList" :key="fine.id">
              <td class="fw-semibold">{{ fine.bookTitle }}</td>
              <td>{{ fine.reason }}</td>
              <td>{{ fine.amount.toLocaleString() }} VND</td>
              <td>{{ fine.issuedDate }}</td>
              <td>{{ fine.dueDate }}</td>
              <td>
                <span
                  :class="
                    fine.status === 'Chua thanh toan'
                      ? 'badge text-bg-danger'
                      : 'badge text-bg-success'
                  "
                >
                  {{ fine.status }}
                </span>
              </td>
              <td class="text-end">
                <button
                  v-if="fine.status === 'Chua thanh toan'"
                  type="button"
                  class="btn btn-primary btn-sm"
                  @click="openPaymentModal(fine)"
                >
                  Thanh toan ngay
                </button>
                <span v-else class="text-muted small">Da xong</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-if="showPaymentModal" class="modal d-block" tabindex="-1" role="dialog">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Thanh toan</h5>
            <button type="button" class="btn-close" @click="closePaymentModal"></button>
          </div>
          <div class="modal-body">
            <p class="mb-2 fw-semibold">{{ selectedFine.bookTitle }}</p>
            <div class="mb-3">
              <label class="form-label">So tien</label>
              <input
                :value="selectedFine.amount.toLocaleString() + ' VND'"
                type="text"
                class="form-control"
                disabled
              />
            </div>
            <div>
              <label class="form-label">Phuong thuc thanh toan</label>
              <select v-model="paymentMethod" class="form-select">
                <option>Tien mat</option>
                <option>The</option>
                <option>Truc tuyen</option>
              </select>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-outline-secondary" @click="closePaymentModal">
              Huy
            </button>
            <button type="button" class="btn btn-primary" @click="closePaymentModal">
              Xac nhan thanh toan
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
