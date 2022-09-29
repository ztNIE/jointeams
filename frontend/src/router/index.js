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
                path: '/dashboard',
                name: 'dashboard',
                icon: 'Odometer',
                capitalName: 'Dashboard',
                component: () => import('@/views/Dashboard'),
            },
            {
                path: '/profile',
                name: 'profile',
                icon: 'Memo',
                capitalName: 'Profile',
                component: () => import('@/views/Profile'),
            },
            {
                path: '/notification',
                name: 'notification',
                icon: 'Notification',
                capitalName: 'Notifications',
                component: () => import('@/views/Notification'),
            },
            {
                path: '/myGroups',
                name: 'myGroups',
                icon: 'User',
                capitalName: 'My Groups',
                component: () => import('@/views/MyGroups'),
            },
            {
                path: '/groupDetails/:group_id',
                name: 'groupDetails',
                component: () => import('@/views/GroupDetails'),
            }
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