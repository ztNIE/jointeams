import {post, get} from './http'

export const postRegister = async function (registerData) {
    return post('/register', registerData)
}

export const getEmailExist = async function (email) {
    return get(`/auth/validEmailExist?email=${email}`)
}

export const postLogin = async function (loginRequest) {
    return post('/auth/login', loginRequest)
}

export const getUniversities = async function () {
    return get()
}

export default {
    name: 'authAPI',
}