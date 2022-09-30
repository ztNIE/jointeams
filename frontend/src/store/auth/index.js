import {postLogin} from "@/api/auth";

export default {
    state() {
        return {
            email: null,
            role: null
        }
    },
    actions: {
        async login(context, loginRequest) {
            const response = await postLogin(loginRequest)
            console.log(response)
            const email = response.data.data.email
            const role = response.data.data.roles[0]
            localStorage.setItem('email', email)
            localStorage.setItem('role', role)
            context.commit('setUserLogin', {
                email: email,
                role: role
            })
        }
    },
    mutations: {
        setUserLogin(state, payload) {
            console.log(payload)
            state.email = payload.email
            state.role = payload.role
        }
    },
    getters: {
        isLogIn(state) {
            return (!!state.email) && (!!state.role);
        },
        isUser(state) {
            return state.role === "ROLE_USER";
        }
    }
}