// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import UserTable from '../components/UserTable.vue'
import DashboardOpciones from '@/views/DashboardOpciones.vue'
import DashboardLayout from '../layouts/DashboardLayout.vue'
import VentaView from '../views/VentaView.vue'
import IngresoTable from '../components/IngresoTable.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/LoginView.vue')
  },
  
  
  {
    path: '/',
    name: 'VentaView',
    component: VentaView

  },
  
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: DashboardLayout
  },

  {
    path: '/listaingresos',
    name: 'Ingresos',
    component: IngresoTable
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router