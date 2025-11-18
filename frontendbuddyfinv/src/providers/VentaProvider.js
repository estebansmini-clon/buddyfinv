// src/services/ventasProvider.js
import { crearVentaDTO } from '../models/VentaDTO.js' // conserva si lo usas en algún lugar; si no, puedes eliminar esta importación

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'
const VENTAS_BASE = `${API_BASE_URL}/ventas`

async function handleResponse(response) {
  // normaliza la respuesta y lanza errores con información útil
  if (!response.ok) {
    const text = await response.text().catch(() => '')
    const contentType = response.headers.get('content-type') || ''
    let body = text
    try {
      if (text && contentType.includes('application/json')) {
        body = JSON.parse(text)
      }
    } catch (e) {
      body = text
    }
    const message = (body && (body.message || body.error)) ? (body.message || body.error) : (text || `${response.status} ${response.statusText}`)
    const err = new Error(message)
    err.status = response.status
    err.body = body
    throw err
  }

  const contentType = response.headers.get('content-type') || ''
  if (contentType.includes('application/json')) {
    return response.json()
  }
  return response.text()
}

function authHeaders() {
  // cambia la lectura del token si lo guardas en Vuex/Pinia en vez de localStorage
  const token = localStorage.getItem('token')
  return token ? { Authorization: `Bearer ${token}` } : {}
}

export const VentaProvider = {
  async getDetalladas() {
    const token = localStorage.getItem('token')
    if (!token) throw new Error('Token no disponible')

    const res = await fetch(`${VENTAS_BASE}/detalladas`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    })

    return handleResponse(res)
  },

  async crearVenta(ventaCrearDto) {
    const res = await fetch(`${VENTAS_BASE}/crearVenta`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        ...authHeaders()
      },
      body: JSON.stringify(ventaCrearDto)
    })
    return handleResponse(res)
  },

  async listarVentas(params = {}) {
    const qs = new URLSearchParams(params).toString()
    const res = await fetch(`${VENTAS_BASE}${qs ? `?${qs}` : ''}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        ...authHeaders()
      }
    })
    return handleResponse(res)
  },

  async obtenerVenta(id) {
    const res = await fetch(`${VENTAS_BASE}/${id}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        ...authHeaders()
      }
    })
    return handleResponse(res)
  }
}