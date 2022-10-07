export default {
    setUserLogin(state, payload) {
        state.email = payload.email
        state.role = payload.role
        state.userId = payload.userId
    },
    setAddRoutes(state, payload) {
        state.addRoutes = payload
    }
}