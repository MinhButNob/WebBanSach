<template>
  <div class="container-fluid py-4">
    <h1 class="mb-4">Bang dieu khien thu thu</h1>

    <!-- Stats Row -->
    <div class="row mb-4">
      <div class="col-12 col-md-6 col-xl-3 mb-3" v-for="stat in librarianStats" :key="stat.label">
        <div :class="`card bg-${stat.tone}-subtle border-${stat.tone} border-2`">
          <div class="card-body">
            <div class="d-flex align-items-center">
              <div>
                <h6 class="card-subtitle text-muted mb-1">{{ stat.label }}</h6>
                <h3 class="card-title mb-0">{{ stat.value }}</h3>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <!-- Quick Thao tacs -->
      <div class="col-12 col-lg-6 mb-4">
        <div class="card">
          <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Thao tac nhanh</h5>
          </div>
          <div class="card-body">
            <div class="row g-2">
              <div class="col-6">
                <RouterLink to="/librarian/books" class="btn btn-outline-primary w-100">
                  Quan ly Sach
                </RouterLink>
              </div>
              <div class="col-6">
                <RouterLink to="/librarian/inventory" class="btn btn-outline-primary w-100">
                  Kho sach
                </RouterLink>
              </div>
              <div class="col-6">
                <RouterLink to="/librarian/loans" class="btn btn-outline-success w-100">
                  Xử ly muon sach
                </RouterLink>
              </div>
              <div class="col-6">
                <RouterLink to="/librarian/fines" class="btn btn-outline-warning w-100">
                  Quan ly tien phat
                </RouterLink>
              </div>
              <div class="col-6">
                <RouterLink to="/librarian/users" class="btn btn-outline-info w-100">
                  Nguoi dung
                </RouterLink>
              </div>
              <div class="col-6">
                <RouterLink to="/returns" class="btn btn-outline-secondary w-100">
                  Tra sach
                </RouterLink>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Recent Activities -->
      <div class="col-12 col-lg-6 mb-4">
        <div class="card">
          <div class="card-header bg-success text-white">
            <h5 class="mb-0">Hoat dong gan day</h5>
          </div>
          <div class="card-body">
            <div class="list-group list-group-flush">
              <div
                v-for="activity in recentActivities"
                :key="activity.id"
                class="list-group-item px-0 py-2"
              >
                <div class="d-flex align-items-center">
                  <div class="flex-grow-1">
                    <p class="mb-0">{{ activity.title }}</p>
                    <small class="text-muted">{{ activity.time }}</small>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Pending Tien phat & Qua han Tra sach -->
    <div class="row">
      <div class="col-12 col-lg-6 mb-4">
        <div class="card">
          <div class="card-header bg-danger text-white">
            <h5 class="mb-0">Tien phat dang cho xu ly</h5>
          </div>
          <div class="table-responsive">
            <table class="table table-sm table-hover mb-0">
              <thead class="table-light">
                <tr>
                  <th>Nguoi dung</th>
                  <th>So tien</th>
                  <th>Trang thai</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="fine in pendingFines" :key="fine.id">
                  <td>{{ fine.userName }}</td>
                  <td>
                    <strong>{{ fine.amount.toLocaleString() }} VND</strong>
                  </td>
                  <td>
                    <span class="badge bg-warning">{{ fine.status }}</span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <div class="col-12 col-lg-6 mb-4">
        <div class="card">
          <div class="card-header bg-warning text-dark">
            <h5 class="mb-0">Sach qua han can tra</h5>
          </div>
          <div class="table-responsive">
            <table class="table table-sm table-hover mb-0">
              <thead class="table-light">
                <tr>
                  <th>Nguoi dung</th>
                  <th>Sach</th>
                  <th>Tong ngay qua han</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="loan in overdueLoans" :key="loan.id">
                  <td>{{ loan.userName }}</td>
                  <td>{{ loan.bookTitle }}</td>
                  <td>
                    <span class="badge bg-danger">{{ -loan.daysRemaining }} days</span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const librarianStats = [
  { label: 'Tong so Nguoi dung', value: 15, tone: 'primary' },
  { label: 'Khoan muon dang hoat dong', value: 28, tone: 'info' },
  { label: 'Sach qua han', value: 5, tone: 'danger' },
  { label: 'Tien phat dang cho', value: 8, tone: 'warning' },
]

const pendingFines = [
  { id: 1, userName: 'Nguyen Van A', amount: 75000, status: 'Pending' },
  { id: 2, userName: 'Pham Thu Ha', amount: 25000, status: 'Pending' },
  { id: 3, userName: 'Le Minh Duc', amount: 50000, status: 'Pending' },
]

const overdueLoans = [
  { id: 1, userName: 'Tran Kim Loan', bookTitle: 'Clean Code', daysRemaining: -3 },
  { id: 2, userName: 'Hoang Minh', bookTitle: 'Design Patterns', daysRemaining: -5 },
]

const recentActivities = [
  {
    id: 1,
    title: 'User Nguyen Van A borrowed Clean Code',
    time: '10 mins ago',
  },
  { id: 2, title: 'Design Patterns was returned', time: '1 hour ago' },
  { id: 3, title: 'Fine payment received from Pham Thu Ha', time: '2 hours ago' },
  { id: 4, title: 'New book added: Vue.js 3 Guide', time: '5 hours ago' },
]
</script>
