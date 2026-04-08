<script setup>
import { computed } from 'vue'

const props = defineProps({
  currentPage: {
    type: Number,
    default: 1,
  },
  totalPages: {
    type: Number,
    default: 1,
  },
})

const emit = defineEmits(['change'])

const pages = computed(() => Array.from({ length: props.totalPages }, (_, index) => index + 1))

const changePage = (page) => {
  if (page >= 1 && page <= props.totalPages) {
    emit('change', page)
  }
}
</script>

<template>
  <nav aria-label="Pagination">
    <ul class="pagination justify-content-center flex-wrap">
      <li class="page-item" :class="{ disabled: currentPage === 1 }">
        <button class="page-link" type="button" @click="changePage(currentPage - 1)">
          Truoc
        </button>
      </li>
      <li
        v-for="page in pages"
        :key="page"
        class="page-item"
        :class="{ active: page === currentPage }"
      >
        <button class="page-link" type="button" @click="changePage(page)">
          {{ page }}
        </button>
      </li>
      <li class="page-item" :class="{ disabled: currentPage === totalPages }">
        <button class="page-link" type="button" @click="changePage(currentPage + 1)">Sau</button>
      </li>
    </ul>
  </nav>
</template>




