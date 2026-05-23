<template>
  <main class="editor" v-if="note">
    <div class="editor-header">
      <input
        class="title-input"
        v-model="title"
        placeholder="笔记标题..."
        @input="onChange"
      />
      <div class="editor-meta">
        <span>创建于 {{ note.createTimeStr }}</span>
        <span>更新于 {{ note.updateTimeStr }}</span>
      </div>
    </div>
    <div class="editor-body">
      <textarea
        class="content-input"
        v-model="content"
        placeholder="在这里写下你的想法..."
        @input="onChange"
      ></textarea>
    </div>
    <div class="editor-footer" v-if="saving || saved">
      <span v-if="saving" class="status saving">保存中...</span>
      <span v-else-if="saved" class="status saved">已保存</span>
    </div>
  </main>
  <main class="editor empty" v-else>
    <div class="empty-state">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" class="empty-icon">
        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
        <polyline points="14 2 14 8 20 8"/>
        <line x1="16" y1="13" x2="8" y2="13"/>
        <line x1="16" y1="17" x2="8" y2="17"/>
      </svg>
      <p>选择一篇笔记或创建新笔记</p>
    </div>
  </main>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  note: { type: Object, default: null }
})

const emit = defineEmits(['update'])

const title = ref('')
const content = ref('')
const saving = ref(false)
const saved = ref(false)
let saveTimer = null

watch(() => props.note, (n) => {
  if (n) {
    title.value = n.title || ''
    content.value = n.content || ''
  } else {
    title.value = ''
    content.value = ''
  }
  saving.value = false
  saved.value = false
}, { immediate: true })

function onChange() {
  saved.value = false
  clearTimeout(saveTimer)
  saveTimer = setTimeout(() => {
    saving.value = true
    emit('update', { title: title.value, content: content.value })
    setTimeout(() => {
      saving.value = false
      saved.value = true
      setTimeout(() => { saved.value = false }, 2000)
    }, 300)
  }, 600)
}
</script>

<style scoped>
.editor {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: #fff;
  overflow: hidden;
}

.editor.empty {
  justify-content: center;
  align-items: center;
}

.empty-state {
  text-align: center;
  color: #b7b7b7;
}

.empty-icon {
  width: 80px;
  height: 80px;
  margin-bottom: 16px;
  opacity: 0.4;
}

.empty-state p {
  font-size: 15px;
  margin: 0;
}

.editor-header {
  padding: 24px 40px 16px;
  border-bottom: 1px solid #e8f5e9;
}

.title-input {
  width: 100%;
  border: none;
  font-size: 28px;
  font-weight: 600;
  color: #1b4332;
  outline: none;
  padding: 0;
  font-family: inherit;
  background: transparent;
}

.title-input::placeholder {
  color: #b7d7c4;
}

.editor-meta {
  display: flex;
  gap: 24px;
  margin-top: 8px;
  font-size: 12px;
  color: #95b5a4;
}

.editor-body {
  flex: 1;
  padding: 20px 40px;
  overflow: hidden;
}

.content-input {
  width: 100%;
  height: 100%;
  border: none;
  resize: none;
  outline: none;
  font-size: 16px;
  line-height: 1.8;
  color: #333;
  font-family: inherit;
  background: transparent;
}

.content-input::placeholder {
  color: #c8d6ce;
}

.editor-footer {
  padding: 8px 40px;
  font-size: 12px;
}

.status {
  transition: opacity 0.3s;
}

.status.saving {
  color: #95b5a4;
}

.status.saved {
  color: #52b788;
}
</style>
