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
                component: () => import('@/views/Dashboard'),
            }
        ]
      }
    ]
  })

export default router