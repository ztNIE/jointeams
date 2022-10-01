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
    return get('/university/getall')
}

export const getRegistrationVerify = async function (token) {
    return get(`/register/verify/?token=${token}`)
}

export const postBeginResetPassword = async function (email) {
    return post('/register/resetPassword', {
        email: email
    })
}

export const getIsPasswordTokenExist = async function (token) {
    return get(`/register/isResetTokenExist?token=${token}`)
}

export const postSavePassword = async function (token, newPassword) {
    return post(`/register/savePassword?token=${token}`,
        {newPassword: newPassword})
}

export default {
    name: 'authAPI',
}