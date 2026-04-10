<script>
import axios from 'axios'

export default {
  data() {
    return {
      apiUrl: 'http://localhost:8080/api/shelves',
      form: {
        id: null,
        code: '',
        floor: '',
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
          code: item.code || item.name || '',
          floor: item.floor || item.location || '',
          status: item.status || 'ACTIVE',
        }))
      } catch (error) {
        this.message = 'Không thể tải danh sách kệ sách'
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
            code: this.form.code,
            floor: this.form.floor,
            status: this.form.status,
          },
          config,
        )
        this.reset()
        this.loadData()
      } catch (error) {
        this.message = 'Không thể thêm kệ sách'
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
            code: this.form.code,
            floor: this.form.floor,
            status: this.form.status,
          },
          config,
        )
        this.reset()
        this.loadData()
      } catch (error) {
        this.message = 'Không thể cập nhật kệ sách'
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
        this.message = 'Không thể xóa kệ sách'
      }
    },

    edit(item) {
      this.isEditing = true
      this.errors = {}
      this.message = ''
      this.form = {
        id: item.id,
        code: item.code,
        floor: item.floor,
        status: item.status,
      }
    },

    reset() {
      this.isEditing = false
      this.errors = {}
      this.message = ''
      this.form = {
        id: null,
        code: '',
        floor: '',
        status: 'ACTIVE',
      }
    },

    validate() {
      this.errors = {}

      if (!this.form.code || !this.form.code.trim()) {
        this.errors.code = 'Mã kệ không được để trống'
      }
      if (!this.form.floor || !this.form.floor.trim()) {
        this.errors.floor = 'Tầng không được để trống'
      }

      return Object.keys(this.errors).length === 0
    },
  },
}
</script>

<template>
  <div class="container py-4">
    <h4 class="mb-3">Quản lý kệ sách</h4>

    <div class="row g-3">
      <div class="col-12 col-lg-4">
        <div class="card">
          <div class="card-header">
            <strong>{{ isEditing ? 'Cập nhật kệ sách' : 'Thêm kệ sách' }}</strong>
          </div>
          <div class="card-body">
            <div class="mb-3">
              <label class="form-label">Mã kệ</label>
              <input v-model="form.code" type="text" class="form-control" />
              <div v-if="errors.code" class="text-danger small mt-1">{{ errors.code }}</div>
            </div>

            <div class="mb-3">
              <label class="form-label">Tầng</label>
              <input v-model="form.floor" type="text" class="form-control" />
              <div v-if="errors.floor" class="text-danger small mt-1">{{ errors.floor }}</div>
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
                  <th>Mã kệ</th>
                  <th>Tầng</th>
                  <th>Trạng thái</th>
                  <th>Thao tác</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in list" :key="item.id">
                  <td>{{ item.id }}</td>
                  <td>{{ item.code }}</td>
                  <td>{{ item.floor }}</td>
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
                  <td colspan="5" class="text-center text-muted py-3">
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
