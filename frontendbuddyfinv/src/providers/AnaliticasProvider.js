// providers/AnaliticasProvider.js
import { VentasSerieDTO } from '@/models/VentasSerieDTO.js';
import { ProductoEstrellaDTO } from '@/models/ProductoEstrellaDTO.js';
import { GastoTipoEgresoDTO } from '@/models/GastoTipoEgresoDTO.js';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';
const VENTAS_BASE = `${API_BASE_URL}/ventas`;
const EGRESOS_BASE = `${API_BASE_URL}/Egresos`;

function getAuthHeader() {
  const token = localStorage.getItem('token');
  return token ? { Authorization: `Bearer ${token}` } : {};
}

async function handleResponse(response) {
  if (!response.ok) {
    const text = await response.text().catch(() => '');
    const contentType = response.headers.get('content-type') || '';
    let body = text;

    try {
      if (text && contentType.includes('application/json')) {
        body = JSON.parse(text);
      }
    } catch (e) {
      body = text;
    }

    const message =
      (body && (body.message || body.error))
        ? (body.message || body.error)
        : (text || `${response.status} ${response.statusText}`);

    const err = new Error(message);
    err.status = response.status;
    err.body = body;
    throw err;
  }

  const contentType = response.headers.get('content-type') || '';
  if (contentType.includes('application/json')) {
    return response.json();
  }
  return response.text();
}

function getUserIdFromToken() {
  try {
    const token = localStorage.getItem('token');
    if (!token) return null;

    const payloadBase64 = token.split('.')[1];
    const payload = JSON.parse(atob(payloadBase64));

    return payload.idUsuario || payload.id || null;
  } catch (error) {
    console.error('Error al decodificar el token:', error);
    return null;
  }
}

export const AnaliticasProvider = {
  /** Ventas por periodo (gráficos) */
  async getVentasGraficos(from, to, granularity = 'MONTH') {
    const userId = getUserIdFromToken();
    if (!userId) {
      throw new Error('No se pudo obtener el ID del usuario desde el token');
    }

    const res = await fetch(
      `${VENTAS_BASE}/graficos?from=${from}&to=${to}&granularity=${granularity}`,
      {
        method: 'GET',
        headers: { ...getAuthHeader() },
        credentials: 'include'
      }
    );

    const data = await handleResponse(res);
    return Array.isArray(data) ? data.map(v => new VentasSerieDTO(v)) : [];
  },

  /** Productos estrella */
  async getProductosEstrella() {
    const userId = getUserIdFromToken();
    if (!userId) {
      throw new Error('No se pudo obtener el ID del usuario desde el token');
    }
  
    const res = await fetch(`${VENTAS_BASE}/graficos/productos-estrella`, {
      method: 'GET',
      headers: { ...getAuthHeader() },
      credentials: 'include'
    });
  
    const data = await handleResponse(res);
    return Array.isArray(data) ? data.map(p => new ProductoEstrellaDTO(p)) : [];
  },
  
  /** Gastos por tipo de egreso */
  async getGastosGraficos() {
    const userId = getUserIdFromToken();
    if (!userId) {
      throw new Error('No se pudo obtener el ID del usuario desde el token');
    }
  
    const res = await fetch(`${EGRESOS_BASE}/graficosGastos`, {
      method: 'GET',
      headers: { ...getAuthHeader() },
      credentials: 'include'
    });
  
    const data = await handleResponse(res);
    return Array.isArray(data) ? data.map(g => new GastoTipoEgresoDTO(g)) : [];  
  }
};

export default AnaliticasProvider;