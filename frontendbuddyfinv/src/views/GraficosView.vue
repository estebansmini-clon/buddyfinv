<template>
    <div class="graficos-view">
      <GraficosComponent />
    </div>
  </template>
  
  <script>
  import GraficosComponent from '../components/GraficosComponent.vue'
  
  export default {
    name: 'GraficosView',
    components: {
      GraficosComponent
    }
  }
  </script>