const API_BASE_URL = import.meta?.env?.VITE_API_BASE_URL || 'http://localhost:8080'
const USERS_BASE = `${API_BASE_URL}/usuarios`

async function handleResponse(response) {
	if (!response.ok) {
		const text = await response.text().catch(() => '')
		const message = text || `${response.status} ${response.statusText}`
		throw new Error(message)
	}
	const contentType = response.headers.get('content-type') || ''
	if (contentType.includes('application/json')) {
		return response.json()
	}
	return response.text()
}

export const UsuarioProvider = {
	// GET /usuarios/test
	async test() {
		const res = await fetch(`${USERS_BASE}/test`, {
			method: 'GET',
			credentials: 'include',
		})
		return handleResponse(res)
	},

	// GET /usuarios/all -> List<UsuarioDTO>
	async getAll() {
		const res = await fetch(`${USERS_BASE}/all`, {
			method: 'GET',
			credentials: 'include',
		})
		return handleResponse(res)
	},

	
}

export default UsuarioProvider
