<script>
import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

export default {
  data() {
    return {
      activeTab: 'form',
      apiUrl: `${API_BASE_URL}/books`,
      publisherApiUrl: `${API_BASE_URL}/publisher`,
      list: [],
      publishers: [],
      form: {
        id: null,
        title: '',
        isbn: '',
        language: '',
        edition: '',
        totalCopies: 0,
        availableCopies: null,
        popularityScore: 0,
        minLoanDays: 1,
        maxLoanDays: 1,
        publisherId: '',
        status: 'AVAILABLE',
      },
      errors: {},
      message: '',
      loading: false,
      isEditing: false,
      statusOptions: [
        { value: 'AVAILABLE', label: 'Có sẵn' },
        { value: 'UNAVAILABLE', label: 'Không có sẵn' },
      ],
    }
  },

  async mounted() {
    await this.loadPublishers()
    await this.loadData()
  },

  methods: {
    async loadPublishers() {
      try {
        const res = await axios.get(this.publisherApiUrl)
        const rows = Array.isArray(res.data)
          ? res.data
          : Array.isArray(res.data?.content)
            ? res.data.content
            : []

        this.publishers = rows
          .filter((item) => item.status !== 'INACTIVE')
          .map((item) => ({
            id: String(item.id),
            name: item.name,
          }))
      } catch (error) {
        this.message = 'Không thể tải danh sách nhà xuất bản'
      }
    },

    async loadData() {
      this.loading = true
      this.message = ''

      try {
        const res = await axios.get(this.apiUrl)
        const rows = Array.isArray(res.data)
          ? res.data
          : Array.isArray(res.data?.content)
            ? res.data.content
            : []

        this.list = rows.map((item) => ({
          id: item.id,
          title: item.title || '',
          isbn: item.isbn || '',
          language: item.language || '',
          edition: item.edition || '',
          totalCopies: Number(item.totalCopies ?? 0),
          availableCopies: item.availableCopies ?? null,
          popularityScore: Number(item.popularityScore ?? 0),
          minLoanDays: Number(item.minLoanDays ?? 1),
          maxLoanDays: Number(item.maxLoanDays ?? 1),
          publisherId: String(item.publisherId ?? item.publisher_id ?? item.publisher?.id ?? ''),
          publisherName:
            item.publisherName ??
            item.publisher_name ??
            item.publisher?.name ??
            this.getPublisherName(item.publisherId ?? item.publisher_id ?? item.publisher?.id),
          status: item.status || 'AVAILABLE',
        }))
      } catch (error) {
        this.message = 'Không thể tải sách'
      } finally {
        this.loading = false
      }
    },

    getPublisherName(publisherId) {
      const found = this.publishers.find((item) => String(item.id) === String(publisherId))
      return found?.name || ''
    },

    async addItem() {
      this.errors = {}

      if (!this.form.publisherId) {
        this.errors.publisherId = 'Vui lòng chọn nhà xuất bản'
        return
      }

      try {
        await axios.post(this.apiUrl, {
          title: this.form.title,
          isbn: this.form.isbn,
          language: this.form.language,
          edition: this.form.edition,
          totalCopies: Number(this.form.totalCopies),
          availableCopies:
            this.form.availableCopies === '' || this.form.availableCopies === null
              ? null
              : Number(this.form.availableCopies),
          popularityScore: Number(this.form.popularityScore),
          minLoanDays: Number(this.form.minLoanDays),
          maxLoanDays: Number(this.form.maxLoanDays),
          publisherId: this.form.publisherId ? Number(this.form.publisherId) : null,
          status: this.form.status,
        })

        this.reset()
        await this.loadData()
        this.activeTab = 'table'
      } catch (error) {
        this.message = 'Không thể thêm sách'
        if (error.response?.data) {
          this.errors = error.response.data
        }
      }
    },

    async updateItem() {
      if (!this.form.id) return
      this.errors = {}

      if (!this.form.publisherId) {
        this.errors.publisherId = 'Vui lòng chọn nhà xuất bản'
        return
      }

      try {
        await axios.put(`${this.apiUrl}/${this.form.id}`, {
          title: this.form.title,
          isbn: this.form.isbn,
          language: this.form.language,
          edition: this.form.edition,
          totalCopies: Number(this.form.totalCopies),
          availableCopies:
            this.form.availableCopies === '' || this.form.availableCopies === null
              ? null
              : Number(this.form.availableCopies),
          popularityScore: Number(this.form.popularityScore),
          minLoanDays: Number(this.form.minLoanDays),
          maxLoanDays: Number(this.form.maxLoanDays),
          publisherId: this.form.publisherId ? Number(this.form.publisherId) : null,
          status: this.form.status,
        })

        this.reset()
        await this.loadData()
        this.activeTab = 'table'
      } catch (error) {
        this.message = 'Không thể cập nhật sách'
        if (error.response?.data) {
          this.errors = error.response.data
        }
      }
    },

    async remove(id) {
      if (!window.confirm('Bạn có chắc muốn xóa sách này?')) return

      try {
        await axios.delete(`${this.apiUrl}/${id}`)
        this.loadData()
      } catch (error) {
        this.message = 'Không thể xóa sách'
      }
    },

    edit(item) {
      this.isEditing = true
      this.activeTab = 'form'
      this.form = {
        id: item.id,
        title: item.title,
        isbn: item.isbn,
        language: item.language,
        edition: item.edition,
        totalCopies: item.totalCopies,
        availableCopies: item.availableCopies,
        popularityScore: item.popularityScore,
        minLoanDays: item.minLoanDays,
        maxLoanDays: item.maxLoanDays,
        publisherId: item.publisherId,
        status: item.status,
      }
    },

    reset() {
      this.isEditing = false
      this.errors = {}
      this.message = ''
      this.form = {
        id: null,
        title: '',
        isbn: '',
        language: '',
        edition: '',
        totalCopies: 0,
        availableCopies: null,
        popularityScore: 0,
        minLoanDays: 1,
        maxLoanDays: 1,
        publisherId: '',
        status: 'AVAILABLE',
      }
    },
  },
}
</script>

<template>
  <div class="container py-4">
    <h4 class="mb-3">Quản lý sách</h4>

    <ul class="nav nav-tabs mb-3">
      <li class="nav-item">
        <button
          class="nav-link"
          :class="{ active: activeTab === 'form' }"
          @click="activeTab = 'form'"
          type="button"
        >
          Biểu mẫu
        </button>
      </li>
      <li class="nav-item">
        <button
          class="nav-link"
          :class="{ active: activeTab === 'table' }"
          @click="activeTab = 'table'"
          type="button"
        >
          Danh sách
        </button>
      </li>
    </ul>

    <div v-if="message" class="alert alert-danger">{{ message }}</div>

    <div v-if="activeTab === 'form'" class="card">
      <div class="card-header">
        <strong>{{ isEditing ? 'Cập nhật sách' : 'Thêm sách' }}</strong>
      </div>
      <div class="card-body">
        <div class="mb-2">
          <label class="form-label">Tiêu đề</label>
          <input v-model="form.title" type="text" class="form-control" />
          <div v-if="errors.title" class="text-danger small mt-1">{{ errors.title }}</div>
        </div>

        <div class="mb-2">
          <label class="form-label">ISBN</label>
          <input v-model="form.isbn" type="text" class="form-control" />
          <div v-if="errors.isbn" class="text-danger small mt-1">{{ errors.isbn }}</div>
        </div>

        <div class="row g-2">
          <div class="col-6">
            <label class="form-label">Ngôn ngữ</label>
            <input v-model="form.language" type="text" class="form-control" />
          </div>
          <div class="col-6">
            <label class="form-label">Phiên bản</label>
            <input v-model="form.edition" type="text" class="form-control" />
          </div>
        </div>

        <div class="row g-2 mt-1">
          <div class="col-6">
            <label class="form-label">Tổng bản sao</label>
            <input v-model.number="form.totalCopies" type="number" min="0" class="form-control" />
            <div v-if="errors.totalCopies" class="text-danger small mt-1">
              {{ errors.totalCopies }}
            </div>
          </div>
          <div class="col-6">
            <label class="form-label">Bản sao khả dụng</label>
            <input
              v-model.number="form.availableCopies"
              type="number"
              min="0"
              class="form-control"
              placeholder="Để trống nếu backend tự tính"
            />
          </div>
        </div>

        <div class="row g-2 mt-1">
          <div class="col-6">
            <label class="form-label">Độ phổ biến</label>
            <input
              v-model.number="form.popularityScore"
              type="number"
              min="0"
              class="form-control"
            />
          </div>
          <div class="col-6"></div>
        </div>

        <div class="row g-2 mt-1">
          <div class="col-6">
            <label class="form-label">Mượn tối thiểu</label>
            <input v-model.number="form.minLoanDays" type="number" min="1" class="form-control" />
            <div v-if="errors.minLoanDays" class="text-danger small mt-1">
              {{ errors.minLoanDays }}
            </div>
          </div>
          <div class="col-6">
            <label class="form-label">Mượn tối đa</label>
            <input v-model.number="form.maxLoanDays" type="number" min="1" class="form-control" />
            <div v-if="errors.maxLoanDays" class="text-danger small mt-1">
              {{ errors.maxLoanDays }}
            </div>
          </div>
        </div>

        <div class="mb-2 mt-2">
          <label class="form-label">Nhà xuất bản</label>
          <select v-model="form.publisherId" class="form-select">
            <option value="">Chọn nhà xuất bản</option>
            <option v-for="item in publishers" :key="item.id" :value="item.id">
              {{ item.name }}
            </option>
          </select>
          <div v-if="errors.publisherId" class="text-danger small mt-1">
            {{ errors.publisherId }}
          </div>
        </div>

        <div class="mb-2">
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

    <div v-if="activeTab === 'table'" class="card">
      <div class="table-responsive">
        <table class="table table-bordered">
          <thead>
            <tr>
              <th>availableCopies</th>
              <th>edition</th>
              <th>id</th>
              <th>isbn</th>
              <th>language</th>
              <th>maxLoanDays</th>
              <th>minLoanDays</th>
              <th>popularityScore</th>
              <th>publisher</th>
              <th>status</th>
              <th>title</th>
              <th>totalCopies</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in list" :key="item.id">
              <td>{{ item.availableCopies ?? 'null' }}</td>
              <td>{{ item.edition }}</td>
              <td>{{ item.id }}</td>
              <td>{{ item.isbn }}</td>
              <td>{{ item.language }}</td>
              <td>{{ item.maxLoanDays }}</td>
              <td>{{ item.minLoanDays }}</td>
              <td>{{ item.popularityScore }}</td>
              <td>
                {{ item.publisherName || getPublisherName(item.publisherId) || item.publisherId || 'N/A' }}
              </td>
              <td>{{ item.status }}</td>
              <td>{{ item.title }}</td>
              <td>{{ item.totalCopies }}</td>
              <td>
                <button class="btn btn-warning btn-sm me-2" @click="edit(item)">Sửa</button>
                <button class="btn btn-danger btn-sm" @click="remove(item.id)">Xóa</button>
              </td>
            </tr>
            <tr v-if="!list.length">
              <td colspan="13" class="text-center text-muted py-3">
                {{ loading ? 'Đang tải dữ liệu...' : 'Chưa có dữ liệu' }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
