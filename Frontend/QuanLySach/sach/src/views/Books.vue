<script setup>
import { computed, ref } from 'vue'
import BookCard from '../components/books/BookCard.vue'
import Pagination from '../components/common/Pagination.vue'
import { books, categories } from '../data/mockData'
import { authState } from '../stores/auth'
import { reservationsSeed } from '../data/featureData'

const searchQuery = ref('')
const categoryFilter = ref('Tat ca the loai')
const sortBy = ref('title')
const currentPage = ref(1)
const pageSize = 4
const reservationMessage = ref('')
const reservations = ref([...reservationsSeed])

const resetPage = () => {
  currentPage.value = 1
}

const filteredBooks = computed(() => {
  const query = searchQuery.value.toLowerCase()
  const filtered = books.filter((book) => {
    const matchesQuery =
      book.title.toLowerCase().includes(query) ||
      book.authors.some((author) => author.toLowerCase().includes(query))

    const matchesCategory =
      categoryFilter.value === 'Tat ca the loai' || book.categories.includes(categoryFilter.value)

    return matchesQuery && matchesCategory
  })

  return [...filtered].sort((left, right) => {
    if (sortBy.value === 'popularity') {
      return right.borrowedCount - left.borrowedCount
    }

    if (sortBy.value === 'newest') {
      return right.publishedYear - left.publishedYear
    }

    return left.title.localeCompare(right.title)
  })
})

const totalPages = computed(() => Math.max(1, Math.ceil(filteredBooks.value.length / pageSize)))

const paginatedBooks = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return filteredBooks.value.slice(start, start + pageSize)
})

const updatePage = (page) => {
  currentPage.value = page
}

const reserveBook = (book) => {
  const existing = reservations.value.find(
    (item) =>
      item.bookId === book.id &&
      item.userId === authState.currentUser.id &&
      item.status === 'PENDING',
  )

  if (existing) {
    reservationMessage.value = `Ban da dat truoc sach "${book.title}" truoc do.`
    return
  }

  const today = new Date()
  const expiry = new Date(today)
  expiry.setDate(today.getDate() + 3)

  const nextId = Math.max(...reservations.value.map((item) => item.id), 0) + 1
  reservations.value.unshift({
    id: nextId,
    userId: authState.currentUser.id,
    userName: authState.currentUser.name,
    bookId: book.id,
    bookTitle: book.title,
    reservationDate: today.toISOString().slice(0, 10),
    expiryDate: expiry.toISOString().slice(0, 10),
    status: 'PENDING',
  })

  reservationMessage.value = `Dat truoc thanh cong cho sach "${book.title}".`
}
</script>

<template>
  <div>
    <div class="d-flex justify-content-between align-items-start flex-wrap gap-3 mb-4">
      <div>
        <h1 class="h3 fw-bold mb-1">Sach</h1>
        <p class="text-muted mb-0">Tim kiem va duyet bo suu tap sach trong thu vien.</p>
      </div>
      <span class="badge text-bg-secondary"> {{ filteredBooks.length }} sach tim thay </span>
    </div>

    <div class="card mb-4">
      <div class="card-body">
        <div class="row g-3 align-items-end">
          <div class="col-12 col-lg-5">
            <label class="form-label">Tim kiem</label>
            <div class="input-group">
              <span class="input-group-text"><FaIcon name="fa-magnifying-glass" /></span>
              <input
                v-model="searchQuery"
                type="text"
                class="form-control"
                placeholder="Tim theo tu khoa, tieu de hoac tac gia"
                @input="resetPage"
              />
            </div>
          </div>

          <div class="col-12 col-md-6 col-lg-3">
            <label class="form-label">The loai</label>
            <select v-model="categoryFilter" class="form-select" @change="resetPage">
              <option v-for="category in categories" :key="category" :value="category">
                {{ category }}
              </option>
            </select>
          </div>

          <div class="col-12 col-md-6 col-lg-3">
            <label class="form-label">Sap xep theo</label>
            <select v-model="sortBy" class="form-select" @change="resetPage">
              <option value="title">Tieu de</option>
              <option value="popularity">Pho bien</option>
              <option value="newest">Moi nhat</option>
            </select>
          </div>
        </div>
      </div>
    </div>

    <div v-if="reservationMessage" class="alert alert-success">
      {{ reservationMessage }}
    </div>

    <div v-if="paginatedBooks.length" class="row g-3">
      <div v-for="book in paginatedBooks" :key="book.id" class="col-12 col-md-6 col-xl-3">
        <BookCard :book="book" @reserve="reserveBook" />
      </div>
    </div>

    <div v-else class="alert alert-info">Khong co sach phu hop bo loc hien tai.</div>

    <div class="mt-4">
      <Pagination :current-page="currentPage" :total-pages="totalPages" @change="updatePage" />
    </div>
  </div>
</template>
