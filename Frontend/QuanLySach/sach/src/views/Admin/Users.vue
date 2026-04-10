<script setup>
import { users } from '../../data/mockData'
</script>

<template>
  <div>
    <div class="d-flex justify-content-between align-items-start flex-wrap gap-3 mb-4">
      <div>
        <h1 class="h3 fw-bold mb-1">Admin Nguoi dung</h1>
        <p class="text-muted mb-0">Quan ly nguoi dung da dang ky va trang thai thanh vien.</p>
      </div>
      <button type="button" class="btn btn-primary">Them nguoi dung</button>
    </div>

    <div class="card border-0 shadow-sm">
      <div class="table-responsive">
        <table class="table align-middle mb-0">
          <thead class="table-light">
            <tr>
              <th>Ten</th>
              <th>Email</th>
              <th>Vai tro</th>
              <th>Trang thai thanh vien</th>
              <th>Khoan dang muon</th>
              <th>So du tien phat</th>
              <th class="text-end">Thao tacs</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in users" :key="user.id">
              <td class="fw-semibold">{{ user.name }}</td>
              <td>{{ user.email }}</td>
              <td>
                <span
                  :class="[
                    'badge',
                    user.role === 'Admin'
                      ? 'text-bg-dark'
                      : user.role === 'Librarian'
                        ? 'text-bg-success'
                        : 'text-bg-primary',
                  ]"
                >
                  {{ user.role }}
                </span>
              </td>
              <td>
                <span
                  :class="
                    user.membershipStatus === 'Hoat dong'
                      ? 'badge text-bg-success'
                      : 'badge text-bg-warning text-dark'
                  "
                >
                  {{ user.membershipStatus }}
                </span>
              </td>
              <td>{{ user.currentLoans }}</td>
              <td>{{ user.penaltyBalance.toLocaleString() }} VND</td>
              <td class="text-end">
                <div class="btn-group btn-group-sm">
                  <button type="button" class="btn btn-outline-primary">Sua</button>
                  <button type="button" class="btn btn-outline-danger">Xoa</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
