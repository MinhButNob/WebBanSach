<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useRouter } from 'vue-router'
import { authState, logout } from '../../stores/auth'

const router = useRouter()
const route = useRoute()

const isStaff = computed(() => ['Admin', 'Librarian'].includes(authState.currentUser.role))

const navigationItems = computed(() => {
  const items = [
    { label: 'Bang dieu khien', to: '/dashboard', icon: 'fa-chart-line' },
    { label: 'Sach', to: '/books', icon: 'fa-book' },
    { label: 'Muon sach', to: '/loans', icon: 'fa-receipt' },
    { label: 'Dat truoc', to: '/reservations', icon: 'fa-bookmark' },
    { label: 'Thanh toan', to: '/payments', icon: 'fa-credit-card' },
    { label: 'Ho so', to: '/profile', icon: 'fa-user' },
    { label: 'Tien phat', to: '/fines', icon: 'fa-coins' },
  ]

  if (authState.currentUser.role === 'Librarian') {
    items.length = 0 // Clear student menu
    items.push(
      { label: 'Bang dieu khien', to: '/librarian/dashboard', icon: 'fa-chart-line' },
      { label: 'Quan ly sach', to: '/librarian/books', icon: 'fa-book' },
      { label: 'Kho sach', to: '/librarian/inventory', icon: 'fa-boxes-stacked', custom: true },
      { label: 'Ban sao', to: '/admin/book-copies', icon: 'fa-boxes-stacked', custom: true },
      { label: 'Xu ly muon sach', to: '/librarian/loans', icon: 'fa-handshake', custom: true },
      { label: 'Dat truoc', to: '/admin/reservations', icon: 'fa-bookmark' },
      { label: 'Thanh toan', to: '/admin/payments', icon: 'fa-credit-card' },
      { label: 'Tien phat', to: '/librarian/fines', icon: 'fa-coins' },
      { label: 'Nguoi dung', to: '/librarian/users', icon: 'fa-users' },
      { label: 'Tra sach', to: '/returns', icon: 'fa-rotate-left' },
    )
  } else if (authState.currentUser.role === 'Admin') {
    items.length = 0 // Clear student menu
    items.push(
      { label: 'Bang dieu khien', to: '/dashboard', icon: 'fa-chart-line' },
      { label: 'Nguoi dung', to: '/admin/users', icon: 'fa-users' },
      { label: 'Bao cao', to: '/admin/reports', icon: 'fa-chart-column' },
      { label: 'Quan ly sach', to: '/librarian/books', icon: 'fa-book' },
      { label: 'Ban sao', to: '/admin/book-copies', icon: 'fa-boxes-stacked' },
      { label: 'Dat truoc', to: '/admin/reservations', icon: 'fa-bookmark' },
      { label: 'Thanh toan', to: '/admin/payments', icon: 'fa-credit-card' },
      { label: 'Danh muc', to: '/admin/catalogs', icon: 'fa-bookmark' },
    )
  }

  return items
})

const handleLogout = () => {
  logout()
  router.push('/login')
}
</script>

<template>
  <div v-if="isStaff">
    <aside class="staff-sidebar d-none d-lg-flex flex-column bg-dark text-white p-3">
      <RouterLink class="navbar-brand fw-semibold mb-4" to="/dashboard">
        <FaIcon name="fa-book-open-reader" class="me-2" />
        He thong thu vien
      </RouterLink>

      <ul class="nav nav-pills flex-column gap-1">
        <li v-for="item in navigationItems" :key="item.to" class="nav-item">
          <RouterLink
            class="nav-link d-flex align-items-center gap-2 text-white"
            :class="{ active: route.path === item.to }"
            :to="item.to"
          >
            <FaIcon :name="item.icon" />
            <span>{{ item.label }}</span>
          </RouterLink>
        </li>
      </ul>

      <div class="mt-auto pt-3 border-top border-secondary-subtle">
        <div class="small text-white-50 mb-2">{{ authState.currentUser.name }}</div>
        <button class="btn btn-outline-light btn-sm w-100" type="button" @click="handleLogout">
          Dang xuat
        </button>
      </div>
    </aside>

    <nav class="navbar navbar-dark bg-dark d-lg-none">
      <div class="container-fluid">
        <RouterLink class="navbar-brand fw-semibold" to="/dashboard">
          <FaIcon name="fa-book-open-reader" class="me-2" />
          He thong thu vien
        </RouterLink>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="offcanvas"
          data-bs-target="#staffSidebarMobile"
          aria-controls="staffSidebarMobile"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
    </nav>

    <div
      id="staffSidebarMobile"
      class="offcanvas offcanvas-start"
      tabindex="-1"
      aria-labelledby="staffSidebarMobileLabel"
    >
      <div class="offcanvas-header">
        <h5 id="staffSidebarMobileLabel" class="offcanvas-title">Menu</h5>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas"></button>
      </div>
      <div class="offcanvas-body d-flex flex-column">
        <ul class="nav nav-pills flex-column gap-1">
          <li v-for="item in navigationItems" :key="`mobile-${item.to}`" class="nav-item">
            <RouterLink
              class="nav-link d-flex align-items-center gap-2"
              :class="{ active: route.path === item.to }"
              :to="item.to"
              data-bs-dismiss="offcanvas"
            >
              <FaIcon :name="item.icon" />
              <span>{{ item.label }}</span>
            </RouterLink>
          </li>
        </ul>

        <div class="mt-auto pt-3 border-top">
          <div class="small text-muted mb-2">{{ authState.currentUser.name }}</div>
          <button class="btn btn-outline-danger btn-sm w-100" type="button" @click="handleLogout">
            Dang xuat
          </button>
        </div>
      </div>
    </div>
  </div>

  <nav v-else class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
      <RouterLink class="navbar-brand fw-semibold" to="/dashboard">
        <FaIcon name="fa-book-open-reader" class="me-2" />
        He thong thu vien
      </RouterLink>

      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#mainNavbar"
        aria-controls="mainNavbar"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div id="mainNavbar" class="collapse navbar-collapse">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li v-for="item in navigationItems" :key="item.to" class="nav-item">
            <RouterLink class="nav-link d-flex align-items-center gap-2" :to="item.to">
              <FaIcon :name="item.icon" />
              <span>{{ item.label }}</span>
            </RouterLink>
          </li>
        </ul>

        <div class="d-flex align-items-center gap-2">
          <span
            v-if="authState.currentUser.penaltyBalance > 0"
            class="badge text-bg-warning text-dark"
          >
            <FaIcon name="fa-circle-exclamation" class="me-1" />
            {{ authState.currentUser.penaltyBalance.toLocaleString() }} VND
          </span>

          <div class="dropdown">
            <button
              class="btn btn-outline-light dropdown-toggle"
              data-bs-toggle="dropdown"
              type="button"
            >
              <FaIcon name="fa-circle-user" class="me-2" />
              {{ authState.currentUser.name }}
            </button>
            <ul class="dropdown-menu dropdown-menu-end">
              <li>
                <RouterLink class="dropdown-item" to="/profile">Ho so</RouterLink>
              </li>
              <li><hr class="dropdown-divider" /></li>
              <li>
                <button class="dropdown-item text-danger" type="button" @click="handleLogout">
                  Dang xuat
                </button>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<style scoped>
.staff-sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 260px;
  height: 100vh;
  z-index: 1030;
}
</style>
