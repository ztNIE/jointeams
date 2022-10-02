import Cookies from 'js-cookie'

export default {
    isLogIn() {
        return !!Cookies.get('jointeams');
    },
    isUser(state) {
        return state.role === "ROLE_USER";
    },
    email(state) {
        return state.email;
    },
    userId(state) {
        return state.userId;
    }
}