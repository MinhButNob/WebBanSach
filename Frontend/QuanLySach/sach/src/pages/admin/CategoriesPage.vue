<script>
import axios from 'axios'

export default {
  data() {
    return {
      apiUrl: 'http://localhost:8080/api/categories',
      form: {
        id: null,
        name: '',
        averageLoanDays: 1,
        totalBooks: 0,
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
          name: item.name || '',
          averageLoanDays: Number(item.averageLoanDays ?? item.average_loan_days ?? 1),
          totalBooks: Number(item.totalBooks ?? item.total_books ?? 0),
          status: item.status || 'ACTIVE',
        }))
      } catch (error) {
        this.message = 'Không thể tải danh sách thể loại'
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
            name: this.form.name,
            averageLoanDays: Number(this.form.averageLoanDays),
            totalBooks: Number(this.form.totalBooks),
            status: this.form.status,
          },
          config,
        )
        this.reset()
        this.loadData()
      } catch (error) {
        this.message = 'Không thể thêm thể loại'
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
            name: this.form.name,
            averageLoanDays: Number(this.form.averageLoanDays),
            totalBooks: Number(this.form.totalBooks),
            status: this.form.status,
          },
          config,
        )
        this.reset()
        this.loadData()
      } catch (error) {
        this.message = 'Không thể cập nhật thể loại'
      }
    },

    async remove(id) {
      if (!window.confirm('Bạn có chắc chắn muốn xóa?')) return

      const token = localStorage.getItem('token')
      const config = token ? { headers: { Authorization: `Bearer ${token}` } } : {}

      try {
        await axios.delete(`${this.apiUrl}/${id}`, config)
        this.loadData()
      } catch (error) {
        this.message = 'Không thể xóa thể loại'
      }
    },

    edit(item) {
      this.isEditing = true
      this.errors = {}
      this.message = ''
      this.form = {
        id: item.id,
        name: item.name,
        averageLoanDays: item.averageLoanDays,
        totalBooks: item.totalBooks,
        status: item.status,
      }
    },

    reset() {
      this.isEditing = false
      this.errors = {}
      this.message = ''
      this.form = {
        id: null,
        name: '',
        averageLoanDays: 1,
        totalBooks: 0,
        status: 'ACTIVE',
      }
    },

    validate() {
      this.errors = {}

      if (!this.form.name || !this.form.name.trim()) {
        this.errors.name = 'Tên không được để trống'
      }
      if (Number(this.form.averageLoanDays) <= 0) {
        this.errors.averageLoanDays = 'Số ngày mượn phải lớn hơn 0'
      }
      if (Number(this.form.totalBooks) < 0) {
        this.errors.totalBooks = 'Tổng số sách phải lớn hơn hoặc bằng 0'
      }

      return Object.keys(this.errors).length === 0
    },
  },
}
</script>

<template>
  <div class="container py-4">
    <h4 class="mb-3">Quản lý thể loại</h4>

    <div class="row g-3">
      <div class="col-12 col-lg-4">
        <div class="card">
          <div class="card-header">
            <strong>{{ isEditing ? 'Cập nhật thể loại' : 'Thêm thể loại' }}</strong>
          </div>
          <div class="card-body">
            <div class="mb-3">
              <label class="form-label">Tên thể loại</label>
              <input v-model="form.name" type="text" class="form-control" />
              <div v-if="errors.name" class="text-danger small mt-1">{{ errors.name }}</div>
            </div>

            <div class="mb-3">
              <label class="form-label">Số ngày mượn trung bình</label>
              <input
                v-model.number="form.averageLoanDays"
                type="number"
                min="1"
                class="form-control"
              />
              <div v-if="errors.averageLoanDays" class="text-danger small mt-1">
                {{ errors.averageLoanDays }}
              </div>
            </div>

            <div class="mb-3">
              <label class="form-label">Tổng số sách</label>
              <input v-model.number="form.totalBooks" type="number" min="0" class="form-control" />
              <div v-if="errors.totalBooks" class="text-danger small mt-1">
                {{ errors.totalBooks }}
              </div>
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
        <div v-if="message" class="alert alert-danger">{{ message }}</div>

        <div class="card">
          <div class="table-responsive">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Tên</th>
                  <th>Số ngày mượn</th>
                  <th>Tổng số sách</th>
                  <th>Trạng thái</th>
                  <th>Thao tác</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in list" :key="item.id">
                  <td>{{ item.id }}</td>
                  <td>{{ item.name }}</td>
                  <td>{{ item.averageLoanDays }}</td>
                  <td>{{ item.totalBooks }}</td>
                  <td>
                    <span
                      class="badge"
                      :class="item.status === 'ACTIVE' ? 'bg-success' : 'bg-secondary'"
                    >
                      {{ item.status }}
                    </span>
                  </td>
                  <td>
                    <button class="btn btn-warning btn-sm me-2" @click="edit(item)">Sửa</button>
                    <button class="btn btn-danger btn-sm" @click="remove(item.id)">Xóa</button>
                  </td>
                </tr>
                <tr v-if="!list.length">
                  <td colspan="6" class="text-center text-muted py-3">
                    {{ loading ? 'Đang tải dữ liệu...' : 'Chưa có dữ liệu' }}
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
