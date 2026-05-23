<template>
  <div class="admin-page">
    <div class="admin-card" v-if="!authed">
      <h1>Admin</h1>
      <input
        v-model="password"
        class="input"
        type="password"
        placeholder="密码"
        @keyup.enter="doLogin"
      />
      <button class="btn" @click="doLogin">进入</button>
      <p class="error" v-if="error">{{ error }}</p>
    </div>
    <div class="admin-card wide" v-else>
      <h1>用户统计</h1>
      <table>
        <thead>
          <tr><th>用户名</th><th>笔记数</th></tr>
        </thead>
        <tbody>
          <tr v-for="u in users" :key="u.username">
            <td>{{ u.username }}</td>
            <td>{{ u.count }}</td>
          </tr>
        </tbody>
      </table>
      <p class="total">共 {{ users.length }} 位用户</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import * as adminApi from '../api/admin.js'

const password = ref('')
const error = ref('')
const authed = ref(false)
const users = ref([])

async function doLogin() {
  const data = await adminApi.getStats(password.value)
  if (data.error) {
    error.value = '密码错误'
    return
  }
  authed.value = true
  users.value = data.users
}
</script>

<style scoped>
.admin-page {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #1a1a2e;
}

.admin-card {
  background: #16213e;
  border-radius: 16px;
  padding: 40px;
  width: 360px;
  text-align: center;
  box-shadow: 0 8px 32px rgba(0,0,0,0.4);
}

.admin-card.wide {
  width: 500px;
}

h1 {
  color: #e94560;
  font-size: 24px;
  margin-bottom: 24px;
}

.input {
  width: 100%;
  padding: 12px 16px;
  border: 2px solid #0f3460;
  border-radius: 8px;
  background: #1a1a2e;
  color: #eee;
  font-size: 16px;
  font-family: inherit;
  outline: none;
  box-sizing: border-box;
}

.input:focus {
  border-color: #e94560;
}

.btn {
  width: 100%;
  margin-top: 16px;
  padding: 12px;
  border: none;
  background: #e94560;
  color: #fff;
  font-size: 16px;
  font-family: inherit;
  border-radius: 8px;
  cursor: pointer;
}

.btn:hover {
  background: #c23152;
}

.error {
  color: #e94560;
  margin-top: 12px;
  font-size: 14px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px 16px;
  text-align: left;
  border-bottom: 1px solid #0f3460;
  color: #ccc;
  font-size: 14px;
}

th {
  color: #e94560;
  font-weight: 500;
}

.total {
  margin-top: 20px;
  color: #888;
  font-size: 14px;
}
</style>
