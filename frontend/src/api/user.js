import { get, put } from './http'
import axios from 'axios'

const getUserInfo = async function(userId) {
    return await get(`/user/getUserInfoById`, {params: {id: userId}})
}

const uploadAvatar = async function(form) {
    let config = {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    }
    return axios.post('/user/uploadAvatar', form, config)
}


const updateUserInfoById = async function(userId, data) {
    return await put('/user/updateUserInfoById?id=' + userId, data)
}

const getAvatar = async function(fileName) {
    return await get('/user/getAvatar', {params: {fileName: fileName}})
}

export default {
    name: 'userAPI',
    getUserInfo,
    uploadAvatar,
    updateUserInfoById,
    getAvatar
}