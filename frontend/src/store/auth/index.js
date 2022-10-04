import actions from "@/store/auth/actions";
import mutations from "@/store/auth/mutations";
import getters from "@/store/auth/getters"

export default {
    state() {
        return {
            email: null,
            role: null,
            userId: null,
            addRoutes: false
        }
    },
    actions,
    mutations,
    getters
}