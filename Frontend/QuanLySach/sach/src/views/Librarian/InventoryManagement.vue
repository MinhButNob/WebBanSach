<template>
  <div class="container-fluid py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h1>Quan ly kho sach</h1>
      <button class="btn btn-primary" @click="showAddCopy = true">Them ban sao</button>
    </div>

    <!-- Inventory Summary -->
    <div class="row mb-4">
      <div class="col-12 col-md-3 mb-3">
        <div class="card bg-primary-subtle border-primary border-2">
          <div class="card-body text-center">
            <h6 class="card-subtitle text-muted mb-2">Tong so ban sao</h6>
            <h3 class="card-title mb-0">{{ totalCopies }}</h3>
          </div>
        </div>
      </div>
      <div class="col-12 col-md-3 mb-3">
        <div class="card bg-success-subtle border-success border-2">
          <div class="card-body text-center">
            <h6 class="card-subtitle text-muted mb-2">Con sach</h6>
            <h3 class="card-title mb-0">{{ availableCopies }}</h3>
          </div>
        </div>
      </div>
      <div class="col-12 col-md-3 mb-3">
        <div class="card bg-warning-subtle border-warning border-2">
          <div class="card-body text-center">
            <h6 class="card-subtitle text-muted mb-2">Da muon</h6>
            <h3 class="card-title mb-0">{{ borrowedCopies }}</h3>
          </div>
        </div>
      </div>
      <div class="col-12 col-md-3 mb-3">
        <div class="card bg-danger-subtle border-danger border-2">
          <div class="card-body text-center">
            <h6 class="card-subtitle text-muted mb-2">Hai hong</h6>
            <h3 class="card-title mb-0">{{ damagedCopies }}</h3>
          </div>
        </div>
      </div>
    </div>

    <!-- Tim kiem -->
    <div class="mb-3">
      <input
        v-model="searchQuery"
        type="text"
        class="form-control"
        placeholder="Tim kiem by book title or copy ID..."
      />
    </div>

    <!-- Inventory Table -->
    <div class="card">
      <div class="table-responsive">
        <table class="table table-hover mb-0">
          <thead class="table-light">
            <tr>
              <th>So hieu ban sao</th>
              <th>Tieu de sach</th>
              <th>Vi tri ke</th>
              <th>Tinh trang</th>
              <th>Trang thai</th>
              <th>Ma vach</th>
              <th>Thao tac</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="copy in filteredCopies" :key="copy.id">
              <td>
                <code>{{ copy.copyId }}</code>
              </td>
              <td>{{ copy.bookTitle }}</td>
              <td>{{ copy.location }}</td>
              <td>
                <span
                  :class="[
                    'badge',
                    copy.condition === 'Good'
                      ? 'bg-success'
                      : copy.condition === 'Fair'
                        ? 'bg-warning'
                        : 'bg-danger',
                  ]"
                >
                  {{ copy.condition }}
                </span>
              </td>
              <td>
                <span
                  :class="[
                    'badge',
                    copy.status === 'Con sach'
                      ? 'bg-success'
                      : copy.status === 'Muoned'
                        ? 'bg-info'
                        : 'bg-secondary',
                  ]"
                >
                  {{ copy.status }}
                </span>
              </td>
              <td>
                <code>{{ copy.barcode }}</code>
              </td>
              <td>
                <button class="btn btn-sm btn-info me-2" @click="editCopy(copy)">Sua</button>
                <button class="btn btn-sm btn-danger" @click="deleteCopy(copy.id)">Xoa</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Add/Sua Copy Modal -->
    <div v-if="showAddCopy" class="modal d-block" style="background: rgba(0, 0, 0, 0.5)">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5>{{ editingCopy ? 'Sua ban sao' : 'Them ban sao moi' }}</h5>
            <button type="button" class="btn-close" @click="showAddCopy = false"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label class="form-label">Tieu de sach <span class="text-danger">*</span></label>
              <select v-model="copyFormData.bookTitle" class="form-select">
                <option value="">Chon sach...</option>
                <option v-for="book in bookTitles" :key="book" :value="book">{{ book }}</option>
              </select>
            </div>
            <div class="mb-3">
              <label class="form-label">Vi tri ke</label>
              <input
                v-model="copyFormData.location"
                type="text"
                class="form-control"
                placeholder="e.g., A1-12"
              />
            </div>
            <div class="mb-3">
              <label class="form-label">Tinh trang</label>
              <select v-model="copyFormData.condition" class="form-select">
                <option value="Good">Tot</option>
                <option value="Fair">Trung binh</option>
                <option value="Damaged">Hai hong</option>
              </select>
            </div>
            <div class="mb-3">
              <label class="form-label">Trang thai</label>
              <select v-model="copyFormData.status" class="form-select">
                <option value="Con sach">Con sach</option>
                <option value="Muoned">Da muon</option>
                <option value="Lost">That lac</option>
              </select>
            </div>
            <div class="mb-3">
              <label class="form-label">Ma vach</label>
              <input v-model="copyFormData.barcode" type="text" class="form-control" />
            </div>
          </div>
          <div class="modal-footer">
            <button @click="showAddCopy = false" type="button" class="btn btn-secondary">
              Huy
            </button>
            <button @click="saveCopy" type="button" class="btn btn-primary">
              {{ editingCopy ? 'Cap nhat' : 'Them moi' }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { books } from '@/data/mockData'

const searchQuery = ref('')
const showAddCopy = ref(false)
const editingCopy = ref(null)

const copiesList = ref([
  {
    id: 1,
    copyId: 'COPY-001',
    bookTitle: 'Clean Code',
    location: 'A1-12',
    condition: 'Good',
    status: 'Con sach',
    barcode: '1001',
  },
  {
    id: 2,
    copyId: 'COPY-002',
    bookTitle: 'Clean Code',
    location: 'A1-13',
    condition: 'Good',
    status: 'Muoned',
    barcode: '1002',
  },
  {
    id: 3,
    copyId: 'COPY-003',
    bookTitle: 'Design Patterns',
    location: 'A2-05',
    condition: 'Fair',
    status: 'Con sach',
    barcode: '1003',
  },
  {
    id: 4,
    copyId: 'COPY-004',
    bookTitle: 'Refactoring',
    location: 'A2-15',
    condition: 'Good',
    status: 'Muoned',
    barcode: '1004',
  },
  {
    id: 5,
    copyId: 'COPY-005',
    bookTitle: 'The Pragmatic Programmer',
    location: 'B1-03',
    condition: 'Damaged',
    status: 'Lost',
    barcode: '1005',
  },
])

const copyFormData = ref({
  bookTitle: '',
  location: '',
  condition: 'Good',
  status: 'Con sach',
  barcode: '',
})

const bookTitles = computed(() => books.map((b) => b.title))

const filteredCopies = computed(() => {
  return copiesList.value.filter(
    (copy) =>
      copy.bookTitle.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      copy.copyId.includes(searchQuery.value),
  )
})

const totalCopies = computed(() => copiesList.value.length)
const availableCopies = computed(
  () => copiesList.value.filter((c) => c.status === 'Con sach').length,
)
const borrowedCopies = computed(() => copiesList.value.filter((c) => c.status === 'Muoned').length)
const damagedCopies = computed(
  () => copiesList.value.filter((c) => c.condition === 'Damaged').length,
)

const editCopy = (copy) => {
  editingCopy.value = copy
  copyFormData.value = { ...copy }
  showAddCopy.value = true
}

const saveCopy = () => {
  if (!copyFormData.value.bookTitle) {
    alert('Please select a book')
    return
  }

  if (editingCopy.value) {
    const index = copiesList.value.findIndex((c) => c.id === editingCopy.value.id)
    if (index !== -1) {
      copiesList.value[index] = { ...copiesList.value[index], ...copyFormData.value }
    }
  } else {
    const newCopy = {
      id: Math.max(...copiesList.value.map((c) => c.id), 0) + 1,
      copyId: `COPY-${String(Math.max(...copiesList.value.map((c) => parseInt(c.copyId.split('-')[1])), 0) + 1).padStart(3, '0')}`,
      ...copyFormData.value,
    }
    copiesList.value.push(newCopy)
  }

  showAddCopy.value = false
  editingCopy.value = null
  copyFormData.value = {
    bookTitle: '',
    location: '',
    condition: 'Good',
    status: 'Con sach',
    barcode: '',
  }
}

const deleteCopy = (id) => {
  if (confirm('Ban chac chan muon xoa ban sao nay?')) {
    copiesList.value = copiesList.value.filter((c) => c.id !== id)
  }
}
</script>
