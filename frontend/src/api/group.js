// import { get, post, put, del } from './http'
import { get } from './http'

export default class GroupAPI {
    static async findAllCurrentGroupsOfAUser(userId) {
        try {
            const res = await get('/api/group/myGroups', userId)
            return res.data
        } catch(err) {
            return err.response
        }
    }
}