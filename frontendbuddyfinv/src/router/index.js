// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import UserTable from '../components/UserTable.vue'
import DashboardOpciones from '@/views/DashboardOpciones.vue'
import DashboardLayout from '../layouts/DashboardLayout.vue'
import VentaView from '../views/VentaView.vue'
import IngresoTable from '../components/IngresoTable.vue'
import LoginView from '@/views/LoginView.vue'

const routes = [

  {path:'/',
    redirect:'/login',
    component:LoginView

  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/LoginView.vue')
  },
  
  
  {
    path: '/ventas',
    name: 'Ventas',
    component: () => import('../views/VentaView.vue')
  }
  ,
  
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