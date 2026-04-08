<script setup>
import { computed, ref } from 'vue'
import DataTable from '@/components/common/DataTable.vue'
import { authState } from '@/stores/auth'
import { fineList } from '@/data/mockData'
import { paymentMethods, paymentsSeed } from '@/data/featureData'

const paymentMethod = ref(paymentMethods[0])
const selectedFineId = ref('')
const payments = ref([...paymentsSeed])

const myUnpaidFines = computed(() => {
  return fineList.filter((fine, index) => {
    const assumedOwner = (index + 1) % 2 === 0 ? authState.currentUser.id : authState.currentUser.id
    return (
      assumedOwner === authState.currentUser.id &&
      fine.status !== 'Da thanh toan' &&
      fine.amount > 0
    )
  })
})

const myPayments = computed(() =>
  payments.value.filter((item) => item.userName === authState.currentUser.name),
)

const columns = [
  { key: 'fineId', label: 'Fine ID' },
  { key: 'amount', label: 'So tien' },
  { key: 'paymentDate', label: 'Ngay thanh toan' },
  { key: 'method', label: 'Phuong thuc' },
  { key: 'status', label: 'Trang thai' },
]

const submitPayment = () => {
  const fine = myUnpaidFines.value.find((item) => item.id === Number(selectedFineId.value))
  if (!fine) return

  const nextId = Math.max(...payments.value.map((item) => item.id), 0) + 1
  payments.value.unshift({
    id: nextId,
    fineId: fine.id,
    userName: authState.currentUser.name,
    amount: fine.amount,
    paymentDate: new Date().toISOString().slice(0, 10),
    method: paymentMethod.value,
    status: 'PENDING',
  })

  selectedFineId.value = ''
}
</script>

<template>
  <div class="container-fluid py-4">
    <h1 class="h4 mb-3">Thanh toan tien phat</h1>

    <div class="card mb-4">
      <div class="card-body">
        <div class="row g-3 align-items-end">
          <div class="col-12 col-md-5">
            <label class="form-label">Khoan phat</label>
            <select v-model="selectedFineId" class="form-select">
              <option value="">Chon khoan phat</option>
              <option v-for="fine in myUnpaidFines" :key="fine.id" :value="fine.id">
                #{{ fine.id }} - {{ fine.bookTitle }} - {{ fine.amount.toLocaleString() }} VND
              </option>
            </select>
          </div>
          <div class="col-12 col-md-4">
            <label class="form-label">Phuong thuc</label>
            <select v-model="paymentMethod" class="form-select">
              <option v-for="method in paymentMethods" :key="method" :value="method">
                {{ method }}
              </option>
            </select>
          </div>
          <div class="col-12 col-md-3 d-grid">
            <button class="btn btn-primary" :disabled="!selectedFineId" @click="submitPayment">
              Tao giao dich
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="card">
      <div class="card-body p-0">
        <DataTable :columns="columns" :rows="myPayments" empty-text="Chua co giao dich nao">
          <template #cell-amount="{ row }"> {{ row.amount.toLocaleString() }} VND </template>
          <template #cell-status="{ row }">
            <span
              :class="
                row.status === 'CONFIRMED' ? 'badge text-bg-success' : 'badge text-bg-warning'
              "
            >
              {{ row.status }}
            </span>
          </template>
        </DataTable>
      </div>
    </div>
  </div>
</template>
