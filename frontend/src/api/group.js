import { get, post, put } from './http'

export default class GroupAPI {
    static async findAllCurrentGroupsOfAUser(userId) {
        try {
            const res = await get(`/group/myGroups`, {params: {userId: userId}})
            return res
        } catch(err) {
            return err.response
        }
    }

    static async getGroupInformationById(id) {
        try {
            const res = await get(`/group/getGroupInformationById`, {params: {id: id}})
            return res
        } catch(err) {
            return err.response
        }
    }

    static async getAllMembers(id) {
        try {
            const res = await get(`/group/getAllMembers`, {params: {id: id}})
            return res
        } catch(err) {
            return err.response
        }
    }

    static async updateDescription(id, newDescription) {
        try {
            // const res = await put(`/group/updateDescription`, {id: id, newDescription: newDescription})
            const res = await put('/group/updateDescription?id=' + id + "&newDescription=" + newDescription)
            return res
        } catch(err) {
            return err.response
        }
    }

    static async leaveGroup(groupId, userId) {
        try {
            const res = await put('/group/leaveGroup?groupId=' + groupId + '&userId=' + userId)
            return res
        } catch(err) {
            return err.response
        }
    }

    static async isCommentFunctionOpen() {
        try {
            const res = await get(`/group/isCommentFunctionOpen`)
            return res
        } catch(err) {
            return err.response
        }
    }

    static async checkCommentExistence(groupId, senderId, receiverId) {
        try {
            var params = new URLSearchParams();
            params.append("groupId", groupId)
            params.append("senderId", senderId)
            params.append("receiverId", receiverId)
            var request = {
                params: params
            }
            const res = await get(`/group/checkCommentExistence`, request)
            return res
        } catch(err) {
            return err.response
        }
    }

    static async leaveComment(comment) {
        try {
            const res = await post(`/group/leaveComment`, comment)
            return res
        } catch(err) {
            return err.response
        }
    }

    static async sendInvitation(groupId, userId) {
        const res = await post(`/group/sendInvitation?groupId=` + groupId + '&userId=' + userId)
        return res
    }

    static async sendJoinRequest(groupId, userId) {
        try {
            const res = await post('/group/sendJoinRequest?groupId=' + groupId + '&userId=' + userId)
            return res
        } catch(err) {
            return err.response
        }
    }

    static async listStudentsNotInAGroup(courseId) {
        try {
            const res = await get(`/group/listStudentsNotInAGroup`, {params: {courseId: courseId}})
            return res
        } catch(err) {
            return err.response
        }
    }
}