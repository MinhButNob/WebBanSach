<script setup>
defineProps({
  book: {
    type: Object,
    required: true,
  },
})

const emit = defineEmits(['reserve'])
</script>

<template>
  <div class="card h-100">
    <div class="card-body d-flex flex-column">
      <div
        class="bg-light border d-flex align-items-center justify-content-center mb-3"
        style="height: 140px"
      >
        <FaIcon name="fa-book" size="3x" class="text-secondary" />
      </div>

      <div class="d-flex justify-content-between align-items-start gap-2 mb-2">
        <div>
          <h5 class="card-title mb-1">{{ book.title }}</h5>
          <p class="text-muted mb-0">{{ book.authors.join(', ') }}</p>
        </div>
        <span
          :class="book.availableCopies > 0 ? 'badge text-bg-success' : 'badge text-bg-secondary'"
        >
          {{ book.availableCopies > 0 ? `Con ${book.availableCopies} cuon` : 'Het sach' }}
        </span>
      </div>

      <div class="mb-3">
        <span
          v-for="category in book.categories"
          :key="category"
          class="badge text-bg-light border me-1 mb-1"
        >
          {{ category }}
        </span>
      </div>

      <p class="text-muted small flex-grow-1">
        {{ book.description }}
      </p>

      <div class="d-flex flex-wrap gap-2 mt-auto">
        <RouterLink class="btn btn-outline-primary btn-sm" :to="`/books/${book.id}`">
          Xem chi tiet
        </RouterLink>
        <button v-if="book.availableCopies > 0" type="button" class="btn btn-primary btn-sm">
          Muon
        </button>
        <button v-else type="button" class="btn btn-warning btn-sm" @click="emit('reserve', book)">
          Dat truoc
        </button>
      </div>
    </div>
  </div>
</template>
