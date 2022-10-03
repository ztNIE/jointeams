import { get, post } from './http'
import {ElMessage} from "element-plus";

const findAllByUserId = async function(userId) {
    try {
        const res = await get(`/notification/findAllByUserId`, {params: {userId: userId}})
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.data.msg)
    } catch(err) {
        ElMessage({
            type: 'error',
            message: err,
        })
        return null
    }
}

const actionOnNotification = async function(notificationId, action) {
    try {
        const res = await post('/notification/actionOnNotification?notificationId=' + notificationId + "&action=" + action)
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.data.msg)
    } catch(err) {
        ElMessage({
            type: 'error',
            message: err,
        })
        return null
    }
}
export default {
    name: 'NotificationAPI',
    findAllByUserId,
    actionOnNotification
}