<script>
import axios from 'axios'

export default {
  data() {
    return {
      apiUrl: 'http://localhost:8080/api/shelves',
      form: {
        id: null,
        name: '',        // Đổi từ code thành name
        location: '',    // Đổi từ floor thành location
        status: 'ACTIVE',
      },
      list: [],
      errors: {},
      message: '',
      loading: false,
      isEditing: false,
      statusOptions: [
        { value: 'ACTIVE', label: 'Hoạt động' },
        { value: 'INACTIVE', label: 'Ngừng hoạt động' },
      ],
    }
  },

  mounted() {
    this.loadData()
  },

  methods: {
    async loadData() {
      this.loading = true
      this.message = ''

      const token = localStorage.getItem('token')
      const config = token ? { headers: { Authorization: `Bearer ${token}` } } : {}

      try {
        let res
        try {
          res = await axios.get(`${this.apiUrl}/admin`, config)
        } catch {
          res = await axios.get(this.apiUrl, config)
        }

        const rows = Array.isArray(res.data)
          ? res.data
          : Array.isArray(res.data?.content)
            ? res.data.content
            : []

        this.list = rows.map((item) => ({
          id: item.id,
          name: item.name || '',      // Đổi từ code thành name
          location: item.location || '', // Đổi từ floor thành location
          status: item.status || 'ACTIVE',
        }))
      } catch (error) {
        this.message = 'Không thể tải danh sách kệ sách'
        console.error(error)
      } finally {
        this.loading = false
      }
    },

    async addItem() {
      if (!this.validate()) return

      const token = localStorage.getItem('token')
      const config = token ? { headers: { Authorization: `Bearer ${token}` } } : {}

      try {
        await axios.post(
          this.apiUrl,
          {
            name: this.form.name,      // Đổi từ code thành name
            location: this.form.location, // Đổi từ floor thành location
            status: this.form.status,
          },
          config,
        )
        this.reset()
        this.loadData()
        this.message = 'Thêm kệ sách thành công!'
        setTimeout(() => this.message = '', 3000)
      } catch (error) {
        this.message = 'Không thể thêm kệ sách'
        console.error(error)
      }
    },

    async updateItem() {
      if (!this.validate() || !this.form.id) return

      const token = localStorage.getItem('token')
      const config = token ? { headers: { Authorization: `Bearer ${token}` } } : {}

      try {
        await axios.put(
          `${this.apiUrl}/${this.form.id}`,
          {
            name: this.form.name,      // Đổi từ code thành name
            location: this.form.location, // Đổi từ floor thành location
            status: this.form.status,
          },
          config,
        )
        this.reset()
        this.loadData()
        this.message = 'Cập nhật kệ sách thành công!'
        setTimeout(() => this.message = '', 3000)
      } catch (error) {
        this.message = 'Không thể cập nhật kệ sách'
        console.error(error)
      }
    },

    async remove(id) {
      if (!window.confirm('Bạn có chắc chắn muốn xóa?')) return

      const token = localStorage.getItem('token')
      const config = token ? { headers: { Authorization: `Bearer ${token}` } } : {}

      try {
        await axios.delete(`${this.apiUrl}/${id}`, config)
        this.loadData()
        this.message = 'Xóa kệ sách thành công!'
        setTimeout(() => this.message = '', 3000)
      } catch (error) {
        this.message = 'Không thể xóa kệ sách'
        console.error(error)
      }
    },

    edit(item) {
      this.isEditing = true
      this.errors = {}
      this.message = ''
      this.form = {
        id: item.id,
        name: item.name,        // Đổi từ code thành name
        location: item.location, // Đổi từ floor thành location
        status: item.status,
      }
    },

    reset() {
      this.isEditing = false
      this.errors = {}
      this.message = ''
      this.form = {
        id: null,
        name: '',      // Đổi từ code thành name
        location: '',  // Đổi từ floor thành location
        status: 'ACTIVE',
      }
    },

    validate() {
      this.errors = {}

      if (!this.form.name || !this.form.name.trim()) {
        this.errors.name = 'Mã kệ không được để trống'
      }
      if (!this.form.location || !this.form.location.trim()) {
        this.errors.location = 'Tầng không được để trống'
      }

      return Object.keys(this.errors).length === 0
    },
  },
}
</script>

<template>
  <div class="container py-4">
    <h4 class="mb-3">Quản lý kệ sách</h4>

    <div v-if="message" class="alert alert-success alert-dismissible fade show" role="alert">
      {{ message }}
      <button type="button" class="btn-close" @click="message = ''"></button>
    </div>

    <div class="row g-3">
      <div class="col-12 col-lg-4">
        <div class="card">
          <div class="card-header">
            <strong>{{ isEditing ? 'Cập nhật kệ sách' : 'Thêm kệ sách' }}</strong>
          </div>
          <div class="card-body">
            <div class="mb-3">
              <label class="form-label">Mã kệ</label>
              <input v-model="form.name" type="text" class="form-control" placeholder="Nhập mã kệ" />
              <div v-if="errors.name" class="text-danger small mt-1">{{ errors.name }}</div>
            </div>

            <div class="mb-3">
              <label class="form-label">Tầng</label>
              <input v-model="form.location" type="text" class="form-control" placeholder="Nhập tầng" />
              <div v-if="errors.location" class="text-danger small mt-1">{{ errors.location }}</div>
            </div>

            <div class="mb-3">
              <label class="form-label">Trạng thái</label>
              <select v-model="form.status" class="form-select">
                <option v-for="status in statusOptions" :key="status.value" :value="status.value">
                  {{ status.label }}
                </option>
              </select>
            </div>
          </div>
          <div class="card-footer d-flex gap-2">
            <button v-if="!isEditing" class="btn btn-primary" @click="addItem">Thêm</button>
            <button v-else class="btn btn-primary" @click="updateItem">Cập nhật</button>
            <button class="btn btn-secondary" @click="reset">Làm mới</button>
          </div>
        </div>
      </div>

      <div class="col-12 col-lg-8">
        <div class="card">
          <div class="table-responsive">
            <table class="table table-bordered table-hover">
              <thead class="table-light">
                <tr>
                  <th>ID</th>
                  <th>Mã kệ</th>
                  <th>Tầng</th>
                  <th>Trạng thái</th>
                  <th>Thao tác</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in list" :key="item.id">
                  <td>{{ item.id }}</td>
                  <td>{{ item.name }}</td>
                  <td>{{ item.location }}</td>
                  <td>
                    <span
                      class="badge"
                      :class="item.status === 'ACTIVE' ? 'bg-success' : 'bg-secondary'"
                    >
                      {{ item.status === 'ACTIVE' ? 'Hoạt động' : 'Ngừng hoạt động' }}
                    </span>
                  </td>
                  <td>
                    <button class="btn btn-warning btn-sm me-2" @click="edit(item)">
                      <i class="bi bi-pencil"></i> Sửa
                    </button>
                    <button class="btn btn-danger btn-sm" @click="remove(item.id)">
                      <i class="bi bi-trash"></i> Xóa
                    </button>
                  </td>
                </tr>
                <tr v-if="!list.length && !loading">
                  <td colspan="5" class="text-center text-muted py-4">
                    <i class="bi bi-inbox"></i> Chưa có dữ liệu
                  </td>
                </tr>
                <tr v-if="loading">
                  <td colspan="5" class="text-center py-4">
                    <div class="spinner-border text-primary" role="status">
                      <span class="visually-hidden">Đang tải...</span>
                    </div>
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