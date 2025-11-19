<template>
    <div class="page-container">
      <div class="box">
        <h2>Empleados</h2>
        <p v-if="loading">Cargando...</p>
        <p v-if="!loading && empleados.length === 0">No hay empleados.</p>
  
        <div v-for="u in empleados" :key="u.id" class="row">
          <div class="col">{{ u.nitUsuario }}</div>
          <div class="col">{{ u.nombre }}</div>
          <div class="col">{{ u.email }}</div>
          <div class="col">{{ u.usuario }}</div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { UsuarioProvider } from '@/providers/UsuarioProvider.js'
  
  export default {
    name: 'ListaEmpleadosView',
    data() {
      return {
        empleados: [],
        loading: false,
        error: ''
      }
    },
    async mounted() {
      await this.cargar()
    },
    methods: {
      async cargar() {
        this.loading = true
        try {
          this.empleados = await UsuarioProvider.listarEmpleados()
        } catch (e) {
          this.error = e.message || 'Error al cargar empleados'
        } finally {
          this.loading = false
        }
      }
    }
  }
  </script>
  
  <style scoped>
  .page-container{display:flex;justify-content:center;padding:30px}
  .box{width:100%;max-width:1000px;background:white;padding:24px;border-radius:12px;box-shadow:0 6px 18px rgba(0,0,0,.06)}
  .row{display:grid;grid-template-columns:1fr 2fr 2fr 1fr;padding:10px;border-radius:6px;background:#f6f6f6;margin-top:8px}
  .row:nth-child(1){background:#ff8800;color:white;padding:12px}
  .col{padding:4px 8px}
  @media (max-width:768px){.row{grid-template-columns:1fr;gap:8px}}
  </style>
  