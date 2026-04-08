<script setup>
import { computed, ref } from 'vue'
import DataTable from '@/components/common/DataTable.vue'
import { books } from '@/data/mockData'
import { authState } from '@/stores/auth'
import { reservationsSeed } from '@/data/featureData'

const reservations = ref([...reservationsSeed])
const selectedBookId = ref('')

const unavailableBooks = computed(() => books.filter((book) => book.availableCopies === 0))
const myReservations = computed(() =>
  reservations.value.filter((item) => item.userId === authState.currentUser.id),
)

const columns = [
  { key: 'bookTitle', label: 'Sach' },
  { key: 'reservationDate', label: 'Ngay dat' },
  { key: 'expiryDate', label: 'Het han' },
  { key: 'status', label: 'Trang thai' },
]

const reserveBook = () => {
  const selectedBook = books.find((book) => book.id === Number(selectedBookId.value))
  if (!selectedBook) return

  const today = new Date()
  const expiry = new Date(today)
  expiry.setDate(today.getDate() + 3)

  const nextId = Math.max(...reservations.value.map((item) => item.id), 0) + 1
  reservations.value.unshift({
    id: nextId,
    userId: authState.currentUser.id,
    userName: authState.currentUser.name,
    bookId: selectedBook.id,
    bookTitle: selectedBook.title,
    reservationDate: today.toISOString().slice(0, 10),
    expiryDate: expiry.toISOString().slice(0, 10),
    status: 'PENDING',
  })

  selectedBookId.value = ''
}

const cancelReservation = (id) => {
  reservations.value = reservations.value.map((item) =>
    item.id === id ? { ...item, status: 'CANCELLED' } : item,
  )
}
</script>

<template>
  <div class="container-fluid py-4">
    <h1 class="h4 mb-3">Dat truoc sach</h1>

    <div class="card mb-4">
      <div class="card-body">
        <div class="row g-3 align-items-end">
          <div class="col-12 col-md-8">
            <label class="form-label">Sach het ban de dat truoc</label>
            <select v-model="selectedBookId" class="form-select">
              <option value="">Chon sach</option>
              <option v-for="book in unavailableBooks" :key="book.id" :value="book.id">
                {{ book.title }}
              </option>
            </select>
          </div>
          <div class="col-12 col-md-4 d-grid">
            <button class="btn btn-primary" :disabled="!selectedBookId" @click="reserveBook">
              Dat truoc
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="card">
      <div class="card-body p-0">
        <DataTable :columns="columns" :rows="myReservations" empty-text="Ban chua co dat truoc nao">
          <template #cell-status="{ row }">
            <span class="badge text-bg-info">{{ row.status }}</span>
          </template>
          <template #actions="{ row }">
            <button
              class="btn btn-sm btn-outline-danger"
              :disabled="row.status !== 'PENDING'"
              @click="cancelReservation(row.id)"
            >
              Huy
            </button>
          </template>
        </DataTable>
      </div>
    </div>
  </div>
</template>
