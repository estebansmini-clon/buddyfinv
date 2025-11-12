import { createRouter, createWebHistory } from 'vue-router'
import DashboardLayout from '../layouts/DashboardLayout.vue'
import DashboardOpciones from '@/views/DashboardOpciones.vue'
import IngresoTable from '../components/IngresoTable.vue'
import ProductoView from '../views/ProductoView.vue'
import VentaView from '../views/VentaView.vue'
import { useUsuarioStore } from '@/stores/usuarioStore.js'

//import TestProducto from '../views/TestProducto.vue'


import EgresoView from '@/views/EgresoView.vue'


const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/dashboard',
    component: DashboardLayout,
    redirect: '/dashboard/dashboard',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'dashboard',
        name: 'dashboard',
        component: DashboardOpciones
      },
      { path: 'ventas', name: 'Ventas', component: VentaView },
      { path: 'ingresos', name: 'Ingresos', component: IngresoTable },
      { path: 'egresos', name: 'Egresos', component: EgresoView },
      { path: 'inventario', name: 'inventario', component: ProductoView }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const usuarioStore = useUsuarioStore()
  const requiereAuth = to.matched.some(record => record.meta.requiereAuth)

  if (requiereAuth && !usuarioStore.token) {
    next('/login')
  } else {
    next()
  }
})


export default router
