<script setup>
import { computed, ref } from 'vue'
import DataTable from '@/components/common/DataTable.vue'
import { reservationStatuses, reservationsSeed } from '@/data/featureData'

const filterStatus = ref('')
const reservations = ref([...reservationsSeed])

const columns = [
  { key: 'userName', label: 'Nguoi dung' },
  { key: 'bookTitle', label: 'Sach' },
  { key: 'reservationDate', label: 'Ngay dat' },
  { key: 'expiryDate', label: 'Het han' },
  { key: 'status', label: 'Trang thai' },
]

const filteredRows = computed(() => {
  if (!filterStatus.value) return reservations.value
  return reservations.value.filter((item) => item.status === filterStatus.value)
})

const updateStatus = (id, status) => {
  reservations.value = reservations.value.map((item) =>
    item.id === id ? { ...item, status } : item,
  )
}
</script>

<template>
  <div class="container-fluid py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h1 class="h4 mb-0">Quan ly dat truoc</h1>
      <select v-model="filterStatus" class="form-select w-auto">
        <option value="">Tat ca trang thai</option>
        <option v-for="status in reservationStatuses" :key="status" :value="status">
          {{ status }}
        </option>
      </select>
    </div>

    <div class="card">
      <div class="card-body p-0">
        <DataTable :columns="columns" :rows="filteredRows" empty-text="Chua co dat truoc nao">
          <template #cell-status="{ row }">
            <span class="badge text-bg-info">{{ row.status }}</span>
          </template>
          <template #actions="{ row }">
            <select
              class="form-select form-select-sm"
              :value="row.status"
              @change="updateStatus(row.id, $event.target.value)"
            >
              <option v-for="status in reservationStatuses" :key="status" :value="status">
                {{ status }}
              </option>
            </select>
          </template>
        </DataTable>
      </div>
    </div>
  </div>
</template>
