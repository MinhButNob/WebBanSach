<script setup>
import { computed, ref } from 'vue'
import { reportResults } from '../../data/mockData'

const reportType = ref('Sach duoc muon nhieu nhat')
const startDate = ref('2024-01-01')
const endDate = ref('2024-03-31')
const generated = ref(true)

const results = computed(() => reportResults[reportType.value] ?? [])

const generateReport = () => {
  generated.value = true
}
</script>

<template>
  <div>
    <div class="d-flex justify-content-between align-items-start flex-wrap gap-3 mb-4">
      <div>
        <h1 class="h3 fw-bold mb-1">Admin Bao cao</h1>
        <p class="text-muted mb-0">Tao bao cao su dung va tai chinh theo khoang ngay da chon.</p>
      </div>
      <button type="button" class="btn btn-primary" @click="generateReport">Tao bao cao</button>
    </div>

    <div class="card border-0 shadow-sm mb-4">
      <div class="card-body">
        <div class="row g-3">
          <div class="col-12 col-md-4">
            <label class="form-label">Tu ngay</label>
            <input v-model="startDate" type="date" class="form-control" />
          </div>
          <div class="col-12 col-md-4">
            <label class="form-label">Den ngay</label>
            <input v-model="endDate" type="date" class="form-control" />
          </div>
          <div class="col-12 col-md-4">
            <label class="form-label">Loai bao cao</label>
            <select v-model="reportType" class="form-select">
              <option>Sach duoc muon nhieu nhat</option>
              <option>Nguoi dung hoat dong nhieu nhat</option>
              <option>Thong ke qua han</option>
              <option>Doanh thu tu tien phat</option>
            </select>
          </div>
        </div>
      </div>
    </div>

    <div v-if="generated" class="card border-0 shadow-sm">
      <div
        class="card-header bg-white border-0 py-3 d-flex justify-content-between align-items-center flex-wrap gap-2"
      >
        <h2 class="h5 mb-0">Ket qua</h2>
        <span class="text-muted small">{{ startDate }} den {{ endDate }}</span>
      </div>
      <div class="table-responsive">
        <table class="table align-middle mb-0">
          <thead class="table-light">
            <tr>
              <th>Hang muc</th>
              <th>Gia tri</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="result in results" :key="result.label">
              <td class="fw-semibold">{{ result.label }}</td>
              <td>{{ result.value }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
