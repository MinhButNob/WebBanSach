import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Dashboard from '../views/Dashboard.vue'
import Books from '../views/Books.vue'
import BookDetail from '../views/BookDetail.vue'
import Loans from '../views/Loans.vue'
import Returns from '../views/Returns.vue'
import Profile from '../views/Profile.vue'
import Fines from '../views/Fines.vue'

// Import từ pages/admin (thay thế cho views/admin)
import AdminBooksPage from '../pages/admin/BooksPage.vue'
import AdminBookCopiesPage from '../pages/admin/BookCopiesPage.vue'
import AdminAuthorsPage from '../pages/admin/AuthorsPage.vue'
import AdminCategoriesPage from '../pages/admin/CategoriesPage.vue'
import AdminPublishersPage from '../pages/admin/PublishersPage.vue'
import AdminShelvesPage from '../pages/admin/ShelvesPage.vue'
import AdminReservationsPage from '../pages/admin/ReservationsPage.vue'
import AdminPaymentsPage from '../pages/admin/PaymentsPage.vue'
import AdminCatalogsPage from '../pages/admin/CatalogsPage.vue'

// Import từ pages/user
import UserReservationsPage from '../pages/user/ReservationsPage.vue'
import UserPaymentsPage from '../pages/user/PaymentsPage.vue'

// Import từ views/librarian
import LibrarianDashboard from '../views/Librarian/Dashboard.vue'
import InventoryManagement from '../views/Librarian/InventoryManagement.vue'
import LoanProcessing from '../views/Librarian/LoanProcessing.vue'
import FineManagement from '../views/Librarian/FineManagement.vue'
import UserManagement from '../views/Librarian/UserManagement.vue'

import { authState } from '../stores/auth'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // Public routes
    { path: '/', redirect: '/login' },
    { path: '/login', name: 'Login', component: Login },
    { path: '/register', name: 'Register', component: Register },

    // User routes
    { path: '/dashboard', name: 'Dashboard', component: Dashboard, meta: { requiresAuth: true } },
    { path: '/books', name: 'Books', component: Books, meta: { requiresAuth: true } },
    {
      path: '/books/:id',
      name: 'BookDetail',
      component: BookDetail,
      props: true,
      meta: { requiresAuth: true },
    },
    { path: '/loans', name: 'Loans', component: Loans, meta: { requiresAuth: true } },
    { path: '/returns', name: 'Returns', component: Returns, meta: { requiresAuth: true } },
    { path: '/profile', name: 'Profile', component: Profile, meta: { requiresAuth: true } },
    { path: '/fines', name: 'Fines', component: Fines, meta: { requiresAuth: true } },
    {
      path: '/reservations',
      name: 'UserReservations',
      component: UserReservationsPage,
      meta: { requiresAuth: true, roles: ['User', 'Student'] },
    },
    {
      path: '/payments',
      name: 'UserPayments',
      component: UserPaymentsPage,
      meta: { requiresAuth: true, roles: ['User', 'Student'] },
    },

    // Admin routes (không còn dùng views/Admin nữa)
    { path: '/admin', redirect: '/admin/books' },
    {
      path: '/admin/books',
      name: 'AdminBooks',
      component: AdminBooksPage,
      meta: { requiresAuth: true, roles: ['Admin', 'Librarian'] },
    },
    {
      path: '/admin/book-copies',
      name: 'AdminBookCopies',
      component: AdminBookCopiesPage,
      meta: { requiresAuth: true, roles: ['Admin', 'Librarian'] },
    },
    {
      path: '/admin/authors',
      name: 'AdminAuthors',
      component: AdminAuthorsPage,
      meta: { requiresAuth: true, roles: ['Admin', 'Librarian'] },
    },
    {
      path: '/admin/categories',
      name: 'AdminCategories',
      component: AdminCategoriesPage,
      meta: { requiresAuth: true, roles: ['Admin', 'Librarian'] },
    },
    {
      path: '/admin/publishers',
      name: 'AdminPublishers',
      component: AdminPublishersPage,
      meta: { requiresAuth: true, roles: ['Admin', 'Librarian'] },
    },
    {
      path: '/admin/shelves',
      name: 'AdminShelves',
      component: AdminShelvesPage,
      meta: { requiresAuth: true, roles: ['Admin', 'Librarian'] },
    },
    {
      path: '/admin/reservations',
      name: 'AdminReservations',
      component: AdminReservationsPage,
      meta: { requiresAuth: true, roles: ['Admin', 'Librarian'] },
    },
    {
      path: '/admin/payments',
      name: 'AdminPayments',
      component: AdminPaymentsPage,
      meta: { requiresAuth: true, roles: ['Admin', 'Librarian'] },
    },
    {
      path: '/admin/catalogs',
      name: 'AdminCatalogs',
      component: AdminCatalogsPage,
      meta: { requiresAuth: true, roles: ['Admin'] },
    },

    // Librarian routes
    { path: '/librarian', redirect: '/librarian/dashboard' },
    {
      path: '/librarian/dashboard',
      name: 'LibrarianDashboard',
      component: LibrarianDashboard,
      meta: { requiresAuth: true, roles: ['Librarian'] },
    },
    {
      path: '/librarian/inventory',
      name: 'InventoryManagement',
      component: InventoryManagement,
      meta: { requiresAuth: true, roles: ['Librarian'] },
    },
    {
      path: '/librarian/loans',
      name: 'LoanProcessing',
      component: LoanProcessing,
      meta: { requiresAuth: true, roles: ['Librarian'] },
    },
    {
      path: '/librarian/fines',
      name: 'FineManagement',
      component: FineManagement,
      meta: { requiresAuth: true, roles: ['Librarian'] },
    },
    {
      path: '/librarian/users',
      name: 'UserManagement',
      component: UserManagement,
      meta: { requiresAuth: true, roles: ['Librarian'] },
    },
  ],
})

// Router Guard
router.beforeEach((to, from, next) => {
  // Lấy thông tin user từ localStorage
  const token = localStorage.getItem('token')
  const userStr = localStorage.getItem('user')
  let userRole = null

  if (userStr) {
    try {
      const user = JSON.parse(userStr)
      userRole = user.roleName || user.role
    } catch (e) {}
  }

  const isAuthenticated = !!token && !!userRole

  // Nếu chưa đăng nhập và không phải trang login/register
  if (!isAuthenticated && to.path !== '/login' && to.path !== '/register') {
    next('/login')
    return
  }

  // Nếu đã đăng nhập mà vào login/register -> redirect theo role
  if (isAuthenticated && (to.path === '/login' || to.path === '/register')) {
    if (userRole === 'Admin') {
      next('/admin/books')
    } else if (userRole === 'Librarian') {
      next('/librarian/dashboard')
    } else {
      next('/dashboard')
    }
    return
  }

  // Nếu route yêu cầu đăng nhập
  if (to.meta?.requiresAuth && !isAuthenticated) {
    next('/login')
    return
  }

  // Kiểm tra role cho route
  const allowedRoles = to.meta?.roles
  if (allowedRoles && allowedRoles.length > 0 && isAuthenticated) {
    const normalizedRole = userRole === 'Student' ? 'User' : userRole
    if (!allowedRoles.includes(normalizedRole)) {
      // Không có quyền -> redirect theo role
      if (userRole === 'Admin') {
        next('/admin/books')
      } else if (userRole === 'Librarian') {
        next('/librarian/dashboard')
      } else {
        next('/dashboard')
      }
      return
    }
  }

  next()
})

export default router
