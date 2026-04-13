<script>
import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

export default {
  data() {
    return {
      activeTab: 'form',
      apiUrl: `${API_BASE_URL}/books`,
      publisherApiUrl: `${API_BASE_URL}/publisher`,  // Sửa thành /publisher (không có 's')
      categoryApiUrl: `${API_BASE_URL}/categories`,
      list: [],
      publishers: [],
      categories: [],
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
        categories: [],
        status: 'ACTIVE',
      },
      errors: {},
      message: '',
      loading: false,
      isEditing: false,
      statusOptions: [
        { value: 'ACTIVE', label: 'Hoạt động' },
        { value: 'INACTIVE', label: 'Không hoạt động' },
      ],
    }
  },

  async mounted() {
    await this.loadPublishers()
    await this.loadCategories()
    await this.loadData()
  },

  methods: {
    async loadPublishers() {
      try {
        // Thử nhiều endpoint khác nhau
        let res
        try {
          res = await axios.get(`${API_BASE_URL}/publisher`)
        } catch (e) {
          try {
            res = await axios.get(`${API_BASE_URL}/publishers`)
          } catch (e2) {
            res = await axios.get(`${API_BASE_URL}/publisher/all`)
          }
        }
        
        console.log('Publishers response:', res.data)
        
        // Lấy dữ liệu từ response
        let rows = []
        if (Array.isArray(res.data)) {
          rows = res.data
        } else if (res.data?.content && Array.isArray(res.data.content)) {
          rows = res.data.content
        } else if (res.data?.data && Array.isArray(res.data.data)) {
          rows = res.data.data
        }
        
        this.publishers = rows.map((item) => ({
          id: item.id,
          name: item.name || item.publisherName || 'Không có tên',
        }))
        
        console.log('Publishers loaded:', this.publishers)
      } catch (error) {
        console.error('Lỗi tải publishers:', error)
        // Nếu không có API, dùng dữ liệu mẫu để test
        this.publishers = [
          { id: 1, name: 'Nhà xuất bản Kim Đồng' },
          { id: 2, name: 'Nhà xuất bản Trẻ' },
          { id: 3, name: 'Nhà xuất bản Giáo Dục' }
        ]
        this.message = 'Đang dùng dữ liệu mẫu cho nhà xuất bản'
      }
    },

    async loadCategories() {
      try {
        let res
        try {
          res = await axios.get(`${API_BASE_URL}/categories`)
        } catch (e) {
          res = await axios.get(`${API_BASE_URL}/category`)
        }
        
        console.log('Categories response:', res.data)
        
        let rows = []
        if (Array.isArray(res.data)) {
          rows = res.data
        } else if (res.data?.content && Array.isArray(res.data.content)) {
          rows = res.data.content
        } else if (res.data?.data && Array.isArray(res.data.data)) {
          rows = res.data.data
        }
        
        this.categories = rows.map((item) => ({
          id: item.id,
          name: item.name,
        }))
        
        console.log('Categories loaded:', this.categories)
      } catch (error) {
        console.error('Lỗi tải categories:', error)
        // Dữ liệu mẫu để test
        this.categories = [
          { id: 1, name: 'Khoa học' },
          { id: 2, name: 'Tiểu thuyết' },
          { id: 3, name: 'Kinh tế' },
          { id: 4, name: 'Giáo khoa' }
        ]
        this.message = 'Đang dùng dữ liệu mẫu cho thể loại'
      }
    },

    async loadData() {
      this.loading = true
      this.message = ''

      try {
        const res = await axios.get(this.apiUrl)
        console.log('Books response:', res.data)
        
        let rows = []
        if (Array.isArray(res.data)) {
          rows = res.data
        } else if (res.data?.content && Array.isArray(res.data.content)) {
          rows = res.data.content
        } else if (res.data?.data && Array.isArray(res.data.data)) {
          rows = res.data.data
        }

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
          publisherId: item.publisherId ?? item.publisher?.id ?? '',
          publisherName: item.publisherName ?? item.publisher?.name ?? this.getPublisherName(item.publisherId ?? item.publisher?.id),
          categories: item.categories || [],
          status: item.status || 'ACTIVE',
        }))
        
        console.log('Books loaded:', this.list.length)
      } catch (error) {
        console.error('Lỗi tải dữ liệu:', error)
        this.message = 'Không thể tải sách'
        // Dữ liệu mẫu để test
        this.list = [
          {
            id: 1,
            title: 'Harry Potter',
            isbn: '123456',
            language: 'English',
            edition: '1st',
            totalCopies: 5,
            availableCopies: 3,
            publisherId: 1,
            publisherName: 'NXB Kim Đồng',
            categories: ['Khoa học', 'Tiểu thuyết'],
            status: 'ACTIVE'
          }
        ]
      } finally {
        this.loading = false
      }
    },

    getPublisherName(publisherId) {
      if (!publisherId) return ''
      const found = this.publishers.find((item) => Number(item.id) === Number(publisherId))
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
          availableCopies: this.form.availableCopies === '' || this.form.availableCopies === null
            ? null
            : Number(this.form.availableCopies),
          popularityScore: Number(this.form.popularityScore),
          minLoanDays: Number(this.form.minLoanDays),
          maxLoanDays: Number(this.form.maxLoanDays),
          publisherId: this.form.publisherId ? Number(this.form.publisherId) : null,
          categories: this.form.categories,
          status: this.form.status,
        })

        this.reset()
        await this.loadData()
        this.activeTab = 'table'
        this.message = 'Thêm sách thành công!'
        setTimeout(() => this.message = '', 3000)
      } catch (error) {
        console.error('Lỗi thêm:', error)
        this.message = error.response?.data?.message || 'Không thể thêm sách'
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
          availableCopies: this.form.availableCopies === '' || this.form.availableCopies === null
            ? null
            : Number(this.form.availableCopies),
          popularityScore: Number(this.form.popularityScore),
          minLoanDays: Number(this.form.minLoanDays),
          maxLoanDays: Number(this.form.maxLoanDays),
          publisherId: this.form.publisherId ? Number(this.form.publisherId) : null,
          categories: this.form.categories,
          status: this.form.status,
        })

        this.reset()
        await this.loadData()
        this.activeTab = 'table'
        this.message = 'Cập nhật sách thành công!'
        setTimeout(() => this.message = '', 3000)
      } catch (error) {
        console.error('Lỗi cập nhật:', error)
        this.message = error.response?.data?.message || 'Không thể cập nhật sách'
        if (error.response?.data) {
          this.errors = error.response.data
        }
      }
    },

    async remove(id) {
      if (!window.confirm('Bạn có chắc muốn xóa sách này?')) return

      try {
        await axios.delete(`${this.apiUrl}/${id}`)
        await this.loadData()
        this.message = 'Xóa sách thành công!'
        setTimeout(() => this.message = '', 3000)
      } catch (error) {
        console.error('Lỗi xóa:', error)
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
        publisherId: item.publisherId ? String(item.publisherId) : '',
        categories: item.categories || [],
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
        categories: [],
        status: 'ACTIVE',
      }
    },
  },
}
</script>
<template>
  <div class="container py-4">
    <h4 class="mb-3">Quản lý sách</h4>

    <!-- Thông báo -->
    <div v-if="message" class="alert alert-success alert-dismissible fade show" role="alert">
      {{ message }}
      <button type="button" class="btn-close" @click="message = ''"></button>
    </div>

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

    <!-- Form Thêm/Sửa -->
    <div v-if="activeTab === 'form'" class="card">
      <div class="card-header">
        <strong>{{ isEditing ? 'Cập nhật sách' : 'Thêm sách mới' }}</strong>
      </div>
      <div class="card-body">
        <div class="row">
          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label">Tiêu đề <span class="text-danger">*</span></label>
              <input v-model="form.title" type="text" class="form-control" placeholder="Nhập tiêu đề sách" />
              <div v-if="errors.title" class="text-danger small mt-1">{{ errors.title }}</div>
            </div>
          </div>

          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label">ISBN</label>
              <input v-model="form.isbn" type="text" class="form-control" placeholder="Nhập ISBN" />
              <div v-if="errors.isbn" class="text-danger small mt-1">{{ errors.isbn }}</div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label">Ngôn ngữ</label>
              <input v-model="form.language" type="text" class="form-control" placeholder="VD: Tiếng Việt, English" />
            </div>
          </div>
          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label">Phiên bản</label>
              <input v-model="form.edition" type="text" class="form-control" placeholder="VD: Tái bản lần 1" />
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label">Tổng bản sao</label>
              <input v-model.number="form.totalCopies" type="number" min="0" class="form-control" />
              <div v-if="errors.totalCopies" class="text-danger small mt-1">{{ errors.totalCopies }}</div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label">Bản sao khả dụng</label>
              <input v-model.number="form.availableCopies" type="number" min="0" class="form-control" placeholder="Để trống nếu backend tự tính" />
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label">Độ phổ biến</label>
              <input v-model.number="form.popularityScore" type="number" min="0" class="form-control" />
            </div>
          </div>
          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label">Nhà xuất bản <span class="text-danger">*</span></label>
              <select v-model="form.publisherId" class="form-select">
                <option value="">-- Chọn nhà xuất bản --</option>
                <option v-for="item in publishers" :key="item.id" :value="item.id">
                  {{ item.name }}
                </option>
              </select>
              <div v-if="errors.publisherId" class="text-danger small mt-1">{{ errors.publisherId }}</div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label">Mượn tối thiểu (ngày)</label>
              <input v-model.number="form.minLoanDays" type="number" min="1" class="form-control" />
              <div v-if="errors.minLoanDays" class="text-danger small mt-1">{{ errors.minLoanDays }}</div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label">Mượn tối đa (ngày)</label>
              <input v-model.number="form.maxLoanDays" type="number" min="1" class="form-control" />
              <div v-if="errors.maxLoanDays" class="text-danger small mt-1">{{ errors.maxLoanDays }}</div>
            </div>
          </div>
        </div>

        <!-- Phần chọn thể loại -->
        <div class="mb-3">
          <label class="form-label">Thể loại</label>
          <select v-model="form.categories" class="form-select" multiple size="4">
            <option v-for="item in categories" :key="item.id" :value="item.name">
              {{ item.name }}
            </option>
          </select>
          <div class="form-text text-muted">Giữ Ctrl (hoặc Cmd) để chọn nhiều thể loại</div>
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
        <button v-if="!isEditing" class="btn btn-primary" @click="addItem">
          Thêm
        </button>
        <button v-else class="btn btn-primary" @click="updateItem">
          Cập nhật
        </button>
        <button class="btn btn-secondary" @click="reset">
          Làm mới
        </button>
      </div>
    </div>

    <!-- Bảng danh sách -->
    <div v-if="activeTab === 'table'" class="card">
      <div class="card-header">
        <strong>Danh sách sách</strong>
      </div>
      <div class="table-responsive">
        <table class="table table-bordered table-hover mb-0">
          <thead>
            <tr>
              <th>STT</th>
              <th>Tiêu đề</th>
              <th>ISBN</th>
              <th>Ngôn ngữ</th>
              <th>Phiên bản</th>
              <th>Tổng bản</th>
              <th>Khả dụng</th>
              <th>Thể loại</th>
              <th>Nhà XB</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in list" :key="item.id">
              <td class="text-center">{{ index + 1 }}</td>
              <td><strong>{{ item.title }}</strong></td>
              <td>{{ item.isbn || '--' }}</td>
              <td>{{ item.language || '--' }}</td>
              <td>{{ item.edition || '--' }}</td>
              <td class="text-center">{{ item.totalCopies }}</td>
              <td class="text-center">{{ item.availableCopies ?? 'N/A' }}</td>
              <td>
                <span v-if="item.categories && item.categories.length">
                  <span v-for="(cat, idx) in item.categories" :key="idx" class="badge bg-secondary me-1">
                    {{ cat }}
                  </span>
                </span>
                <span v-else class="text-muted">--</span>
              </td>
              <td>{{ item.publisherName || getPublisherName(item.publisherId) || '--' }}</td>
              <td class="text-center">
                <span class="badge" :class="item.status === 'ACTIVE' ? 'bg-success' : 'bg-secondary'">
                  {{ item.status === 'ACTIVE' ? 'Hoạt động' : 'Không hoạt động' }}
                </span>
              </td>
              <td class="text-center">
                <button class="btn btn-warning btn-sm me-1" @click="edit(item)" title="Sửa">
                  Sửa
                </button>
                <button class="btn btn-danger btn-sm" @click="remove(item.id)" title="Xóa">
                  Xóa
                </button>
              </td>
            </tr>
            <tr v-if="!list.length && !loading">
              <td colspan="11" class="text-center text-muted py-4">
                Chưa có dữ liệu
              </td>
            </tr>
            <tr v-if="loading">
              <td colspan="11" class="text-center py-4">
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
</template>

<style scoped>
.card {
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.btn-sm {
  border-radius: 5px;
}

.badge {
  padding: 5px 10px;
  font-size: 0.85em;
}

select[multiple] {
  min-height: 100px;
}

.nav-tabs .nav-link {
  cursor: pointer;
}
</style>