import { get } from './http'

const getAllCourse = async function(userId) {
    return await get(`/course/getAllCourse`, {params: {id: userId}})
}

export default {
    name: 'courseAPI',
    getAllCourse,
}