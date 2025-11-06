<template>
  <div class="login-container">
    <h2>Iniciar Sesión</h2>
    <form @submit.prevent="iniciarSesion">
      <input v-model="usuario" type="text" placeholder="Usuario" required />
      <input v-model="clave" type="password" placeholder="Contraseña" required />
      <button type="submit">Ingresar</button>
    </form>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useUsuarioStore } from '../stores/usuarioStore'
import { useRouter } from 'vue-router'

const usuario = ref('')
const clave = ref('')
const error = ref(null)
const usuarioStore = useUsuarioStore()
const router = useRouter()

async function iniciarSesion() {
  try {
    const res = await fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        username: usuario.value.trim(),
        password: clave.value.trim()
      })
    })

    if (!res.ok) throw new Error('Credenciales incorrectas')

    const { token } = await res.json()
    localStorage.setItem('token', token)
    usuarioStore.establecerDatosDesdeToken(token)
    router.push('/ventas')
  } catch (e) {
    error.value = e.message
  }
}
</script>


<style scoped>
.login-container {
  max-width: 400px;
  margin: auto;
  padding: 2rem;
}
.error {
  color: red;
  margin-top: 1rem;
}
</style>