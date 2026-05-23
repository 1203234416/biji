# 我的笔记 (Biji)

基于 Vue 3 + Spring Boot 的个人笔记管理应用，绿色主题，简洁优雅。

## 功能

- 笔记支持新建、编辑、删除
- 每篇笔记对应一个独立的 txt 文件，存放于 `backend/notes/` 目录
- 输入后自动保存
- 三栏布局：顶部 Logo + 实时时钟，左侧笔记列表，右侧编辑区

## 技术栈

| 层级 | 技术 |
|------|------|
| 前端 | Vue 3 + Vite |
| 后端 | Spring Boot 3.3 + JDK 21 |
| 存储 | 本地 txt 文件 |

## 启动方式

### 1. 启动后端（端口 8080）

```bash
cd backend
mvn spring-boot:run
```

### 2. 启动前端（端口 5173）

```bash
cd frontend
npm install
npm run dev
```

### 3. 访问

浏览器打开 `http://localhost:5173`
