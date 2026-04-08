<template>
  <div class="container-fluid py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h1>Quan ly sach</h1>
      <button class="btn btn-primary" @click="showAddForm = true">
        <FaIcon name="fa-plus" class="me-2" />
        Them sach moi
      </button>
    </div>

    <!-- Tim kiem & Filter -->
    <div class="row mb-4">
      <div class="col-12 col-md-8">
        <input
          v-model="searchQuery"
          type="text"
          class="form-control"
          placeholder="Tim kiem theo tieu de, ISBN, tac gia..."
        />
      </div>
      <div class="col-12 col-md-4">
        <select v-model="filterCategory" class="form-select">
          <option value="">Tat ca the loai</option>
          <option v-for="cat in categories" :key="cat" :value="cat">{{ cat }}</option>
        </select>
      </div>
    </div>

    <!-- Sach Table -->
    <div class="card">
      <div class="table-responsive">
        <table class="table table-hover mb-0">
          <thead class="table-light">
            <tr>
              <th>Tieu de</th>
              <th>ISBN</th>
              <th>Tac gia</th>
              <th>The loai</th>
              <th>Tong so ban sao</th>
              <th>Con sach</th>
              <th>Thao tac</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="book in filteredSach" :key="book.id">
              <td>
                <strong>{{ book.title }}</strong>
              </td>
              <td>
                <code>{{ book.isbn }}</code>
              </td>
              <td>{{ book.authors.join(', ') }}</td>
              <td>{{ book.categories.join(', ') }}</td>
              <td>{{ book.totalCopies }}</td>
              <td>
                <span :class="['badge', book.availableCopies > 0 ? 'bg-success' : 'bg-danger']">
                  {{ book.availableCopies }}
                </span>
              </td>
              <td>
                <button class="btn btn-sm btn-warning me-2" @click="editBook(book)">
                  <FaIcon name="fa-pen" />
                </button>
                <button class="btn btn-sm btn-danger" @click="deleteBook(book.id)">
                  <FaIcon name="fa-trash" />
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Add/Sua Form Modal -->
    <div v-if="showAddForm" class="modal d-block" style="background: rgba(0, 0, 0, 0.5)">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5>{{ editingBook ? 'Sua thong tin sach' : 'Them sach moi' }}</h5>
            <button type="button" class="btn-close" @click="showAddForm = false"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label class="form-label">Tieu de <span class="text-danger">*</span></label>
              <input v-model="formData.title" type="text" class="form-control" />
            </div>
            <div class="mb-3">
              <label class="form-label">ISBN <span class="text-danger">*</span></label>
              <input v-model="formData.isbn" type="text" class="form-control" />
            </div>
            <div class="mb-3">
              <label class="form-label">Tac gia <span class="text-danger">*</span></label>
              <input
                v-model="formData.authorsInput"
                type="text"
                class="form-control"
                placeholder="Cach nhau bang dau phay"
              />
            </div>
            <div class="mb-3">
              <label class="form-label">The loai</label>
              <input
                v-model="formData.categoriesInput"
                type="text"
                class="form-control"
                placeholder="Cach nhau bang dau phay"
              />
            </div>
            <div class="row">
              <div class="col-6">
                <div class="mb-3">
                  <label class="form-label">Tong so ban sao</label>
                  <input v-model.number="formData.totalCopies" type="number" class="form-control" />
                </div>
              </div>
              <div class="col-6">
                <div class="mb-3">
                  <label class="form-label">So ban sao con</label>
                  <input
                    v-model.number="formData.availableCopies"
                    type="number"
                    class="form-control"
                  />
                </div>
              </div>
            </div>
            <div class="mb-3">
              <label class="form-label">Nha xuat ban</label>
              <input v-model="formData.publisher" type="text" class="form-control" />
            </div>
            <div class="mb-3">
              <label class="form-label">Nam xuat ban</label>
              <input v-model.number="formData.publishedYear" type="number" class="form-control" />
            </div>
          </div>
          <div class="modal-footer">
            <button @click="showAddForm = false" type="button" class="btn btn-secondary">
              Huy
            </button>
            <button @click="saveBook" type="button" class="btn btn-primary">
              {{ editingBook ? 'Cap nhat' : 'Them moi' }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import FaIcon from '@/components/common/FaIcon.vue'
import { books, categories } from '@/data/mockData'

const searchQuery = ref('')
const filterCategory = ref('')
const showAddForm = ref(false)
const editingBook = ref(null)

const bookList = ref([...books])

const formData = ref({
  title: '',
  isbn: '',
  authorsInput: '',
  categoriesInput: '',
  totalCopies: 0,
  availableCopies: 0,
  publisher: '',
  publishedYear: new Date().getFullYear(),
})

const filteredBooks = computed(() => {
  return bookList.value.filter((book) => {
    const matchSearch =
      book.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      book.isbn.includes(searchQuery.value) ||
      book.authors.some((a) => a.toLowerCase().includes(searchQuery.value.toLowerCase()))

    const matchCategory = !filterCategory.value || book.categories.includes(filterCategory.value)

    return matchSearch && matchCategory
  })
})

const editBook = (book) => {
  editingBook.value = book
  formData.value = {
    title: book.title,
    isbn: book.isbn,
    authorsInput: book.authors.join(', '),
    categoriesInput: book.categories.join(', '),
    totalCopies: book.totalCopies,
    availableCopies: book.availableCopies,
    publisher: book.publisher,
    publishedYear: book.publishedYear,
  }
  showAddForm.value = true
}

const saveBook = () => {
  if (!formData.value.title || !formData.value.isbn) {
    alert('Please fill required fields')
    return
  }

  const bookData = {
    title: formData.value.title,
    isbn: formData.value.isbn,
    authors: formData.value.authorsInput.split(',').map((a) => a.trim()),
    categories: formData.value.categoriesInput
      .split(',')
      .map((c) => c.trim())
      .filter((c) => c),
    totalCopies: formData.value.totalCopies,
    availableCopies: formData.value.availableCopies,
    publisher: formData.value.publisher,
    publishedYear: formData.value.publishedYear,
    borrowedCount: editingBook.value?.borrowedCount || 0,
    language: 'English',
    edition: '1st',
    description: '',
  }

  if (editingBook.value) {
    const index = bookList.value.findIndex((b) => b.id === editingBook.value.id)
    if (index !== -1) {
      bookList.value[index] = { ...bookList.value[index], ...bookData }
    }
  } else {
    const newBook = {
      id: Math.max(...bookList.value.map((b) => b.id), 0) + 1,
      ...bookData,
    }
    bookList.value.push(newBook)
  }

  showAddForm.value = false
  editingBook.value = null
  formData.value = {
    title: '',
    isbn: '',
    authorsInput: '',
    categoriesInput: '',
    totalCopies: 0,
    availableCopies: 0,
    publisher: '',
    publishedYear: new Date().getFullYear(),
  }
}

const deleteBook = (id) => {
  if (confirm('Ban chac chan muon xoa sach nay?')) {
    bookList.value = bookList.value.filter((b) => b.id !== id)
  }
}
</script>
