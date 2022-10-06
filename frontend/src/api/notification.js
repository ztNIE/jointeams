import { get, post } from './http'
import catchError from "@/util/catchError";

const findAllByUserId = async function(userId) {
    try {
        const res = await get(`/notification/findAllByUserId`, {params: {userId: userId}})
        if(res.request.status === 200)
            return res
        else if (res.request.status === 202)
        {
            // catchError.outputInfo(res.data.msg)
            return res
        }
        else
            throw new Error(res.data.msg)
    } catch(err) {
            return catchError.outputError(err)
    }
}

const actionOnNotification = async function(notificationId, action) {
    try {
        const res = await post('/notification/actionOnNotification?notificationId=' + notificationId + '&action=' + action)
        if(res.request.status === 200)
            return res
        else
            throw new Error(res.data.msg)
    } catch(err) {
        return catchError.outputError(err)
    }
}
export default {
    name: 'notificationAPI',
    findAllByUserId,
    actionOnNotification
}