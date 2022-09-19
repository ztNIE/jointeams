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
            }
        ]
      }
    ]
  })

export default router