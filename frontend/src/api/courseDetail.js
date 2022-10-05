import {get} from './http'

const getCourseById = async function (courseId) {
    let response = await get(`course/getCourseById?id=${courseId}`)
    return response.data
}

const getAllCurrentStudent = async function (courseId) {
    let response = await get(`course/getAllCurrentStudent?id=${courseId}`)
    return response.data
}

const getAllPreviousStudent = async function (courseId) {
    let response = await get(`course/getAllPreviousStudent?id=${courseId}`)
    return response.data
}

const getAllPreviousTeammate = async function (courseId, userId) {
    let response = await get(`course/getAllPreviousTeammates?userId=${userId}&&courseId=${courseId}`)
    return response.data
}

export default {
    name: 'courseDetailAPI',
    getCourseById,
    getAllCurrentStudent,
    getAllPreviousTeammate,
    getAllPreviousStudent
}