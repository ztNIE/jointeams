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
                component: () => import('@/views/landing'),
            },
            {
                path: '/sign-in',
                name: 'login',
                component: () => import('@/views/login'),
            },
            {
                path: '/sign-up',
                name: 'signUp',
                component: () => import('@/views/signUp'),
            },
            {
                path: '/dashboard',
                name: 'dashboard',
                component: () => import('@/views/dashboard'),
            }
        ]
      }
    ]
  })

export default router