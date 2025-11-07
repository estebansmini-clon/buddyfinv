const API_BASE_URL = import.meta?.env?.VITE_API_BASE_URL || 'http://localhost:8080'
const EGRESO_BASE = `${API_BASE_URL}/Egresos`

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

export const EgresoProvider = {
    async test(){
        const res = await fetch(`${EGRESO_BASE}/test`,{
            method: 'GET',
            credentials:'include',
        })
        return handleResponse(res)
    },

    async getAll(){
        const res= await fetch(`${EGRESO_BASE}/obtenerTegresos`,{
            method: 'GET',
            credentials: 'include'
        })
        return handleResponse(res)
    },

    async registerEgreso(data){
        const res= await fetch(`${EGRESO_BASE}/agregarEgreso`,{
            method:'POST',
            headers:{'Content-Type':'aplication.json'},
            body: JSON.stringify(data),
            credentials:'include'
        })
        return handleResponse(res)
    },

    async modifyEgreso(id,data){
        const res = await fetch(`${EGRESO_BASE}/modificar/${id}`,{
            method:'PUT',
            headers:{'Content-Type':'application/json'},
            body: JSON.stringify(data),
            credentials:'include'

        })
        return handleResponse(res)

    },
    async validateEgreso(id,data){
        const res = await fetch(`${EGRESO_BASE}/valida/${id}`,{
            method:'POST',
            headers:{'Content-Type':'application/json'},
            body: JSON.stringify(data),
            credentials:'include'

        })
        return handleResponse(res)
    
    },

    async eliminateEgreso(id){
        const res = await fetch(`${EGRESO_BASE}/eliminar/${id}`,{
            method:'DELETE',
            credentials:'include'

        })
        return handleResponse(res)
    },

    async getAllEgresosByUsuario(id){
        const res = await fetch(`${EGRESO_BASE}/obtenetXusuario/${id}`,{
            method:'GET',
            credentials:'include'
        })
        return handleResponse(res)
    },

    async costoTotalEgresosById(id){
        const res = await fetch(`${EGRESO_BASE}/obtenerTotal/${id}`,{
            method:'GET',
            credentials:'include'
        })
        return handleResponse(res)




    }



}


export default EgresoProvider












