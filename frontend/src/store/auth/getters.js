export default {
    isUser(state) {
        console.log(state.role)
        return state.role === "ROLE_USER";
    },
    email(state) {
        return state.email;
    },
    userId(state) {
        return state.userId;
    },
    addRoutes(state) {
        return state.addRoutes
    }
}