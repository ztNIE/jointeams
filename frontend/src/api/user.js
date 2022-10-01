import { get } from './http'

const getUserInfo = async function(userId) {
    return await get(`/user/getUserInfoById`, {params: {id: userId}})
}

export default {
    name: 'userAPI',
    getUserInfo
}