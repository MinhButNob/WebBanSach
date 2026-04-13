<template>
  <div class="container py-4">
    <h4 class="mb-3">Quản lý bản sao sách</h4>

    <!-- Hiển thị thông báo -->
    <div v-if="message" class="alert alert-success alert-dismissible fade show" role="alert">
      {{ message }}
      <button type="button" class="btn-close" @click="message = ''"></button>
    </div>

    <div class="row g-3">
      <div class="col-12 col-lg-4">
        <div class="card">
          <div class="card-header">
            <strong>{{ isEditing ? 'Cập nhật bản sao sách' : 'Thêm bản sao sách' }}</strong>
          </div>
          <div class="card-body">
            <div class="mb-3">
              <label class="form-label">Sách</label>
              <select v-model="form.bookId" class="form-select">
                <option value="">Chọn sách</option>
                <option v-for="item in books" :key="item.id" :value="item.id">
                  {{ item.title }}
                </option>
              </select>
              <div v-if="errors.bookId" class="text-danger small mt-1">{{ errors.bookId }}</div>
            </div>

            <div class="mb-3">
              <label class="form-label">Mã bản sao</label>
              <input v-model="form.barcode" type="text" class="form-control" />
              <div v-if="errors.barcode" class="text-danger small mt-1">{{ errors.barcode }}</div>
            </div>

            <div class="mb-3">
              <label class="form-label">Kệ sách</label>
              <select v-model="form.shelfId" class="form-select">
                <option value="">Chọn kệ sách</option>
                <option v-for="item in shelves" :key="item.id" :value="item.id">
                  {{ item.name }}
                </option>
              </select>
              <div v-if="errors.shelfId" class="text-danger small mt-1">{{ errors.shelfId }}</div>
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
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th>STT</th>
                  <th>Sách</th>
                  <th>Mã bản sao</th>
                  <th>Kệ sách</th>
                  <th>Trạng thái</th>
                  <th>Thao tác</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in list" :key="item.id">
                  <td class="text-center">{{ index + 1 }}</td>
                  <td>
                    {{ getBookTitle(item.bookId) }}
                  </td>
                  <td><code>{{ item.barcode }}</code></td>
                  <td>
                    {{ getShelfName(item.shelfId) }}
                  </td>
                  <td>
                    <span
                      class="badge"
                      :class="{
                        'bg-success': item.status === 'ACTIVE',
                        'bg-warning': item.status === 'BORROWED',
                        'bg-danger': item.status === 'LOST',
                        'bg-secondary': item.status === 'INACTIVE'
                      }"
                    >
                      <i v-if="item.status === 'ACTIVE'" class="bi bi-check-circle"></i>
                      <i v-else-if="item.status === 'BORROWED'" class="bi bi-book"></i>
                      <i v-else-if="item.status === 'LOST'" class="bi bi-exclamation-triangle"></i>
                      <i v-else class="bi bi-ban"></i>
                      {{
                        item.status === 'ACTIVE' ? 'Hoạt động' :
                        item.status === 'BORROWED' ? 'Đang mượn' :
                        item.status === 'LOST' ? 'Đã mất' : 'Không hoạt động'
                      }}
                    </span>
                  </td>
                  <td>
                    <button class="btn btn-warning btn-sm me-2" @click="edit(item)">Sửa</button>
                    <button class="btn btn-danger btn-sm" @click="remove(item.id)">Xóa</button>
                  </td>
                </tr>
                <tr v-if="!list.length && !loading">
                  <td colspan="6" class="text-center text-muted py-3">
                    <i class="bi bi-inbox"></i> Chưa có dữ liệu
                  </td>
                </tr>
                <tr v-if="loading">
                  <td colspan="6" class="text-center py-3">
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

<script>
import axios from 'axios'

export default {
  data() {
    return {
      apiUrl: 'http://localhost:8080/api/book-copies',
      form: {
        id: null,
        bookId: '',
        barcode: '',
        shelfId: '',
        status: 'ACTIVE',
      },
      list: [],
      errors: {},
      message: '',
      loading: false,
      isEditing: false,
      statusOptions: [
        { value: 'ACTIVE', label: 'Hoạt động' },
        { value: 'INACTIVE', label: 'Không hoạt động' },
        { value: 'BORROWED', label: 'Đang mượn' },
        { value: 'LOST', label: 'Đã mất' },
      ],
      books: [],
      shelves: [],
    }
  },

  async mounted() {
    await this.loadReferences()
    await this.loadData()
  },

  methods: {
    async loadReferences() {
      const token = localStorage.getItem('token')
      const config = token ? { headers: { Authorization: `Bearer ${token}` } } : {}

      try {
        const [bookRes, shelfRes] = await Promise.all([
          axios.get('http://localhost:8080/api/books', config),
          axios.get('http://localhost:8080/api/shelves', config),
        ])

        // Lưu books với key là id để tra cứu nhanh
        const bookRows = Array.isArray(bookRes.data) ? bookRes.data : []
        const shelfRows = Array.isArray(shelfRes.data) ? shelfRes.data : []
        
        // Tạo map để tra cứu nhanh
        this.booksMap = new Map()
        bookRows.forEach(book => {
          this.booksMap.set(Number(book.id), book.title || 'Không xác định')
        })
        
        this.shelvesMap = new Map()
        shelfRows.forEach(shelf => {
          this.shelvesMap.set(Number(shelf.id), shelf.name || 'Không xác định')
        })
        
        // Giữ lại mảng books và shelves cho dropdown
        this.books = bookRows.map(book => ({
          id: Number(book.id),
          title: book.title || ''
        }))
        
        this.shelves = shelfRows.map(shelf => ({
          id: Number(shelf.id),
          name: shelf.name || ''
        }))
        
        console.log('Books loaded:', this.books.length)
        console.log('Shelves loaded:', this.shelves.length)
      } catch (error) {
        console.error('Lỗi tải reference:', error)
        this.message = 'Không thể tải dữ liệu tham chiếu'
      }
    },

    async loadData() {
      this.loading = true
      this.message = ''

      const token = localStorage.getItem('token')
      const config = token ? { headers: { Authorization: `Bearer ${token}` } } : {}

      try {
        const res = await axios.get(this.apiUrl, config)
        
        // Đảm bảo dữ liệu list có id là number
        this.list = (Array.isArray(res.data) ? res.data : []).map(item => ({
          id: Number(item.id),
          bookId: item.bookId ? Number(item.bookId) : null,
          barcode: item.barcode || '',
          shelfId: item.shelfId ? Number(item.shelfId) : null,
          status: item.status || 'ACTIVE',
        }))
        
        console.log('Book copies loaded:', this.list.length)
      } catch (error) {
        console.error('Lỗi tải dữ liệu:', error)
        this.message = 'Không thể tải danh sách bản sao sách'
      } finally {
        this.loading = false
      }
    },

    // Lấy tên sách từ bookId
    getBookTitle(bookId) {
      if (!bookId) return 'Không xác định'
      const book = this.books.find(b => b.id === bookId)
      return book?.title || 'Không xác định'
    },

    // Lấy tên kệ từ shelfId
    getShelfName(shelfId) {
      if (!shelfId) return 'Không xác định'
      const shelf = this.shelves.find(s => s.id === shelfId)
      return shelf?.name || 'Không xác định'
    },

    async addItem() {
      if (!this.validate()) return

      const token = localStorage.getItem('token')
      const config = token ? { headers: { Authorization: `Bearer ${token}` } } : {}

      try {
        await axios.post(this.apiUrl, {
          bookId: Number(this.form.bookId),
          barcode: this.form.barcode,
          shelfId: this.form.shelfId ? Number(this.form.shelfId) : null,
          status: this.form.status,
        }, config)
        
        this.reset()
        await this.loadData()
        this.message = 'Thêm bản sao sách thành công!'
        setTimeout(() => this.message = '', 3000)
      } catch (error) {
        console.error('Lỗi thêm:', error)
        this.message = error.response?.data?.message || 'Không thể thêm bản sao sách'
      }
    },

    async updateItem() {
      if (!this.validate() || !this.form.id) return

      const token = localStorage.getItem('token')
      const config = token ? { headers: { Authorization: `Bearer ${token}` } } : {}

      try {
        await axios.put(`${this.apiUrl}/${this.form.id}`, {
          bookId: Number(this.form.bookId),
          barcode: this.form.barcode,
          shelfId: this.form.shelfId ? Number(this.form.shelfId) : null,
          status: this.form.status,
        }, config)
        
        this.reset()
        await this.loadData()
        this.message = 'Cập nhật bản sao sách thành công!'
        setTimeout(() => this.message = '', 3000)
      } catch (error) {
        console.error('Lỗi cập nhật:', error)
        this.message = error.response?.data?.message || 'Không thể cập nhật bản sao sách'
      }
    },

    async remove(id) {
      if (!confirm('Bạn có chắc chắn muốn xóa bản sao sách này?')) return

      const token = localStorage.getItem('token')
      const config = token ? { headers: { Authorization: `Bearer ${token}` } } : {}

      try {
        await axios.delete(`${this.apiUrl}/${id}`, config)
        await this.loadData()
        this.message = 'Xóa bản sao sách thành công!'
        setTimeout(() => this.message = '', 3000)
      } catch (error) {
        console.error('Lỗi xóa:', error)
        this.message = error.response?.data?.message || 'Không thể xóa bản sao sách'
      }
    },

    edit(item) {
      this.isEditing = true
      this.errors = {}
      this.message = ''
      this.form = {
        id: item.id,
        bookId: item.bookId,
        barcode: item.barcode,
        shelfId: item.shelfId,
        status: item.status,
      }
    },

    reset() {
      this.isEditing = false
      this.errors = {}
      this.message = ''
      this.form = {
        id: null,
        bookId: '',
        barcode: '',
        shelfId: '',
        status: 'ACTIVE',
      }
    },

    validate() {
      this.errors = {}

      if (!this.form.bookId) {
        this.errors.bookId = 'Vui lòng chọn sách'
      }
      if (!this.form.barcode || !this.form.barcode.trim()) {
        this.errors.barcode = 'Mã bản sao không được để trống'
      }
      if (!this.form.shelfId) {
        this.errors.shelfId = 'Vui lòng chọn kệ sách'
      }

      return Object.keys(this.errors).length === 0
    },
  },
}
</script>

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

.badge i {
  margin-right: 4px;
}

code {
  background-color: #f8f9fa;
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 0.9em;
}

.table th {
  background-color: #f8f9fa;
  font-weight: 600;
}

.nav-tabs .nav-link {
  cursor: pointer;
}
</style>