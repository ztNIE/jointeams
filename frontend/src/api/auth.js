import {post} from './http'

export const postRegister = async function (registerData) {
    return post('/register', registerData)
}

export default {
    name: 'authAPI',
    postRegister
}