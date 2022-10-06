import {del, get, post, put} from './http'

const getCourseById = async function (courseId) {
    let response = await get(`course/getCourseById?id=${courseId}`)
    return {
        ...response.data,
        status: response.status
    }
}

const getAllCurrentStudent = async function (courseId) {
    let response = await get(`course/getAllCurrentStudent?id=${courseId}`)
    return {
        ...response.data,
        status: response.status
    }
}

const getAllPreviousStudent = async function (courseId) {
    let response = await get(`course/getAllPreviousStudent?id=${courseId}`)
    return {
        ...response.data,
        status: response.status
    }
}

const getAllPreviousTeammate = async function (courseId, userId) {
    let response = await get(`course/getAllPreviousTeammates?userId=${userId}&&courseId=${courseId}`)
    return {
        ...response.data,
        status: response.status
    }
}

const postEnrollCourse = async function(courseId, userId) {
    let response = await post(`course/enrollCourse?userId=${userId}&&courseId=${courseId}`)
    return {
        ...response.data,
        status: response.status
    }
}

const delDropCourse = async function(courseId, userId) {
    let response = await del(`course/dropCourse?userId=${userId}&&courseId=${courseId}`)
    return {
        ...response.data,
        status: response.status
    }
}

const getCheckEnrollment = async function(courseId, userId) {
    let response = await get(`course/checkEnrollment?userId=${userId}&&courseId=${courseId}`)
    return {
        ...response.data,
        status: response.status
    }
}

const getCheckMarkedCourse = async function(courseId, userId) {
    let response = await get(`course/checkMarkedCourse?userId=${userId}&&courseId=${courseId}`)
    return {
        ...response.data,
        status: response.status
    }
}

const postMarkCourse = async function(courseId, userId) {
    let response = await post(`course/markCourse?userId=${userId}&&courseId=${courseId}`)
    return {
        ...response.data,
        status: response.status
    }
}

const delUnmarkCourse = async function(courseId, userId) {
    let response = await del(`course/unmarkCourse?userId=${userId}&&courseId=${courseId}`)
    return {
        ...response.data,
        status: response.status
    }
}

const getTutorial = async function(courseId, userId) {
    let response = await get(`course/getTutorial?userId=${userId}&&courseId=${courseId}`)
    return {
        ...response.data,
        status: response.status
    }
}

const putSetTutorial = async function(courseId, userId, tutorial) {
    let response = await put(`course/setTutorial?userId=${userId}&&courseId=${courseId}&&tutorial=${tutorial}`)
    return {
        ...response.data,
        status: response.status
    }
}

export default {
    name: 'courseDetailAPI',
    getCourseById,
    getAllCurrentStudent,
    getAllPreviousTeammate,
    getAllPreviousStudent,
    getCheckEnrollment,
    postEnrollCourse,
    delDropCourse,
    getCheckMarkedCourse,
    postMarkCourse,
    delUnmarkCourse,
    getTutorial,
    putSetTutorial
}