import {postLogin} from "@/api/auth";
import Cookies from "js-cookie";

export default {
    async login(context, loginRequest) {
        const response = await postLogin(loginRequest)
        const email = response.data.data.email
        const role = response.data.data.roles[0] 
        const userId = response.data.data.userId
        localStorage.setItem('email', email)
        localStorage.setItem('role', role)
        localStorage.setItem('userId', userId)
        await context.commit('setUserLogin', {
            email: email,
            role: role,
            userId: userId
        })
        return role;
    },
    tryAutoLogin(context) {
        if (!Cookies.get('jointeams')) {
            return false;
        }

        const role = localStorage.getItem('role');
        context.commit('setUserLogin', {
            email: localStorage.getItem('email'),
            role: role,
            userId: localStorage.getItem('userId')
        })
        return true;
    },
    logout(context) {
        Cookies.remove('jointeams')
        localStorage.removeItem('email')
        localStorage.removeItem('role')
        localStorage.removeItem('userId')
        context.commit('setUserLogin', {
            email: null,
            role: null,
            userId: null
        })
    },
}