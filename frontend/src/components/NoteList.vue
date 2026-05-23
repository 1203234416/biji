<template>
  <aside class="sidebar">
    <div class="sidebar-header">
      <button class="btn-new" @click="$emit('create')">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
          <line x1="12" y1="5" x2="12" y2="19"/>
          <line x1="5" y1="12" x2="19" y2="12"/>
        </svg>
        新建笔记
      </button>
    </div>
    <div class="note-list">
      <div
        v-for="note in notes"
        :key="note.id"
        class="note-item"
        :class="{ active: activeId === note.id }"
        @click="$emit('select', note.id)"
      >
        <div class="note-info">
          <div class="note-title">{{ note.title }}</div>
          <div class="note-time">{{ note.createTimeStr }}</div>
        </div>
        <button class="btn-delete" @click.stop="$emit('delete', note.id)" title="删除笔记">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polyline points="3 6 5 6 21 6"/>
            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/>
          </svg>
        </button>
      </div>
      <div v-if="notes.length === 0" class="empty-hint">
        还没有笔记，点击上方按钮创建第一篇吧
      </div>
    </div>
  </aside>
</template>

<script setup>
defineProps({
  notes: { type: Array, default: () => [] },
  activeId: { type: String, default: '' }
})

defineEmits(['select', 'create', 'delete'])
</script>

<style scoped>
.sidebar {
  width: 260px;
  min-width: 260px;
  height: 100%;
  background: #f0faf3;
  border-right: 1px solid #d8f3dc;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.sidebar-header {
  padding: 16px;
  border-bottom: 1px solid #d8f3dc;
}

.btn-new {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  padding: 10px 0;
  background: linear-gradient(135deg, #40916c, #52b788);
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  font-family: inherit;
}

.btn-new:hover {
  background: linear-gradient(135deg, #2d6a4f, #40916c);
  box-shadow: 0 2px 8px rgba(45, 106, 79, 0.3);
  transform: translateY(-1px);
}

.btn-new svg {
  width: 18px;
  height: 18px;
}

.note-list {
  flex: 1;
  overflow-y: auto;
  padding: 8px;
}

.note-item {
  display: flex;
  align-items: center;
  padding: 12px 14px;
  margin-bottom: 4px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.15s;
  gap: 8px;
}

.note-item:hover {
  background: #d8f3dc;
}

.note-item.active {
  background: #52b788;
  color: #fff;
}

.note-info {
  flex: 1;
  min-width: 0;
}

.note-title {
  font-size: 14px;
  font-weight: 500;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.note-time {
  font-size: 11px;
  opacity: 0.6;
  margin-top: 3px;
}

.btn-delete {
  display: none;
  align-items: center;
  justify-content: center;
  width: 28px;
  height: 28px;
  border: none;
  background: transparent;
  color: inherit;
  cursor: pointer;
  border-radius: 6px;
  opacity: 0.5;
  transition: all 0.15s;
  flex-shrink: 0;
}

.note-item:hover .btn-delete {
  display: flex;
}

.note-item.active .btn-delete {
  display: flex;
  opacity: 0.8;
}

.btn-delete:hover {
  opacity: 1 !important;
  background: rgba(255, 255, 255, 0.2);
}

.btn-delete svg {
  width: 16px;
  height: 16px;
}

.empty-hint {
  text-align: center;
  color: #95d5b2;
  font-size: 13px;
  padding: 32px 16px;
  line-height: 1.6;
}
</style>
