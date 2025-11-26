<template>
    <div class="container">
      <h2>Verificar código</h2>
  
      <div class="form-card">
        <label>Ingrese el código recibido</label>
        <input
          type="text"
          v-model="code"
          maxlength="6"
          placeholder="Código de verificación"
        />
  
        <button @click="verify" :disabled="loading">
          {{ loading ? "Verificando..." : "Verificar código" }}
        </button>
  
        <p v-if="message" :class="ok ? 'success' : 'error'">{{ message }}</p>
      </div>
    </div>
  </template>
  
  <script>
  import PasswordResetProvider from "@/providers/PasswordResetProvider";
  
  export default {
    name: "VerifyCodeView",
  
    data() {
      return {
        code: "",
        loading: false,
        ok: false,
        message: "",
        usernameOrEmail: "",
      };
    },
  
    mounted() {
      // Recibir usuario del query param
      this.usernameOrEmail = this.$route.query.u || "";
    },
  
    methods: {
      async verify() {
        if (!this.code.trim()) {
          this.message = "Ingrese el código.";
          return;
        }
  
        this.loading = true;
        this.message = "";
  
        const resp = await PasswordResetProvider.verifyResetCode(
          this.usernameOrEmail,
          this.code
        );
  
        this.ok = resp.ok;
        this.message = resp.message;
  
        this.loading = false;
  
        // Si es correcto, pasar a cambiar clave
        if (resp.ok) {
          setTimeout(() => {
            this.$router.push({
              name: "change-password",
              query: { u: this.usernameOrEmail },
            });
          }, 1000);
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
  