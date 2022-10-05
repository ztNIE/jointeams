import {get, post} from './http'
import {ElMessage} from "element-plus";

const getCurrentSemester = async function() {
    return await get(`/admin/findCurrentSemester`, {})
}
const findAllComments = async function() {
    try {
        const res = await get(`/admin/findAllComments`, {})
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.data.msg)
    } catch(err) {
        outputError(err)
    }
}

const deleteAComment = async function(commentId) {
    try {
        const res = await post(`/admin/deleteAComment?commentId=`+ commentId)
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.data.msg)
    } catch(err) {
        outputError(err)
    }
}
const findAllCourses = async function() {
    try {
        const res = await get(`/admin/findAllCourses`, {})
        console.log(res.data.data.msg)
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.data.msg)
    } catch(err) {
        outputError(err)
    }
}
const deleteACourse = async function(courseId) {
    try {
        const res = await post(`/admin/deleteACourse?courseId=`+ courseId)
        console.log(res.data.data.msg)
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.data.msg)
    } catch(err) {
        outputError(err)
    }
}

const addACourse = async function(code, name, universityId) {
    try {
        const res = await post(`/admin/addACourse`, {'code':code,'name':name,'universityId':universityId})
            // ?code=` + code +`&name=` + name + `&universityId=` + universityId
        console.log(res.data.data.msg)
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.data.msg)
    } catch(err) {
        outputError(err)
    }
}

const changeACourseLockStatus = async function(courseId, isLocked) {
    try {
        const res = await post(`/admin/changeACourseLockStatus?courseId=`+ courseId +'&isLocked=' + isLocked)
        console.log(res.data.data.msg)
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.data.msg)
    } catch(err) {
        outputError(err)
    }
}

const findAllUniversities = async function() {
    try {
        const res = await get(`/admin/findAllUniversities`)
        console.log(res.data.data.msg)
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.data.msg)
    } catch(err) {
        outputError(err)
    }
}

const outputError = function(err) {
    ElMessage({
        type: 'error',
        message: err,
    })
    return null;
}
export default {
    name: 'adminAPI',
    getCurrentSemester,
    findAllComments,
    deleteAComment,
    findAllCourses,
    deleteACourse,
    addACourse,
    changeACourseLockStatus,
    findAllUniversities,
}