<script setup>
import { ref } from 'vue'
import { currentLoans, loanHistory } from '../data/mockData'

const activeTab = ref('current')
</script>

<template>
  <div>
    <div class="d-flex justify-content-between align-items-start flex-wrap gap-3 mb-4">
      <div>
        <h1 class="h3 fw-bold mb-1">Quan ly muon sach</h1>
        <p class="text-muted mb-0">Theo doi sach dang muon, qua han va lich su da tra.</p>
      </div>
    </div>

    <ul class="nav nav-pills mb-4">
      <li class="nav-item">
        <button
          class="nav-link"
          :class="{ active: activeTab === 'current' }"
          type="button"
          @click="activeTab = 'current'"
        >
          Khoan dang muon
        </button>
      </li>
      <li class="nav-item">
        <button
          class="nav-link"
          :class="{ active: activeTab === 'history' }"
          type="button"
          @click="activeTab = 'history'"
        >
          Lich su tra
        </button>
      </li>
    </ul>

    <div v-if="activeTab === 'current'" class="card border-0 shadow-sm">
      <div class="table-responsive">
        <table class="table align-middle mb-0">
          <thead class="table-light">
            <tr>
              <th>Ten sach</th>
              <th>Ngay muon</th>
              <th>Han tra</th>
              <th>Trang thai</th>
              <th class="text-end">Thao tacs</th>
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
              </td>
              <td class="text-end">
                <div class="btn-group btn-group-sm">
                  <button type="button" class="btn btn-outline-primary">Gia han</button>
                  <button type="button" class="btn btn-outline-secondary">Tra</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-else class="card border-0 shadow-sm">
      <div class="table-responsive">
        <table class="table align-middle mb-0">
          <thead class="table-light">
            <tr>
              <th>Ten sach</th>
              <th>Ngay muon</th>
              <th>Ngay tra</th>
              <th>Trang thai</th>
              <th>Tien phat</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="loan in loanHistory" :key="loan.id">
              <td class="fw-semibold">{{ loan.bookTitle }}</td>
              <td>{{ loan.loanDate }}</td>
              <td>{{ loan.returnDate }}</td>
              <td>
                <span class="badge text-bg-success">{{ loan.status }}</span>
              </td>
              <td>{{ loan.fineAmount.toLocaleString() }} VND</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
