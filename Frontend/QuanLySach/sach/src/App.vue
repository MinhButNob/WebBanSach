<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import Navbar from './components/common/Navbar.vue'
import { authState } from './stores/auth'

const route = useRoute()

const showNavbar = computed(() => !['/login', '/register'].includes(route.path))
const isStaffLayout = computed(
  () =>
    showNavbar.value &&
    ['Admin', 'Librarian'].includes(authState.currentUser?.role || authState.currentUser?.roleName),
)
</script>

<template>
  <div class="min-vh-100 bg-light">
    <Navbar v-if="showNavbar" />
    <main class="py-4" :class="{ 'staff-main': isStaffLayout }">
      <div :class="isStaffLayout ? 'container-fluid px-3 px-lg-4' : 'container'">
        <RouterView />
      </div>
    </main>
  </div>
</template>

<style scoped>
@media (min-width: 992px) {
  .staff-main {
    margin-left: 260px;
  }
}
</style>
