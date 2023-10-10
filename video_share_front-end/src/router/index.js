import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'layout',
      component: () => import('@/views/layout/LayoutContainer.vue'),
      redirect: '/home',
      children: [
        {
          path: '/home',
          name: 'home',
          component: () => import('@/views/home/Home.vue')
        }
      ]
    },
    {
      path: '/publish',
      name: 'publish',
      component: () => import('@/views/publish/Publish.vue'),
      redirect: '/publish',
      children: [
        {
          path: '/publish',
          name: 'publish',
          component: () => import('@/views/publish/publish/publish.vue')
        }
      ]
    }
  ]
})

export default router
