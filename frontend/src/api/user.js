// sample here
import { get, post, put, del } from './http'

const getSingleUserDetails = async function(id) {
    return get(`/api/getSingleUserDetails/${id}`, null)
}
const editProfile = async function(id,editForm) {
    return put(`/api/editProfile/${id}`, editForm)
}

const newPhone = async function(id,newPhone) {
    return post(`/api/newPhone/${id}`, newPhone)
}

const postImage = async function(image) {
    return post(`/api/upload/image`, image)
}

const getAllPhone = async function(id) {
    return get(`/api/getAllPhone/${id}`,null)
}

const disablePhone = async function(id) {
    return put(`/api/disablePhone/${id}`, null)
}
const enablePhone = async function(id) {
    return put(`/api/enablePhone/${id}`, null)
}
const removePhone = async function(id) {
    return del(`/api/removePhone/${id}`, null)
}

export default {
    name: 'userPageAPI',
    getSingleUserDetails,
    editProfile,
    newPhone,
    postImage,
    getAllPhone,
    disablePhone,
    enablePhone,
    removePhone
}