import {get, post} from './http'
import catchError from "@/util/catchError";

const getCurrentSemester = async function() {
    try {
        const res = await get(`/admin/findCurrentSemester`)
        if(res.request.status === 200)
            return res
        else if (res.request.status === 202)
        {
            catchError.outputInfo(res.data.msg)
            return res
        }
        else
            throw new Error(res.data.msg)
    } catch(err) {
            return catchError.outputError(err)
    }
}
const changeCurrentSemester = async function(year,semesterNumber) {
    try {
        const res = await post(`/admin/changeCurrentSemester?year=`+ year + `&semesterNumber=`+ semesterNumber)
        if(res.request.status === 200)
            return res
        else if (res.request.status === 202)
        {
            catchError.outputInfo(res.data.msg)
            return res
        }
        else
            throw new Error(res.data.msg)
    } catch(err) {
            return catchError.outputError(err)
    }
}
const findAllComments = async function() {
    try {
        const res = await get(`/admin/findAllComments`, {})
        if(res.request.status === 200)
            return res
        else if (res.request.status === 202)
        {
            catchError.outputInfo(res.data.msg)
            return res
        }
        else
            throw new Error(res.data.msg)
    } catch(err) {
            return catchError.outputError(err)
    }
}

const deleteAComment = async function(commentId) {
    try {
        const res = await post(`/admin/deleteAComment?commentId=`+ commentId)
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.msg)
    } catch(err) {
        return catchError.outputError(err)
    }
}
const changeIsCommentAvailableStatus = async function(isAvailable) {
    try {
        const res = await post(`/admin/changeIsCommentAvailableStatus?isAvailable=`+ isAvailable)
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.msg)
    } catch(err) {
        if(err.message === 'Change IsCommentAvailable status failed, because the new status is the same to the current one!')
            return catchError.outputInfo(err)
        else
            return catchError.outputError(err)
    }
}
const findAllCourses = async function() {
    try {
        const res = await get(`/admin/findAllCourses`, {})
        if(res.request.status === 200)
            return res
        else if (res.request.status === 202)
        {
            catchError.outputInfo(res.data.msg)
            return res
        }
        else
            throw new Error(res.data.msg)
    } catch(err) {
        return catchError.outputError(err)
    }
}
const deleteACourse = async function(courseId) {
    try {
        const res = await post(`/admin/deleteACourse?courseId=`+ courseId)
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.msg)
    } catch(err) {
        return catchError.outputError(err)
    }
}

const addACourse = async function(code, name, universityId) {
    try {
        const res = await post(`/admin/addACourse`, {'code':code,'name':name,'universityId':universityId})
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.msg)
    } catch(err) {
        return catchError.outputError(err)
    }
}

const changeACourseLockStatus = async function(courseId, isLocked) {
    try {
        const res = await post(`/admin/changeACourseLockStatus?courseId=`+ courseId +'&isLocked=' + isLocked)
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.msg)
    } catch(err) {
        return catchError.outputError(err)
    }
}

const findAllUniversities = async function() {
    try {
        const res = await get(`/admin/findAllUniversities`)
        if(res.request.status === 200)
            return res
        else if (res.request.status === 202)
        {
            catchError.outputInfo(res.data.msg)
            return res
        }
        else
            throw new Error(res.data.msg)
    } catch(err) {
        return catchError.outputError(err)
    }
}

export default {
    name: 'adminAPI',
    getCurrentSemester,
    changeCurrentSemester,
    findAllComments,
    deleteAComment,
    changeIsCommentAvailableStatus,
    findAllCourses,
    deleteACourse,
    addACourse,
    changeACourseLockStatus,
    findAllUniversities,
}