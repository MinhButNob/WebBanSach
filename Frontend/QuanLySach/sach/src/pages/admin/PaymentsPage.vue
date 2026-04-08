<script setup>
import { computed, ref } from 'vue'
import DataTable from '@/components/common/DataTable.vue'
import { paymentStatuses, paymentsSeed } from '@/data/featureData'

const statusFilter = ref('')
const payments = ref([...paymentsSeed])

const columns = [
  { key: 'fineId', label: 'Fine ID' },
  { key: 'userName', label: 'Nguoi dung' },
  { key: 'amount', label: 'So tien' },
  { key: 'paymentDate', label: 'Ngay thanh toan' },
  { key: 'method', label: 'Phuong thuc' },
  { key: 'status', label: 'Trang thai' },
]

const rows = computed(() => {
  if (!statusFilter.value) return payments.value
  return payments.value.filter((item) => item.status === statusFilter.value)
})

const confirmPayment = (id) => {
  payments.value = payments.value.map((item) =>
    item.id === id ? { ...item, status: 'CONFIRMED' } : item,
  )
}
</script>

<template>
  <div class="container-fluid py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h1 class="h4 mb-0">Quan ly thanh toan</h1>
      <select v-model="statusFilter" class="form-select w-auto">
        <option value="">Tat ca trang thai</option>
        <option v-for="status in paymentStatuses" :key="status" :value="status">
          {{ status }}
        </option>
      </select>
    </div>

    <div class="card">
      <div class="card-body p-0">
        <DataTable :columns="columns" :rows="rows" empty-text="Khong co lich su thanh toan">
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
          <template #actions="{ row }">
            <button
              class="btn btn-sm btn-primary"
              :disabled="row.status === 'CONFIRMED'"
              @click="confirmPayment(row.id)"
            >
              Xac nhan
            </button>
          </template>
        </DataTable>
      </div>
    </div>
  </div>
</template>
