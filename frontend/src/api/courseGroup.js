import { get, post, put } from './http'

export default class CourseGroupAPI {
    static async getTutorial(userId, courseId) {
        try {
            var params = new URLSearchParams();
            params.append("userId", userId)
            params.append("courseId", courseId)
            var request = {
                params: params
            }
            const res = await get(`/course/getTutorial`, request)
            return res
        } catch(err) {
            return err.response
        }
    }

    static async setTutorial(userId, courseId, tutorial) {
        try {
            const res = await put('/course/setTutorial?userId=' + userId + "&courseId=" + courseId + "&tutorial=" + tutorial)
            return res
        } catch(err) {
            return err.response
        }
    }

    static async getAllGroupsInOneCourse(courseId, userId) {
        try {
            var params = new URLSearchParams()
            params.append("courseId", courseId)
            params.append("userId", userId)
            var request = {
                params: params
            }

            const res = await get(`/courseGroup/getAllGroupsInOneCourse`, request)
            return res
        } catch(err) {
            return err.response
        }
    }

    static async addAGroup(courseId, userId, capacity) {
        try {
            const res = await post('/courseGroup/addAGroup?courseId=' + courseId + "&userId=" + userId + "&capacity=" + capacity)
            return res
        } catch(err) {
            return err.response
        }
    }
}