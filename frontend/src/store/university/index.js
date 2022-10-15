import {getUniversities} from "@/api/auth";

export default {
    namespaced: true,
    state() {
        return {
            universities: null
        }
    },
    getters: {
        universities(state) {
            return state.universities
        }
    },
    actions: {
        async loadUniversities(context) {
            const response = await getUniversities()
            const universityData = response.data.data.filter((uni) => {
                return uni.name !== "Test University"
            })
            context.commit('loadUniversities', universityData)
        }
    },
    mutations: {
        loadUniversities(state, payload) {
            state.universities = payload;
        }
    }
}