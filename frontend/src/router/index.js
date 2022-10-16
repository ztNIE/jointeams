import {createWebHistory, createRouter} from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

NProgress.configure({showSpinner: false})

const staticRoutes = [
    {
        path: '/',
        name: 'root',
        redirect: 'landing',

        children: [
            {
                path: '/landing',
                name: 'landing',
                component: () => import('@/views/Landing'),
            },
            {
                path: '/sign-in',
                name: 'login',
                component: () => import('@/views/Login'),
            },
            {
                path: '/sign-up',
                name: 'register',
                component: () => import('@/views/Register'),
            },
            {
                path: '/verify/register/:token',
                name: 'verifyRegisterToken',
                component: () => import('@/views/VerifyRegisterToken')
            },
            {
                path: '/reset-password/:token',
                name: 'resetPassword',
                component: () => import('@/views/ResetPassword')
            }
        ]
    },
    {
        path: '/404',
        name: '404',
        component: () => import('@/views/404/404'),
    },
]

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

// create router
const history = createWebHistory()
const router = createRouter({
    history,
    routes: staticRoutes
})

// handle dynamic routes
// execute every time loading a new page
import store from '@/store'
import {ElMessage} from 'element-plus'
import Cookies from "js-cookie";

let remove_not_found_route = null;

router.beforeEach(async (to, from, next) => {
    NProgress.start()

    // handle not logged in
    const whiteList = ['/landing', '/sign-in', '/sign-up', '/verify/register/', '/reset-password/', '/404']
    if (whiteList.indexOf(to.path) === -1 && to.path.indexOf('/verify/register/') === -1 && to.path.indexOf('/reset-password/') === -1 && !Cookies.get('jointeams')) {
        ElMessage.info('Please log in first!')
        next('/sign-in')
        NProgress.done()
    } else {

        if (remove_not_found_route) {
            remove_not_found_route()
        }
        let dynamicRoutes = []

        if (!store.getters.addRoutes) {
            if (Cookies.get('jointeams')) {
                if (store.getters.isUser) {
                    dynamicRoutes = userRoutes
                } else {
                    dynamicRoutes = adminRoutes
                }
                // deep copy
                let temp = [
                    {
                        path: '/',
                        name: 'root',
                        redirect: 'landing',
                        children: [
                            {
                                path: '/landing',
                                name: 'landing',
                                component: () => import('@/views/Landing'),
                            },
                            {
                                path: '/sign-in',
                                name: 'login',
                                component: () => import('@/views/Login'),
                            },
                            {
                                path: '/sign-up',
                                name: 'register',
                                component: () => import('@/views/Register'),
                            },
                            {
                                path: '/verify/register/:token',
                                name: 'verifyRegisterToken',
                                component: () => import('@/views/VerifyRegisterToken')
                            },
                            {
                                path: '/reset-password/:token',
                                name: 'resetPassword',
                                component: () => import('@/views/ResetPassword')
                            }
                        ]
                    },
                    {
                        path: '/404',
                        name: '404',
                        component: () => import('@/views/404/404'),
                    },
                ]

                dynamicRoutes.forEach((route) => {
                    router.addRoute('root', route)
                    temp[0].children.unshift(route)
                })

                // dynamically add in the 404 not found route
                const notFound = {
                    path: '/:catchAll(.*)',
                    redirect: '/404',
                }
                remove_not_found_route = router.addRoute(notFound)
                temp.push(notFound)

                localStorage.setItem('routes', JSON.stringify(temp))
                store.commit('setAddRoutes', true)
                next({...to, replace: true})
            } else {
                next()
            }

        } else {
            next()
        }
    }
})

router.afterEach(() => {
    NProgress.done()
})

export default router

export {
    userRoutes,
    adminRoutes
}