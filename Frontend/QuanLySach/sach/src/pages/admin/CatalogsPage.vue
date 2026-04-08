<script setup>
import { computed, ref } from 'vue'
import BaseModal from '@/components/common/BaseModal.vue'
import DataTable from '@/components/common/DataTable.vue'
import EntityForm from '@/components/common/EntityForm.vue'
import { catalogsSeed } from '@/data/featureData'

const tabs = [
  { key: 'authors', label: 'Tac gia' },
  { key: 'categories', label: 'The loai' },
  { key: 'publishers', label: 'Nha xuat ban' },
  { key: 'shelves', label: 'Ke sach' },
]

const activeTab = ref('authors')
const catalogs = ref(structuredClone(catalogsSeed))
const showModal = ref(false)
const editingId = ref(null)
const form = ref({ name: '', code: '', floor: '' })

const columns = computed(() => {
  if (activeTab.value === 'shelves') {
    return [
      { key: 'code', label: 'Ma ke' },
      { key: 'floor', label: 'Tang' },
    ]
  }

  return [{ key: 'name', label: 'Ten' }]
})

const fields = computed(() => {
  if (activeTab.value === 'shelves') {
    return [
      { key: 'code', label: 'Ma ke', col: 'col-12 col-md-6' },
      { key: 'floor', label: 'Tang', col: 'col-12 col-md-6' },
    ]
  }

  return [{ key: 'name', label: 'Ten', col: 'col-12' }]
})

const currentRows = computed(() => catalogs.value[activeTab.value])

const openCreate = () => {
  editingId.value = null
  form.value = activeTab.value === 'shelves' ? { code: '', floor: '' } : { name: '' }
  showModal.value = true
}

const openEdit = (row) => {
  editingId.value = row.id
  form.value =
    activeTab.value === 'shelves' ? { code: row.code, floor: row.floor } : { name: row.name }
  showModal.value = true
}

const saveItem = () => {
  const list = catalogs.value[activeTab.value]

  if (editingId.value) {
    catalogs.value[activeTab.value] = list.map((item) =>
      item.id === editingId.value ? { ...item, ...form.value } : item,
    )
  } else {
    const nextId = Math.max(...list.map((item) => item.id), 0) + 1
    catalogs.value[activeTab.value] = [{ id: nextId, ...form.value }, ...list]
  }

  showModal.value = false
}

const deleteItem = (id) => {
  catalogs.value[activeTab.value] = catalogs.value[activeTab.value].filter((item) => item.id !== id)
}
</script>

<template>
  <div class="container-fluid py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h1 class="h4 mb-0">Quan ly danh muc</h1>
      <button class="btn btn-primary" @click="openCreate">Them moi</button>
    </div>

    <ul class="nav nav-tabs mb-3">
      <li v-for="tab in tabs" :key="tab.key" class="nav-item">
        <button
          class="nav-link"
          :class="{ active: activeTab === tab.key }"
          @click="activeTab = tab.key"
        >
          {{ tab.label }}
        </button>
      </li>
    </ul>

    <div class="card">
      <div class="card-body p-0">
        <DataTable :columns="columns" :rows="currentRows" empty-text="Chua co du lieu danh muc">
          <template #actions="{ row }">
            <div class="btn-group btn-group-sm">
              <button class="btn btn-outline-primary" @click="openEdit(row)">Sua</button>
              <button class="btn btn-outline-danger" @click="deleteItem(row.id)">Xoa</button>
            </div>
          </template>
        </DataTable>
      </div>
    </div>

    <BaseModal
      :show="showModal"
      :title="editingId ? 'Cap nhat danh muc' : 'Them danh muc'"
      @close="showModal = false"
    >
      <EntityForm v-model="form" :fields="fields" @submit="saveItem" />
      <template #footer>
        <button class="btn btn-secondary" @click="showModal = false">Huy</button>
        <button class="btn btn-primary" @click="saveItem">Luu</button>
      </template>
    </BaseModal>
  </div>
</template>
