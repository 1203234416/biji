<template>
  <div class="login-page">
    <div class="login-card">
      <h1>我的笔记</h1>
      <p class="subtitle">输入用户名即可登录</p>
      <input
        v-model="username"
        class="input"
        placeholder="输入用户名"
        maxlength="20"
        @keyup.enter="doLogin"
      />
      <button class="btn" :disabled="!username.trim()" @click="doLogin">进入</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import * as userApi from '../api/user.js'

const emit = defineEmits(['login'])
const username = ref('')

async function doLogin() {
  const name = username.value.trim()
  if (!name) return
  const user = await userApi.login(name)
  if (user) {
    localStorage.setItem('biji_user_id', user.id)
    localStorage.setItem('biji_username', user.username)
    emit('login', user)
  }
}
</script>

<style scoped>
.login-page {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f0faf3;
}

.login-card {
  background: #fff;
  border-radius: 20px;
  padding: 48px 40px;
  width: 380px;
  text-align: center;
  box-shadow: 0 4px 24px rgba(0, 0, 0, 0.06);
}

h1 {
  font-size: 28px;
  color: #1b4332;
  margin-bottom: 8px;
}

.subtitle {
  font-size: 14px;
  color: #888;
  margin-bottom: 32px;
}

.input {
  width: 100%;
  padding: 12px 16px;
  border: 2px solid #d8f3dc;
  border-radius: 12px;
  font-size: 16px;
  font-family: inherit;
  outline: none;
  transition: border-color 0.15s;
  box-sizing: border-box;
}

.input:focus {
  border-color: #52b788;
}

.btn {
  width: 100%;
  margin-top: 16px;
  padding: 12px;
  border: none;
  background: #52b788;
  color: #fff;
  font-size: 16px;
  font-family: inherit;
  border-radius: 12px;
  cursor: pointer;
  transition: background 0.15s;
}

.btn:disabled {
  background: #b7d7c4;
  cursor: default;
}

.btn:not(:disabled):hover {
  background: #40916c;
}
</style>
