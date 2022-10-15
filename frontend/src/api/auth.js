import {get, post} from './http'

export const postRegister = async function (registerData) {
    return post('/register', registerData)
}

export const getEmailExist = async function (email) {
    return get(`/auth/validEmailExist?email=${email}`)
}

export const getEmailActivate = async function (email) {
    return get(`/auth/validEmailActivate?email=${email}`)
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

export const postReCaptchaToken = async function (token, action) {
    return post('auth/validReCaptchaToken', {
        token, action
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