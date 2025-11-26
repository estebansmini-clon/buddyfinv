<template>
    <div class="container">
      <h2>Recuperar contraseña</h2>
  
      <div class="form-card">
        <label>Usuario o correo registrado</label>
        <input
          type="text"
          v-model="usernameOrEmail"
          placeholder="Ingrese su usuario o correo"
        />
  
        <button @click="sendCode" :disabled="loading">
          {{ loading ? "Enviando..." : "Enviar código" }}
        </button>
  
        <p v-if="message" class="info">{{ message }}</p>
      </div>
    </div>
  </template>
  
  <script>
  import PasswordResetProvider from "@/providers/PasswordResetProvider";
  
  export default {
    name: "ForgotPasswordView",
  
    data() {
      return {
        usernameOrEmail: "",
        message: "",
        loading: false,
      };
    },
  
    methods: {
      async sendCode() {
        if (!this.usernameOrEmail.trim()) {
          this.message = "Ingrese un usuario o correo.";
          return;
        }
  
        this.loading = true;
        this.message = "";
  
        const resp = await PasswordResetProvider.requestPasswordReset(
          this.usernameOrEmail
        );
  
        this.message = resp;
  
        this.loading = false;
  
        // Ir a pantalla de verificación
        setTimeout(() => {
          this.$router.push({
            name: "verify-code",
            query: { u: this.usernameOrEmail },
          });
        }, 1000);
      },
    },
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 430px;
    margin: 60px auto;
    text-align: center;
  }
  .form-card {
    padding: 20px;
    border-radius: 12px;
    background: white;
    box-shadow: 0px 2px 8px #00000020;
  }
  input {
    width: 100%;
    padding: 12px;
    margin: 10px 0;
  }
  button {
    width: 100%;
    padding: 12px;
    background: #0a70ff;
    color: white;
    border: none;
    margin-top: 10px;
    cursor: pointer;
  }
  .info {
    margin-top: 10px;
    color: #555;
  }
  </style>
