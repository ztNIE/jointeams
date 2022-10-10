import router from "@/router";
import store from "@/store";
import Cookies from "js-cookie";
import {ElMessage} from "element-plus";

// DEPRECATED: Use dynamic routing
// const authenticateIdentity = function (role) {
//     const cookie = Cookies.get('jointeams');
//     if (!cookie) {
//         router.replace('/sign-in')
//         ElMessage("Please log in first")
//         return
//     }
//     if (!store.getters.isUser && role === 'ROLE_ADMIN') {
//         router.replace('/comment')
//         return
//     }
//     if (!store.getters.isUser && role === 'ROLE_USER') {
//         router.replace('/dashboard')
//         return
//     }
// }

const isLogin = function () {
    return !!Cookies.get('jointeams')
}

const skipLogin = function () {
    if (isLogin()) {
        if (store.getters.isUser) {
            router.push('/dashboard')
            ElMessage("You've already logged in")
        } else {
            router.push('/comment')
            ElMessage("You've already logged in")
        }
    }
}

export default {
    name: 'authUtil',
    // authenticateIdentity,
    isLogin,
    skipLogin
}
