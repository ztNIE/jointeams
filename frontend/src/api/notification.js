import { get, post } from './http'

const findAllByUserId = async function(userId) {
    try {
        const res = await get(`/notification/findAllByUserId`, {params: {userId: userId}})
        return res
    } catch(err) {
        return err.response
    }
}

const actionOnNotification = async function(notificationId, action) {
    try {
        const res = await post('/notification/actionOnNotification?notificationId=' + notificationId + "&action=" + action)
        return res
    } catch(err) {
        return err.response
    }
}
export default {
    name: 'NotificationAPI',
    findAllByUserId,
    actionOnNotification
}