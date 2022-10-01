import { get } from './http'

const getCurrentSemester = async function() {
    return await get(`/admin/findCurrentSemester`, {})
}

export default {
    name: 'adminAPI',
    getCurrentSemester
}