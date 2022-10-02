import { createWebHistory, createRouter } from 'vue-router'

const history = createWebHistory()
const router = createRouter({
    history,
    routes: [
      {
        path: '/',
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
            },
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
                path: '/profile',
                name: 'profile',
                meta: {
                  icon: 'Memo',
                  capitalName: 'Profile',
                  highlight: 'profile',
                  hidden: false,
                },
                component: () => import('@/views/Profile'),
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
            },
        ]
      },
      {
        path: '/404',
        name: '404',
        component: () => import('@/views/404/404'),
      },
      { 
        path: '/:catchAll(.*)', 
        redirect: '/404',
      }
    ]
  })

export default router