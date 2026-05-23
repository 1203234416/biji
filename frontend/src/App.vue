<template>
  <LoginView v-if="!user" @login="onLogin" />
  <div class="app" v-else>
    <TopBar :username="user.username" @logout="onLogout" />
    <div class="body">
      <NoteList
        :notes="notes"
        :activeId="activeId"
        @select="selectNote"
        @create="createNote"
        @delete="confirmDelete"
      />
      <NoteEditor
        :note="currentNote"
        @update="handleUpdate"
      />
    </div>
    <div class="overlay" v-if="showDeleteConfirm" @click="showDeleteConfirm = false">
      <div class="dialog" @click.stop>
        <h3>确认删除</h3>
        <p>确定要删除笔记「{{ deletingNote?.title }}」吗？此操作不可撤销。</p>
        <div class="dialog-actions">
          <button class="btn-cancel" @click="showDeleteConfirm = false">取消</button>
          <button class="btn-confirm" @click="doDelete">确认删除</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import LoginView from './components/LoginView.vue'
import TopBar from './components/TopBar.vue'
import NoteList from './components/NoteList.vue'
import NoteEditor from './components/NoteEditor.vue'
import * as api from './api/note.js'

const user = ref(null)
const notes = ref([])
const activeId = ref('')
const showDeleteConfirm = ref(false)
const deletingNote = ref(null)

const currentNote = computed(() => {
  return notes.value.find(n => n.id === activeId.value) || null
})

onMounted(() => {
  const id = localStorage.getItem('biji_user_id')
  const username = localStorage.getItem('biji_username')
  if (id && username) {
    user.value = { id, username }
    loadNotes()
  }
})

async function loadNotes() {
  await loadNotes()
}

function onLogin(u) {
  user.value = u
  loadNotes()
}

function onLogout() {
  localStorage.removeItem('biji_user_id')
  localStorage.removeItem('biji_username')
  user.value = null
  notes.value = []
  activeId.value = ''
}

async function selectNote(id) {
  activeId.value = id
}

async function createNote() {
  const note = await api.createNote('未命名笔记', '')
  await loadNotes()
  activeId.value = note.id
}

function confirmDelete(id) {
  deletingNote.value = notes.value.find(n => n.id === id)
  showDeleteConfirm.value = true
}

async function doDelete() {
  if (deletingNote.value) {
    await api.deleteNote(deletingNote.value.id)
    if (activeId.value === deletingNote.value.id) {
      activeId.value = ''
    }
    await loadNotes()
  }
  showDeleteConfirm.value = false
  deletingNote.value = null
}

async function handleUpdate({ title, content }) {
  if (!activeId.value) return
  await api.updateNote(activeId.value, title, content)
  await loadNotes()
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body, #app {
  height: 100%;
  font-family: 'Noto Sans SC', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  background: #f0faf3;
  color: #333;
}

::-webkit-scrollbar {
  width: 6px;
}

::-webkit-scrollbar-track {
  background: transparent;
}

::-webkit-scrollbar-thumb {
  background: #b7d7c4;
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: #95d5b2;
}
</style>

<style scoped>
.app {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.body {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.35);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  backdrop-filter: blur(2px);
}

.dialog {
  background: #fff;
  border-radius: 16px;
  padding: 32px;
  width: 400px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

.dialog h3 {
  font-size: 18px;
  color: #1b4332;
  margin-bottom: 12px;
}

.dialog p {
  font-size: 14px;
  color: #666;
  line-height: 1.6;
  margin-bottom: 24px;
}

.dialog-actions {
  display: flex;
  gap: 12px;
  justify-content: flex-end;
}

.btn-cancel {
  padding: 8px 20px;
  border: 1px solid #d8f3dc;
  background: #fff;
  color: #666;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  font-family: inherit;
  transition: all 0.15s;
}

.btn-cancel:hover {
  background: #f0faf3;
}

.btn-confirm {
  padding: 8px 20px;
  border: none;
  background: #e74c3c;
  color: #fff;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  font-family: inherit;
  transition: all 0.15s;
}

.btn-confirm:hover {
  background: #c0392b;
}
</style>
