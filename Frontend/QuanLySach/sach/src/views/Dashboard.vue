<script setup>
import { currentLoans, dashboardStats, recentActivity, topBorrowedBooks } from '../data/mockData'
</script>

<template>
  <div class="container-fluid p-0">
    <div class="d-flex justify-content-between align-items-start flex-wrap gap-3 mb-4">
      <div>
        <h1 class="h3 fw-bold mb-1">Bang dieu khien</h1>
        <p class="text-muted mb-0">Tong quan hoat dong muon va van hanh thu vien gan day.</p>
      </div>
      <span
        class="badge text-bg-primary-subtle text-primary border border-primary-subtle px-3 py-2"
      >
        Du lieu demo
      </span>
    </div>

    <div class="row g-3 mb-4">
      <div v-for="stat in dashboardStats" :key="stat.label" class="col-12 col-sm-6 col-xl-3">
        <div class="card h-100">
          <div class="card-body">
            <div>
              <div class="text-muted small">{{ stat.label }}</div>
              <div class="h4 fw-bold mb-0">{{ stat.value }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row g-4">
      <div class="col-12 col-xl-8">
        <div class="card h-100">
          <div class="card-header">
            <h2 class="h5 mb-0">Khoan dang muon</h2>
          </div>
          <div class="table-responsive">
            <table class="table align-middle mb-0">
              <thead class="table-light">
                <tr>
                  <th>Ten sach</th>
                  <th>Ngay muon</th>
                  <th>Han tra</th>
                  <th>Trang thai</th>
                  <th class="text-end">Thao tac</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="loan in currentLoans" :key="loan.id">
                  <td class="fw-semibold">{{ loan.bookTitle }}</td>
                  <td>{{ loan.loanDate }}</td>
                  <td>{{ loan.dueDate }}</td>
                  <td>
                    <span
                      :class="
                        loan.status === 'Qua han' ? 'badge text-bg-danger' : 'badge text-bg-success'
                      "
                    >
                      {{ loan.status }}
                    </span>
                    <small class="d-block text-muted mt-1"
                      >{{ loan.daysRemaining }} ngay con lai</small
                    >
                  </td>
                  <td class="text-end">
                    <button type="button" class="btn btn-outline-primary btn-sm">Gia han</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <div class="col-12 col-xl-4">
        <div class="card h-100">
          <div class="card-header">
            <h2 class="h5 mb-0">Sach duoc muon nhieu</h2>
          </div>
          <div class="table-responsive">
            <table class="table table-sm align-middle mb-0">
              <thead class="table-light">
                <tr>
                  <th>Tên sách</th>
                  <th class="text-end">Lượt mượn</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="book in topBorrowedBooks" :key="book.title">
                  <td class="fw-semibold">{{ book.title }}</td>
                  <td class="text-end">{{ book.count }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <div class="col-12">
        <div class="card">
          <div class="card-header">
            <h2 class="h5 mb-0">Hoat dong gan day</h2>
          </div>
          <div class="list-group list-group-flush">
            <div v-for="activity in recentActivity" :key="activity.id" class="list-group-item">
              <div>
                <div class="fw-semibold">{{ activity.title }}</div>
                <small class="text-muted">{{ activity.time }}</small>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
