<template>
  <div class="container-fluid py-4">
    <h1 class="mb-4">Loan Processing</h1>

    <ul class="nav nav-tabs mb-4" role="tablist">
      <li class="nav-item" role="presentation">
        <button
          @click="activeTab = 'issueBook'"
          :class="['nav-link', { active: activeTab === 'issueBook' }]"
          type="button"
          role="tab"
        >
          Issue Book
        </button>
      </li>
      <li class="nav-item" role="presentation">
        <button
          @click="activeTab = 'acceptTra'"
          :class="['nav-link', { active: activeTab === 'acceptTra' }]"
          type="button"
          role="tab"
        >
          Accept Tra
        </button>
      </li>
      <li class="nav-item" role="presentation">
        <button
          @click="activeTab = 'active'"
          :class="['nav-link', { active: activeTab === 'active' }]"
          type="button"
          role="tab"
        >
          Hoat dong Muon sach
        </button>
      </li>
    </ul>

    <!-- Issue Book Tab -->
    <div v-show="activeTab === 'issueBook'" class="tab-content">
      <div class="row">
        <div class="col-12 col-lg-6">
          <div class="card">
            <div class="card-header bg-primary text-white">
              <h5 class="mb-0">Issue Book</h5>
            </div>
            <div class="card-body">
              <div class="mb-3">
                <label class="form-label"
                  >User Card Number <span class="text-danger">*</span></label
                >
                <input
                  v-model="issueForm.cardNumber"
                  type="text"
                  class="form-control"
                  placeholder="LMS-1001"
                />
              </div>
              <div class="mb-3">
                <label class="form-label">Book Barcode <span class="text-danger">*</span></label>
                <input
                  v-model="issueForm.barcode"
                  type="text"
                  class="form-control"
                  placeholder="1001"
                />
              </div>
              <button @click="issueBook" class="btn btn-success w-100">Issue Book</button>
            </div>
          </div>
        </div>

        <div v-if="selectedUserForIssue" class="col-12 col-lg-6">
          <div class="card">
            <div class="card-header bg-info text-white">
              <h5 class="mb-0">User Info</h5>
            </div>
            <div class="card-body">
              <p><strong>Name:</strong> {{ selectedUserForIssue.name }}</p>
              <p><strong>Email:</strong> {{ selectedUserForIssue.email }}</p>
              <p><strong>Card Number:</strong> {{ selectedUserForIssue.cardNumber }}</p>
              <p><strong>Khoan dang muon:</strong> {{ selectedUserForIssue.currentLoans }}</p>
              <p><strong>Toi da muon sach:</strong> {{ selectedUserForIssue.maxLoans }}</p>
              <div v-if="selectedUserForIssue.penaltyBalance > 0" class="alert alert-warning">
                <strong>Penalty Balance:</strong>
                {{ selectedUserForIssue.penaltyBalance.toLocaleString() }} VND
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Accept Tra Tab -->
    <div v-show="activeTab === 'acceptTra'" class="tab-content">
      <div class="row">
        <div class="col-12 col-lg-6">
          <div class="card">
            <div class="card-header bg-warning text-dark">
              <h5 class="mb-0">Accept Tra</h5>
            </div>
            <div class="card-body">
              <div class="mb-3">
                <label class="form-label">Book Barcode <span class="text-danger">*</span></label>
                <input
                  v-model="returnForm.barcode"
                  type="text"
                  class="form-control"
                  placeholder="1001"
                />
              </div>
              <div class="mb-3">
                <label class="form-label">Condition</label>
                <select v-model="returnForm.condition" class="form-select">
                  <option value="Good">Good</option>
                  <option value="Fair">Fair (Minor damage)</option>
                  <option value="Damaged">Damaged (Require repair)</option>
                  <option value="Lost">Lost</option>
                </select>
              </div>
              <div class="mb-3">
                <label class="form-label">Notes</label>
                <textarea v-model="returnForm.notes" class="form-control" rows="2"></textarea>
              </div>
              <button @click="acceptTra" class="btn btn-warning w-100">Accept Tra</button>
            </div>
          </div>
        </div>

        <div v-if="returnSummary" class="col-12 col-lg-6">
          <div class="card">
            <div class="card-header bg-success text-white">
              <h5 class="mb-0">Tra Summary</h5>
            </div>
            <div class="card-body">
              <p><strong>Book:</strong> {{ returnSummary.bookTitle }}</p>
              <p><strong>User:</strong> {{ returnSummary.userName }}</p>
              <p><strong>Loan Date:</strong> {{ returnSummary.loanDate }}</p>
              <p><strong>Due Date:</strong> {{ returnSummary.dueDate }}</p>
              <div v-if="returnSummary.daysOverdue > 0" class="alert alert-danger">
                <strong>Qua han Days:</strong> {{ returnSummary.daysOverdue }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Hoat dong Muon sach Tab -->
    <div v-show="activeTab === 'active'" class="tab-content">
      <div class="card">
        <div class="card-header">
          <h5 class="mb-0">Hoat dong Muon sach</h5>
        </div>
        <div class="table-responsive">
          <table class="table table-hover mb-0">
            <thead class="table-light">
              <tr>
                <th>User</th>
                <th>Book</th>
                <th>Loan Date</th>
                <th>Due Date</th>
                <th>Days Remaining</th>
                <th>Trang thai</th>
                <th>Thao tacs</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="loan in activeLoans" :key="loan.id">
                <td>{{ loan.userName }}</td>
                <td>{{ loan.bookTitle }}</td>
                <td>{{ loan.loanDate }}</td>
                <td>{{ loan.dueDate }}</td>
                <td>
                  <span :class="['badge', loan.daysRemaining > 0 ? 'bg-info' : 'bg-danger']">
                    {{
                      loan.daysRemaining > 0
                        ? loan.daysRemaining
                        : -loan.daysRemaining + ' days overdue'
                    }}
                  </span>
                </td>
                <td>
                  <span :class="['badge', loan.status === 'Muoned' ? 'bg-success' : 'bg-danger']">
                    {{ loan.status }}
                  </span>
                </td>
                <td>
                  <button class="btn btn-sm btn-success" @click="extendLoan(loan.id)">
                    Gia han
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { users } from '@/data/mockData'

const activeTab = ref('issueBook')

const issueForm = ref({ cardNumber: '', barcode: '' })
const returnForm = ref({ barcode: '', condition: 'Good', notes: '' })
const selectedUserForIssue = ref(null)
const returnSummary = ref(null)

const activeLoans = ref([
  {
    id: 1,
    userName: 'Nguyen Van A',
    bookTitle: 'Clean Code',
    loanDate: '2024-01-01',
    dueDate: '2024-01-15',
    status: 'Muoned',
    daysRemaining: 3,
  },
  {
    id: 2,
    userName: 'Pham Thu Ha',
    bookTitle: 'Refactoring',
    loanDate: '2024-01-04',
    dueDate: '2024-01-18',
    status: 'Muoned',
    daysRemaining: -2,
  },
  {
    id: 3,
    userName: 'Le Minh Duc',
    bookTitle: 'Design Patterns',
    loanDate: '2024-01-08',
    dueDate: '2024-01-22',
    status: 'Muoned',
    daysRemaining: 8,
  },
])

const issueBook = () => {
  if (!issueForm.value.cardNumber || !issueForm.value.barcode) {
    alert('Please enter card number and barcode')
    return
  }

  const user = users.find((u) => u.cardNumber === issueForm.value.cardNumber)
  if (!user) {
    alert('User not found')
    return
  }

  selectedUserForIssue.value = user

  if (user.currentLoans >= user.maxLoans) {
    alert('User has reached maximum number of loans')
    return
  }

  alert(`✓ Book issued to ${user.name}`)
  issueForm.value = { cardNumber: '', barcode: '' }
  selectedUserForIssue.value = null
}

const acceptTra = () => {
  if (!returnForm.value.barcode) {
    alert('Please enter barcode')
    return
  }

  returnSummary.value = {
    bookTitle: 'Clean Code',
    userName: 'Nguyen Van A',
    loanDate: '2024-01-01',
    dueDate: '2024-01-15',
    daysOverdue: 0,
  }

  alert(`✓ Book returned as ${returnForm.value.condition}`)
  returnForm.value = { barcode: '', condition: 'Good', notes: '' }
}

const extendLoan = (loanId) => {
  const loan = activeLoans.value.find((l) => l.id === loanId)
  if (loan) {
    loan.daysRemaining += 14
    alert(`Loan extended for ${loan.bookTitle}`)
  }
}
</script>
