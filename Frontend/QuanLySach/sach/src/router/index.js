import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Dashboard from '../views/Dashboard.vue'
import Books from '../views/Books.vue'
import BookDetail from '../views/BookDetail.vue'
import Loans from '../views/Loans.vue'
import Returns from '../views/Returns.vue'
import Profile from '../views/Profile.vue'
import Fines from '../views/Fines.vue'
import AdminUsers from '../views/Admin/Users.vue'
import AdminReports from '../views/Admin/Reports.vue'
import LibrarianDashboard from '../views/Librarian/Dashboard.vue'
import BookManagement from '../views/Librarian/BookManagement.vue'
import InventoryManagement from '../views/Librarian/InventoryManagement.vue'
import LoanProcessing from '../views/Librarian/LoanProcessing.vue'
import FineManagement from '../views/Librarian/FineManagement.vue'
import UserManagement from '../views/Librarian/UserManagement.vue'
import UserReservationsPage from '../pages/user/ReservationsPage.vue'
import UserPaymentsPage from '../pages/user/PaymentsPage.vue'
import AdminReservationsPage from '../pages/admin/ReservationsPage.vue'
import AdminBookCopiesPage from '../pages/admin/BookCopiesPage.vue'
import AdminCatalogsPage from '../pages/admin/CatalogsPage.vue'
import AdminPaymentsPage from '../pages/admin/PaymentsPage.vue'
import { authState } from '../stores/auth'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', component: Login },
    { path: '/dashboard', component: Dashboard },
    { path: '/books', component: Books },
    { path: '/books/:id', component: BookDetail, props: true },
    {
      path: '/reservations',
      component: UserReservationsPage,
      meta: { roles: ['User', 'Student'] },
    },
    { path: '/payments', component: UserPaymentsPage, meta: { roles: ['User', 'Student'] } },
    { path: '/loans', component: Loans },
    { path: '/returns', component: Returns },
    { path: '/profile', component: Profile },
    { path: '/fines', component: Fines },
    { path: '/admin', redirect: '/admin/users' },
    { path: '/admin/users', component: AdminUsers },
    { path: '/admin/reports', component: AdminReports },
    {
      path: '/admin/reservations',
      component: AdminReservationsPage,
      meta: { roles: ['Admin', 'Librarian'] },
    },
    {
      path: '/admin/book-copies',
      component: AdminBookCopiesPage,
      meta: { roles: ['Admin', 'Librarian'] },
    },
    { path: '/admin/catalogs', component: AdminCatalogsPage, meta: { roles: ['Admin'] } },
    {
      path: '/admin/payments',
      component: AdminPaymentsPage,
      meta: { roles: ['Admin', 'Librarian'] },
    },
    { path: '/librarian', redirect: '/librarian/dashboard' },
    { path: '/librarian/dashboard', component: LibrarianDashboard },
    { path: '/librarian/books', component: BookManagement },
    { path: '/librarian/inventory', component: InventoryManagement },
    { path: '/librarian/loans', component: LoanProcessing },
    { path: '/librarian/fines', component: FineManagement },
    { path: '/librarian/users', component: UserManagement },
    { path: '/librarian/reservations', redirect: '/admin/reservations' },
    { path: '/librarian/book-copies', redirect: '/admin/book-copies' },
    { path: '/librarian/payments', redirect: '/admin/payments' },
  ],
})

router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next()
    return
  }

  const role = authState.currentUser.role
  const allowedRoles = to.meta?.roles

  if (!allowedRoles || allowedRoles.length === 0) {
    next()
    return
  }

  const normalizedRole = role === 'Student' ? 'User' : role

  if (allowedRoles.includes(normalizedRole)) {
    next()
    return
  }

  if (normalizedRole === 'Admin') {
    next('/admin/users')
  } else if (normalizedRole === 'Librarian') {
    next('/librarian/dashboard')
  } else {
    next('/dashboard')
  }
})

export default router
