<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useRouter } from 'vue-router'
import { authState, logout } from '../../stores/auth'

const router = useRouter()
const route = useRoute()

const currentUser = computed(() => authState.currentUser)
const currentRole = computed(() => currentUser.value?.role || currentUser.value?.roleName)
const isStaff = computed(() => ['Admin', 'Librarian'].includes(currentRole.value))

const navigationItems = computed(() => {
  const items = [
    { label: 'Bảng điều khiển', to: '/dashboard' },
    { label: 'Sách', to: '/books' },
    { label: 'Mượn sách', to: '/loans' },
    { label: 'Đặt trước', to: '/reservations' },
    { label: 'Thanh toán', to: '/payments' },
    { label: 'Hồ sơ', to: '/profile' },
    { label: 'Tiền phạt', to: '/fines' },
  ]

  if (currentRole.value === 'Librarian') {
    items.length = 0 // Clear student menu
    items.push(
      { label: 'Bảng điều khiển', to: '/librarian/dashboard' },
      { label: 'Quản lý sách', to: '/admin/books' },
      { label: 'Kho sách', to: '/librarian/inventory', custom: true },
      { label: 'Bản sao sách', to: '/admin/book-copies', custom: true },
      { label: 'Danh mục tác giả', to: '/admin/authors' },
      { label: 'Danh mục thể loại', to: '/admin/categories' },
      { label: 'Danh mục nhà xuất bản', to: '/admin/publishers' },
      { label: 'Danh mục kệ sách', to: '/admin/shelves' },
      { label: 'Xử lý mượn sách', to: '/librarian/loans', custom: true },
      { label: 'Đặt trước', to: '/admin/reservations' },
      { label: 'Thanh toán', to: '/admin/payments' },
      { label: 'Tiền phạt', to: '/librarian/fines' },
      { label: 'Người dùng', to: '/librarian/users' },
      { label: 'Trả sách', to: '/returns' },
    )
  } else if (currentRole.value === 'Admin') {
    items.length = 0 // Clear student menu
    items.push(
      { label: 'Bảng điều khiển', to: '/admin/dashboard' },
      { label: 'Quản lý sách', to: '/admin/books' },
      { label: 'Bản sao sách', to: '/admin/book-copies' },
      { label: 'Danh mục tác giả', to: '/admin/authors' },
      { label: 'Danh mục thể loại', to: '/admin/categories' },
      { label: 'Danh mục nhà xuất bản', to: '/admin/publishers' },
      { label: 'Danh mục kệ sách', to: '/admin/shelves' },
      { label: 'Đặt trước', to: '/admin/reservations' },
      { label: 'Thanh toán', to: '/admin/payments' },
      { label: 'Danh mục', to: '/admin/catalogs' },
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
        Hệ thống thư viện
      </RouterLink>

      <ul class="nav nav-pills flex-column gap-1">
        <li v-for="item in navigationItems" :key="item.to" class="nav-item">
          <RouterLink
            class="nav-link d-flex align-items-center gap-2 text-white"
            :class="{ active: route.path === item.to }"
            :to="item.to"
          >
            <span>{{ item.label }}</span>
          </RouterLink>
        </li>
      </ul>

      <div class="mt-auto pt-3 border-top border-secondary-subtle">
        <div class="small text-white-50 mb-2">{{ currentUser?.name || 'Người dùng' }}</div>
        <button class="btn btn-outline-light btn-sm w-100" type="button" @click="handleLogout">
          Đăng xuất
        </button>
      </div>
    </aside>

    <nav class="navbar navbar-dark bg-dark d-lg-none">
      <div class="container-fluid">
        <RouterLink class="navbar-brand fw-semibold" to="/dashboard">
          Hệ thống thư viện
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
              <span>{{ item.label }}</span>
            </RouterLink>
          </li>
        </ul>

        <div class="mt-auto pt-3 border-top">
          <div class="small text-muted mb-2">{{ currentUser?.name || 'Người dùng' }}</div>
          <button class="btn btn-outline-danger btn-sm w-100" type="button" @click="handleLogout">
            Đăng xuất
          </button>
        </div>
      </div>
    </div>
  </div>

  <nav v-else class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
      <RouterLink class="navbar-brand fw-semibold" to="/dashboard"> Hệ thống thư viện </RouterLink>

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
              <span>{{ item.label }}</span>
            </RouterLink>
          </li>
        </ul>

        <div class="d-flex align-items-center gap-2">
          <span
            v-if="(currentUser?.penaltyBalance || 0) > 0"
            class="badge text-bg-warning text-dark"
          >
            {{ currentUser.penaltyBalance.toLocaleString() }} VND
          </span>

          <div class="dropdown">
            <button
              class="btn btn-outline-light dropdown-toggle"
              data-bs-toggle="dropdown"
              type="button"
            >
              {{ currentUser?.name || 'Người dùng' }}
            </button>
            <ul class="dropdown-menu dropdown-menu-end">
              <li>
                <RouterLink class="dropdown-item" to="/profile">Hồ sơ</RouterLink>
              </li>
              <li><hr class="dropdown-divider" /></li>
              <li>
                <button class="dropdown-item text-danger" type="button" @click="handleLogout">
                  Đăng xuất
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
