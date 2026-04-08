<script setup>
import { computed, ref } from 'vue'
import BaseModal from '@/components/common/BaseModal.vue'
import DataTable from '@/components/common/DataTable.vue'
import EntityForm from '@/components/common/EntityForm.vue'
import { bookCopiesSeed, copyStatuses } from '@/data/featureData'
import { books } from '@/data/mockData'

const statusFilter = ref('')
const copies = ref([...bookCopiesSeed])
const showModal = ref(false)
const editingCopyId = ref(null)
const form = ref({
  bookId: books[0]?.id ?? 1,
  barcode: '',
  status: 'AVAILABLE',
  shelf: 'S-1',
})

const columns = [
  { key: 'bookTitle', label: 'Sach' },
  { key: 'barcode', label: 'Barcode' },
  { key: 'status', label: 'Trang thai' },
  { key: 'shelf', label: 'Ke' },
]

const fields = [
  {
    key: 'bookId',
    label: 'Sach',
    type: 'select',
    options: books.map((book) => ({ value: book.id, label: book.title })),
    col: 'col-12 col-md-6',
  },
  { key: 'barcode', label: 'Barcode', col: 'col-12 col-md-6' },
  {
    key: 'status',
    label: 'Trang thai',
    type: 'select',
    options: copyStatuses.map((status) => ({ value: status, label: status })),
    col: 'col-12 col-md-6',
  },
  { key: 'shelf', label: 'Ke', col: 'col-12 col-md-6' },
]

const rows = computed(() => {
  if (!statusFilter.value) return copies.value
  return copies.value.filter((item) => item.status === statusFilter.value)
})

const openCreate = () => {
  editingCopyId.value = null
  form.value = { bookId: books[0]?.id ?? 1, barcode: '', status: 'AVAILABLE', shelf: 'S-1' }
  showModal.value = true
}

const openEdit = (row) => {
  editingCopyId.value = row.id
  form.value = { bookId: row.bookId, barcode: row.barcode, status: row.status, shelf: row.shelf }
  showModal.value = true
}

const saveCopy = () => {
  const selectedBook = books.find((book) => book.id === Number(form.value.bookId))

  if (editingCopyId.value) {
    copies.value = copies.value.map((item) =>
      item.id === editingCopyId.value
        ? {
            ...item,
            ...form.value,
            bookId: Number(form.value.bookId),
            bookTitle: selectedBook?.title ?? item.bookTitle,
          }
        : item,
    )
  } else {
    const nextId = Math.max(...copies.value.map((item) => item.id), 0) + 1
    copies.value.unshift({
      id: nextId,
      ...form.value,
      bookId: Number(form.value.bookId),
      bookTitle: selectedBook?.title ?? 'Unknown',
    })
  }

  showModal.value = false
}

const deleteCopy = (id) => {
  copies.value = copies.value.filter((item) => item.id !== id)
}
</script>

<template>
  <div class="container-fluid py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h1 class="h4 mb-0">Quan ly ban sao sach</h1>
      <div class="d-flex gap-2">
        <select v-model="statusFilter" class="form-select">
          <option value="">Tat ca trang thai</option>
          <option v-for="status in copyStatuses" :key="status" :value="status">{{ status }}</option>
        </select>
        <button class="btn btn-primary" @click="openCreate">Them ban sao</button>
      </div>
    </div>

    <div class="card">
      <div class="card-body p-0">
        <DataTable :columns="columns" :rows="rows" empty-text="Chua co ban sao nao">
          <template #cell-status="{ row }">
            <span
              :class="
                row.status === 'AVAILABLE' ? 'badge text-bg-success' : 'badge text-bg-secondary'
              "
            >
              {{ row.status }}
            </span>
          </template>
          <template #actions="{ row }">
            <div class="btn-group btn-group-sm">
              <button class="btn btn-outline-primary" @click="openEdit(row)">Sua</button>
              <button class="btn btn-outline-danger" @click="deleteCopy(row.id)">Xoa</button>
            </div>
          </template>
        </DataTable>
      </div>
    </div>

    <BaseModal
      :show="showModal"
      :title="editingCopyId ? 'Cap nhat ban sao' : 'Them ban sao'"
      @close="showModal = false"
    >
      <EntityForm v-model="form" :fields="fields" @submit="saveCopy" />
      <template #footer>
        <button class="btn btn-secondary" @click="showModal = false">Huy</button>
        <button class="btn btn-primary" @click="saveCopy">Luu</button>
      </template>
    </BaseModal>
  </div>
</template>
