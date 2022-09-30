import {post, get} from './http'

export const postRegister = async function (registerData) {
    return post('/register', registerData)
}

export const getEmailExist = async function (email) {
    return get('/auth/validEmailExist', {email: email})
}

export default {
    name: 'authAPI',
    postRegister
}