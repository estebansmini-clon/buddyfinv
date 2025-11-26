<template>
    <div class="container">
      <h2>Cambiar contraseña</h2>
  
      <div class="form-card">
        <label>Nueva contraseña</label>
        <input
          type="password"
          v-model="newPassword"
          placeholder="Ingresa tu nueva contraseña"
        />
  
        <label>Confirmar contraseña</label>
        <input
          type="password"
          v-model="confirmPassword"
          placeholder="Confirma tu contraseña"
        />
  
        <button @click="update" :disabled="loading">
          {{ loading ? "Guardando..." : "Registrar nueva contraseña" }}
        </button>
  
        <p v-if="message" :class="ok ? 'success' : 'error'">{{ message }}</p>
      </div>
    </div>
  </template>
  
  <script>
  import PasswordResetProvider from "@/providers/PasswordResetProvider";
  
  export default {
    name: "ChangePasswordView",
  
    data() {
      return {
        newPassword: "",
        confirmPassword: "",
        loading: false,
        ok: false,
        message: "",
        usernameOrEmail: "",
      };
    },
  
    mounted() {
      this.usernameOrEmail = this.$route.query.u || "";
    },
  
    methods: {
      async update() {
        if (!this.newPassword || !this.confirmPassword) {
          this.message = "Complete todos los campos.";
          return;
        }
  
        this.loading = true;
        this.message = "";
  
        const resp = await PasswordResetProvider.updatePassword(
          this.usernameOrEmail,
          this.newPassword,
          this.confirmPassword
        );
  
        this.ok = resp.ok;
        this.message = resp.message;
  
        this.loading = false;
  
        if (resp.ok) {
          setTimeout(() => {
            this.$router.push({ name: "Login" });
          }, 1200);
        }
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
  .error {
    color: red;
  }
  .success {
    color: green;
  }
  </style>
  