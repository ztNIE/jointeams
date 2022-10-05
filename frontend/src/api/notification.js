import { get, post } from './http'
import catchError from "@/util/catchError";

const findAllByUserId = async function(userId) {
    try {
        const res = await get(`/notification/findAllByUserId`, {params: {userId: userId}})
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.data.msg)
    } catch(err) {
        if(err.message === 'No notification is found!')
            return catchError.outputInfo(err)
        else
            return catchError.outputError(err)
    }
}

const actionOnNotification = async function(notificationId, action) {
    try {
        const res = await post('/notification/actionOnNotification?notificationId=' + notificationId + '&action=' + action)
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.data.msg)
    } catch(err) {
        return catchError.outputError(err)
    }
}
export default {
    name: 'notificationAPI',
    findAllByUserId,
    actionOnNotification
}