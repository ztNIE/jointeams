import router from "@/router";
import store from "@/store";
import Cookies from "js-cookie";
import {ElMessage} from "element-plus";

export default function authenticateIdentity(role) {
    const cookie = Cookies.get('jointeams');
    if (!cookie) {
        router.replace('/sign-in')
        ElMessage("Please log in first")
        return
    }
    if (store.getters.isUser && role === 'ROLE_ADMIN') {
        console.log("Should jump to admin page")
        return
    }
    if (!store.getters.isUser && role === 'ROLE_USER') {
        router.replace('/dashboard')
        return
    }
}