<template>
  <div class="container-fluid py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h1>Quan ly nguoi dung</h1>
      <button class="btn btn-primary" @click="showAddForm = true">
        <FaIcon name="fa-user-plus" class="me-2" />
        Them nguoi dung moi
      </button>
    </div>

    <!-- Tim kiem & Filter -->
    <div class="row mb-4">
      <div class="col-12 col-md-6">
        <input
          v-model="searchQuery"
          type="text"
          class="form-control"
          placeholder="Tim kiem theo ten hoac email..."
        />
      </div>
      <div class="col-12 col-md-3">
        <select v-model="roleFilter" class="form-select">
          <option value="">Tat ca vai tro</option>
          <option value="Student">Sinh vien</option>
          <option value="Librarian">Thu thu</option>
          <option value="Admin">Quan tri</option>
        </select>
      </div>
      <div class="col-12 col-md-3">
        <select v-model="statusFilter" class="form-select">
          <option value="">Tat ca trang thai</option>
          <option value="Hoat dong">Hoat dong</option>
          <option value="Tam khoa">Tam khoa</option>
          <option value="Inactive">Khong hoat dong</option>
        </select>
      </div>
    </div>

    <!-- Nguoi dung Table -->
    <div class="card">
      <div class="table-responsive">
        <table class="table table-hover mb-0">
          <thead class="table-light">
            <tr>
              <th>Ten</th>
              <th>Email</th>
              <th>Vai tro</th>
              <th>Trang thai</th>
              <th>Khoan dang muon</th>
              <th>Tien phat</th>
              <th>Hieu luc den</th>
              <th>Thao tac</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in filteredUsers" :key="user.id">
              <td>
                <strong>{{ user.name }}</strong
                ><br />
                <small class="text-muted">{{ user.cardNumber }}</small>
              </td>
              <td>{{ user.email }}</td>
              <td>
                <span
                  :class="[
                    'badge',
                    user.role === 'Student'
                      ? 'bg-info'
                      : user.role === 'Librarian'
                        ? 'bg-warning'
                        : 'bg-danger',
                  ]"
                >
                  {{ user.role }}
                </span>
              </td>
              <td>
                <span
                  :class="[
                    'badge',
                    user.membershipStatus === 'Hoat dong'
                      ? 'bg-success'
                      : user.membershipStatus === 'Tam khoa'
                        ? 'bg-warning'
                        : 'bg-secondary',
                  ]"
                >
                  {{ user.membershipStatus }}
                </span>
              </td>
              <td>{{ user.currentLoans }} / {{ user.maxLoans }}</td>
              <td>
                <span v-if="user.penaltyBalance > 0" class="badge bg-danger">
                  {{ user.penaltyBalance.toLocaleString() }} VND
                </span>
                <span v-else class="badge bg-success">Khong co tien phat</span>
              </td>
              <td>{{ user.validTo }}</td>
              <td>
                <button class="btn btn-sm btn-info me-2" @click="editUser(user)">
                  <FaIcon name="fa-pen" />
                </button>
                <button
                  :class="[
                    'btn btn-sm me-2',
                    user.membershipStatus === 'Hoat dong' ? 'btn-warning' : 'btn-success',
                  ]"
                  @click="toggleStatus(user)"
                >
                  <FaIcon :name="user.membershipStatus === 'Hoat dong' ? 'fa-ban' : 'fa-check'" />
                </button>
                <button class="btn btn-sm btn-danger" @click="deleteUser(user.id)">
                  <FaIcon name="fa-trash" />
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Add/Sua User Modal -->
    <div v-if="showAddForm" class="modal d-block" style="background: rgba(0, 0, 0, 0.5)">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5>{{ editingUser ? 'Sua thong tin nguoi dung' : 'Them nguoi dung moi' }}</h5>
            <button type="button" class="btn-close" @click="showAddForm = false"></button>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col-12 col-md-6">
                <div class="mb-3">
                  <label class="form-label">Ten <span class="text-danger">*</span></label>
                  <input v-model="formData.name" type="text" class="form-control" />
                </div>
              </div>
              <div class="col-12 col-md-6">
                <div class="mb-3">
                  <label class="form-label">Email <span class="text-danger">*</span></label>
                  <input v-model="formData.email" type="email" class="form-control" />
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-12 col-md-6">
                <div class="mb-3">
                  <label class="form-label">Vai tro <span class="text-danger">*</span></label>
                  <select v-model="formData.role" class="form-select">
                    <option value="">Chon vai tro...</option>
                    <option value="Student">Sinh vien</option>
                    <option value="Librarian">Thu thu</option>
                    <option value="Admin">Quan tri</option>
                  </select>
                </div>
              </div>
              <div class="col-12 col-md-6">
                <div class="mb-3">
                  <label class="form-label">Dien thoai</label>
                  <input v-model="formData.phone" type="tel" class="form-control" />
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-12 col-md-6">
                <div class="mb-3">
                  <label class="form-label">So the thanh vien</label>
                  <input v-model="formData.cardNumber" type="text" class="form-control" />
                </div>
              </div>
              <div class="col-12 col-md-6">
                <div class="mb-3">
                  <label class="form-label">Trang thai thanh vien</label>
                  <select v-model="formData.membershipStatus" class="form-select">
                    <option value="Hoat dong">Hoat dong</option>
                    <option value="Tam khoa">Tam khoa</option>
                    <option value="Inactive">Khong hoat dong</option>
                  </select>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-12 col-md-6">
                <div class="mb-3">
                  <label class="form-label">Hieu luc tu ngay</label>
                  <input v-model="formData.validFrom" type="date" class="form-control" />
                </div>
              </div>
              <div class="col-12 col-md-6">
                <div class="mb-3">
                  <label class="form-label">Hieu luc den ngay</label>
                  <input v-model="formData.validTo" type="date" class="form-control" />
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-12 col-md-6">
                <div class="mb-3">
                  <label class="form-label">So tien phat toi da (VND)</label>
                  <input v-model.number="formData.maxLoans" type="number" class="form-control" />
                </div>
              </div>
              <div class="col-12 col-md-6">
                <div class="mb-3">
                  <label class="form-label">So du tien phat (VND)</label>
                  <input
                    v-model.number="formData.penaltyBalance"
                    type="number"
                    class="form-control"
                  />
                </div>
              </div>
            </div>

            <div class="mb-3">
              <label class="form-label">Dia chi</label>
              <textarea v-model="formData.address" class="form-control" rows="2"></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button @click="showAddForm = false" type="button" class="btn btn-secondary">
              Huy
            </button>
            <button @click="saveUser" type="button" class="btn btn-primary">
              {{ editingUser ? 'Cap nhat' : 'Them moi' }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import FaIcon from '@/components/common/FaIcon.vue'
import { users } from '@/data/mockData'

const searchQuery = ref('')
const roleFilter = ref('')
const statusFilter = ref('')
const showAddForm = ref(false)
const editingUser = ref(null)

const usersList = ref([...users])

const formData = ref({
  name: '',
  email: '',
  role: '',
  phone: '',
  cardNumber: '',
  membershipStatus: 'Hoat dong',
  validFrom: '',
  validTo: '',
  maxLoans: 5,
  penaltyBalance: 0,
  address: '',
})

const filteredUsers = computed(() => {
  return usersList.value.filter((user) => {
    const matchSearch =
      user.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      user.email.toLowerCase().includes(searchQuery.value.toLowerCase())

    const matchRole = !roleFilter.value || user.role === roleFilter.value
    const matchStatus = !statusFilter.value || user.membershipStatus === statusFilter.value

    return matchSearch && matchRole && matchStatus
  })
})

const editUser = (user) => {
  editingUser.value = user
  formData.value = { ...user }
  showAddForm.value = true
}

const saveUser = () => {
  if (!formData.value.name || !formData.value.email || !formData.value.role) {
    alert('Please fill required fields')
    return
  }

  if (editingUser.value) {
    const index = usersList.value.findIndex((u) => u.id === editingUser.value.id)
    if (index !== -1) {
      usersList.value[index] = { ...usersList.value[index], ...formData.value }
    }
  } else {
    const newUser = {
      id: Math.max(...usersList.value.map((u) => u.id), 0) + 1,
      currentLoans: 0,
      ...formData.value,
    }
    usersList.value.push(newUser)
  }

  showAddForm.value = false
  editingUser.value = null
  formData.value = {
    name: '',
    email: '',
    role: '',
    phone: '',
    cardNumber: '',
    membershipStatus: 'Hoat dong',
    validFrom: '',
    validTo: '',
    maxLoans: 5,
    penaltyBalance: 0,
    address: '',
  }
}

const toggleStatus = (user) => {
  user.membershipStatus = user.membershipStatus === 'Hoat dong' ? 'Tam khoa' : 'Hoat dong'
}

const deleteUser = (id) => {
  if (confirm('Ban chac chan muon xoa nguoi dung nay?')) {
    usersList.value = usersList.value.filter((u) => u.id !== id)
  }
}
</script>
