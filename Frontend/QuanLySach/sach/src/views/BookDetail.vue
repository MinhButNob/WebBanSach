<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { getBookById } from '../data/mockData'

const route = useRoute()

const book = computed(() => getBookById(route.params.id))
</script>

<template>
  <div class="row g-4 align-items-start">
    <div class="col-12 col-lg-4">
      <div class="card">
        <div class="card-body text-center p-4">
          <div
            class="bg-light border d-flex align-items-center justify-content-center mx-auto mb-4"
            style="width: 200px; height: 280px"
          >
            <FaIcon name="fa-book-open" size="4x" class="text-secondary" />
          </div>
          <h1 class="h4 fw-bold mb-2">{{ book.title }}</h1>
          <p class="text-muted mb-3">ISBN: {{ book.isbn }}</p>
          <div class="d-flex flex-wrap justify-content-center gap-2">
            <span class="badge text-bg-light border">{{ book.language }}</span>
            <span class="badge text-bg-light border">{{ book.edition }}</span>
          </div>
        </div>
      </div>
    </div>

    <div class="col-12 col-lg-8">
      <div class="card mb-4">
        <div class="card-body">
          <div class="d-flex justify-content-between align-items-start flex-wrap gap-3 mb-3">
            <div>
              <h2 class="h3 fw-bold mb-1">{{ book.title }}</h2>
              <p class="text-muted mb-0">Tac gia: {{ book.authors.join(', ') }}</p>
            </div>
            <span
              :class="
                book.availableCopies > 0
                  ? 'badge text-bg-success fs-6'
                  : 'badge text-bg-secondary fs-6'
              "
            >
              {{ book.availableCopies > 0 ? 'Con sach' : 'Het sach' }}
            </span>
          </div>

          <div class="row g-3 mb-4">
            <div class="col-12 col-md-6">
              <div class="border rounded-3 p-3 h-100">
                <div class="text-muted small">Nha xuat ban</div>
                <div class="fw-semibold">{{ book.publisher }}</div>
              </div>
            </div>
            <div class="col-12 col-md-3">
              <div class="border rounded-3 p-3 h-100">
                <div class="text-muted small">Tong ban sao</div>
                <div class="fw-semibold">{{ book.totalCopies }}</div>
              </div>
            </div>
            <div class="col-12 col-md-3">
              <div class="border rounded-3 p-3 h-100">
                <div class="text-muted small">Ban sao con</div>
                <div class="fw-semibold">{{ book.availableCopies }}</div>
              </div>
            </div>
          </div>

          <div class="mb-4">
            <h3 class="h6 text-uppercase text-muted">The loai</h3>
            <div class="d-flex flex-wrap gap-2">
              <span
                v-for="category in book.categories"
                :key="category"
                class="badge text-bg-primary-subtle text-primary border border-primary-subtle"
              >
                {{ category }}
              </span>
            </div>
          </div>

          <div class="mb-4">
            <h3 class="h6 text-uppercase text-muted">Mo ta</h3>
            <p class="mb-0 text-muted">{{ book.description }}</p>
          </div>

          <div class="d-flex flex-wrap gap-2">
            <button v-if="book.availableCopies > 0" type="button" class="btn btn-primary">
              Muon
            </button>
            <button v-else type="button" class="btn btn-warning">Dat truoc</button>
            <RouterLink class="btn btn-outline-secondary" to="/books">
              Quay lai danh sach sach
            </RouterLink>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
