import axios from "axios";

const API_URL = "http://localhost:8080/auth/recover-username";

class UsernameRecoveryProvider {
    async recuperarUsuario(email) {
        try {
            const response = await axios.post(API_URL, {
                email: email});
            return {
                enviado: true,
                mensaje: response.data};
        } catch (error) {
            if (error.response && error.response.data) {
                return error.response.data;
            }
            return { enviado: false, mensaje: "Error inesperado al procesar la solicitud" };
        }
    }
}

export default new UsernameRecoveryProvider();
