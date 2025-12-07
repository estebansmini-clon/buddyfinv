<template>
    <div class="recover-container">
      <h2 class="logo">BUDDYFINV</h2>
  
      <div class="recover-box">
        <h2>Recuperar Usuario</h2>
  
        <!-- Campo Correo -->
        <div class="input-group" :class="{ error: errorCampo }">
          <input
            v-model="correo"
            type="email"
            placeholder="Ingrese el correo registrado"
          />
          <span class="icon">
            <span v-if="correo && correoValido">✅</span>
            <span v-else-if="correo">❌</span>
          </span>
        </div>
  
        <p v-if="mensaje" :class="mensajeColor" class="mensaje-text">
          {{ mensaje }}
        </p>
  
        <!-- Botón enviar -->
        <button :disabled="bloqueo || !correoValido || loading" @click="enviar">
          {{loading ? "Enviando...":"Enviar correo"}}
        </button>
  
        <div class="links">
          <router-link to="/login">Volver al login</router-link>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, watch } from "vue";
  import UsernameRecoveryProvider from "@/providers/UsernameRecoveryProvider";
  import { useRouter } from "vue-router";
  
  const router = useRouter();
  
  const correo = ref("");
  const correoValido = ref(false);
  const mensaje = ref("");
  const mensajeColor = ref("");
  const bloqueo = ref(false);
  const errorCampo = ref(false);
  const loading = ref(false);
  
  /* --- VALIDACIÓN DEL CORREO --- */
  const validarCorreo = (valor) => {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    correoValido.value = regex.test(valor);
    errorCampo.value = !correoValido.value && valor !== "";
  };
  
  watch(correo, () => {
    mensaje.value = "";
    validarCorreo(correo.value);
  });
  
  /* --- ACCIÓN DE ENVÍO --- */
  const enviar = async () => {
    mensaje.value = "";
  
    if (!correoValido.value) {
      mensaje.value = "Ingrese un correo válido.";
      mensajeColor.value = "error-text";
      errorCampo.value = true;
      return;
    }
  
    loading.value = true;
    errorCampo.value = false;

    try{
        const res = await UsernameRecoveryProvider.recuperarUsuario(correo.value);
  
        mensaje.value = res.mensaje;
  
        if (res.enviado) {
        mensajeColor.value = "success-text";
  
        setTimeout(() => router.push("/login"), 2000);
        } else {
        mensajeColor.value = "error-text";
        errorCampo.value = true;
        }
    }catch (err){
        mensajeColor.value = "error-text";
        mensaje.value = "Ocurrió un error al procesar la solicitud.";
    } finally {
        loading.value = false;
    }
  };
  </script>
  
  <style scoped>
  @import url("https://fonts.googleapis.com/css2?family=Outfit:wght@100..900&display=swap");
  
  /* --- CONTENEDOR GENERAL --- */
  .recover-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    width: 100vw;
    background-image: url("@/assets/FondoLogin.png");
    background-size: cover;
    background-position: center;
    font-family: "Outfit", sans-serif;
  }
  
  /* --- LOGO --- */
  .logo {
    font-weight: 900;
    font-size: 3.4rem;
    color: coral;
    margin-bottom: 10px;
  }
  
  /* --- CAJA PRINCIPAL --- */
  .recover-box {
    background: rgba(255, 255, 255, 0.85);
    width: 360px;
    padding: 20px;
    margin-top: 10px;
    border-radius: 30px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
    font-weight: 800;
    text-align: center;
  }
  
  h2 {
    font-size: 2rem;
    color: #575757;
    margin-bottom: 30px;
  }
  
  /* --- INPUT GROUP --- */
  .input-group {
    position: relative;
    margin-bottom: 20px;
  }
  
  .input-group input {
    width: 100%;
    padding: 4%;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f5f5f5;
  }
  
  .input-group.error input {
    border-color: red;
  }
  
  .icon {
    position: absolute;
    right: 10px;
    top: 10px;
  }
  
  /* --- BOTÓN --- */
  button {
    width: 100%;
    padding: 10px;
    background-color: #ff7b00;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: 0.3s;
  }
  
  button:hover:enabled {
    background-color: #ff7b00;
  }
  
  button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
  
  /* --- TEXTOS DE MENSAJE --- */
  .mensaje-text {
    font-size: 0.9em;
    margin-bottom: 10px;
  }
  
  .error-text {
    color: red;
  }
  
  .success-text {
    color: #1ad063;
  }
  
  /* --- LINKS --- */
  .links {
    margin-top: 15px;
  }
  
  .links a {
    color: #004aad;
    text-decoration: none;
  }
  </style>
  