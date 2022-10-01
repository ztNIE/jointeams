import {postLogin} from "@/api/auth";

export default {
    async login(context, loginRequest) {
        const response = await postLogin(loginRequest)
        console.log(response)
        const email = response.data.data.email
        const role = response.data.data.roles[0]
        const userId = response.data.data.userId
        localStorage.setItem('email', email)
        localStorage.setItem('role', role)
        localStorage.setItem('userId', userId)
        context.commit('setUserLogin', {
            email: email,
            role: role,
            userId: userId
        })
    },
    tryAutoLogin(context) {
        if (!context.rootGetters.isLogIn) {
            return false;
        }

        const role = localStorage.getItem('role');
        context.commit('setUserLogin', {
            email: localStorage.getItem('email'),
            role: role,
            userId: localStorage.getItem('userId')
        })
        return true;
    }
}