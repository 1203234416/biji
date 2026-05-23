import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  base: '/wenote/',
  server: {
    port: 5173,
    proxy: {
      '/wenote/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/wenote/, '')
      }
    }
  }
})
