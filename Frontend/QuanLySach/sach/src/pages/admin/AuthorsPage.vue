<script>
import axios from 'axios'

export default {
  data() {
    return {
      apiUrl: 'http://localhost:8080/api/authors',
      form: {
        id: null,
        name: '',
        activeYears: 0,
        awardsCount: 0,
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
        const res = await axios.get(this.apiUrl, config)
        const rows = Array.isArray(res.data)
          ? res.data
          : Array.isArray(res.data?.content)
            ? res.data.content
            : []

        this.list = rows.map((item) => ({
          id: item.id,
          name: item.name || '',
          activeYears: Number(item.activeYears ?? item.active_years ?? 0),
          awardsCount: Number(item.awardsCount ?? item.awards_count ?? 0),
          status: item.status || 'ACTIVE',
        }))
      } catch (error) {
        this.message = 'Không thể tải danh sách tác giả'
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
            activeYears: Number(this.form.activeYears),
            awardsCount: Number(this.form.awardsCount),
            status: this.form.status,
          },
          config,
        )
        this.reset()
        this.loadData()
      } catch (error) {
        this.message = 'Không thể thêm tác giả'
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
            activeYears: Number(this.form.activeYears),
            awardsCount: Number(this.form.awardsCount),
            status: this.form.status,
          },
          config,
        )
        this.reset()
        this.loadData()
      } catch (error) {
        this.message = 'Không thể cập nhật tác giả'
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
        this.message = 'Không thể xóa tác giả'
      }
    },

    edit(item) {
      this.isEditing = true
      this.errors = {}
      this.message = ''
      this.form = {
        id: item.id,
        name: item.name,
        activeYears: item.activeYears,
        awardsCount: item.awardsCount,
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
        activeYears: 0,
        awardsCount: 0,
        status: 'ACTIVE',
      }
    },

    validate() {
      this.errors = {}

      if (!this.form.name || !this.form.name.trim()) {
        this.errors.name = 'Tên không được để trống'
      }
      if (Number(this.form.activeYears) < 0) {
        this.errors.activeYears = 'Số năm hoạt động phải lớn hơn hoặc bằng 0'
      }
      if (Number(this.form.awardsCount) < 0) {
        this.errors.awardsCount = 'Số giải thưởng phải lớn hơn hoặc bằng 0'
      }

      return Object.keys(this.errors).length === 0
    },
  },
}
</script>

<template>
  <div class="container py-4">
    <h4 class="mb-3">Quản lý tác giả</h4>

    <div class="row g-3">
      <div class="col-12 col-lg-4">
        <div class="card">
          <div class="card-header">
            <strong>{{ isEditing ? 'Cập nhật tác giả' : 'Thêm tác giả' }}</strong>
          </div>
          <div class="card-body">
            <div class="mb-3">
              <label class="form-label">Tên tác giả</label>
              <input v-model="form.name" type="text" class="form-control" />
              <div v-if="errors.name" class="text-danger small mt-1">{{ errors.name }}</div>
            </div>

            <div class="mb-3">
              <label class="form-label">Số năm hoạt động</label>
              <input v-model.number="form.activeYears" type="number" min="0" class="form-control" />
              <div v-if="errors.activeYears" class="text-danger small mt-1">
                {{ errors.activeYears }}
              </div>
            </div>

            <div class="mb-3">
              <label class="form-label">Số giải thưởng</label>
              <input v-model.number="form.awardsCount" type="number" min="0" class="form-control" />
              <div v-if="errors.awardsCount" class="text-danger small mt-1">
                {{ errors.awardsCount }}
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
                  <th>Số năm hoạt động</th>
                  <th>Số giải thưởng</th>
                  <th>Trạng thái</th>
                  <th>Thao tác</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in list" :key="item.id">
                  <td>{{ item.id }}</td>
                  <td>{{ item.name }}</td>
                  <td>{{ item.activeYears }}</td>
                  <td>{{ item.awardsCount }}</td>
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
