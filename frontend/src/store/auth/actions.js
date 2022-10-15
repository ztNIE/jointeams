import {postLogin} from "@/api/auth";
import Cookies from "js-cookie";
import router from "@/router";

const userRoutes = [
    {
        path: '/dashboard',
        name: 'dashboard',
        meta: {
            icon: 'Odometer',
            capitalName: 'Dashboard',
            highlight: 'dashboard',
            hidden: false,
        },
        component: () => import('@/views/Dashboard'),
    },
    {
        path: '/myProfile',
        name: 'myProfile',
        meta: {
            icon: 'Memo',
            capitalName: 'My Profile',
            highlight: 'myProfile',
            hidden: false,
        },
        component: () => import('@/views/profile/MyProfile'),
    },
    {
        path: '/editProfile',
        name: 'editProfile',
        meta: {
            highlight: 'myProfile',
            hidden: true,
        },
        component: () => import('@/views/profile/EditProfile'),
    },
    {
        path: '/userProfile/:id',
        name: 'userProfile',
        meta: {
            hidden: true,
        },
        component: () => import('@/views/profile/UserProfile'),
    },
    {
        path: '/courseDetails/:course_id',
        name: 'courseDetails',
        meta: {
            hidden: true
        },
        props: true,
        component: () => import('@/views/CourseDetails/CourseDetails')
    },
    {
        path: '/notification',
        name: 'notification',
        meta: {
            icon: 'Notification',
            capitalName: 'Notifications',
            highlight: 'notification',
            hidden: false,
        },
        component: () => import('@/views/Notification'),
    },
    {
        path: '/myGroups',
        name: 'myGroups',
        meta: {
            icon: 'User',
            capitalName: 'My Groups',
            highlight: 'myGroups',
            hidden: false,
        },
        component: () => import('@/views/MyGroups'),
    },
    {
        path: '/groupDetails/:group_id',
        name: 'groupDetails',
        meta: {
            hidden: true,
        },
        component: () => import('@/views/GroupDetails'),
    },
    {
        path: '/courseGroups/:course_id',
        name: 'courseGroups',
        meta: {
            hidden: true,
        },
        component: () => import('@/views/CourseGroups'),
    }
]

const adminRoutes = [
    {
        path: '/comment',
        name: 'comment_admin',
        meta: {
            icon: 'ChatSquare',
            capitalName: 'Comment',
            highlight: 'comment_admin',
            hidden: false,
        },
        component: () => import('@/views/admin/Comment'),
    },
    {
        path: '/course',
        name: 'course_admin',
        meta: {
            icon: 'Management',
            capitalName: 'Course',
            highlight: 'course_admin',
            hidden: false,
        },
        component: () => import('@/views/admin/Course'),
    },
    {
        path: '/semester',
        name: 'semester_admin',
        meta: {
            icon: 'Calendar',
            capitalName: 'Semester',
            highlight: 'semester_admin',
            hidden: false,
        },
        component: () => import('@/views/admin/Semester'),
    },
]

const authRoute = userRoutes.concat(adminRoutes)

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
        localStorage.removeItem('route')
        context.commit('setAddRoutes', false)
        context.commit('setUserLogin', {
            email: null,
            role: null,
            userId: null
        })
        authRoute.forEach((route) => {
            if (router.hasRoute(route.name)) {
                router.removeRoute(route.name)
            }
        })
    },
}