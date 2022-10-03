import { get } from './http'

const getAllCourse = async function(userId) {
    return await get(`/course/getAllCourse`, {params: {id: userId}})
}

const getCurrentSemester = async function() {
    return await get(`/course/findCurrentSemester`, {})
}

export default {
    name: 'courseAPI',
    getAllCourse,
    getCurrentSemester
}