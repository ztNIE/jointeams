import {postLogin} from "@/api/auth";

export default {
    state() {
        return {
            email: null,
            role: null,
            userId: null
        }
    },
    actions: {
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
        }
    },
    mutations: {
        setUserLogin(state, payload) {
            console.log(payload)
            state.email = payload.email
            state.role = payload.role
            state.userId = payload.userId
        }
    },
    getters: {
        isLogIn(state) {
            return (!!state.email) && (!!state.role);
        },
        isUser(state) {
            return state.role === "ROLE_USER";
        },
        email(state) {
            return state.email;
        },
        userId() {
            return localStorage.getItem('userId');
        }
    }
}