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
      redirect: '/publish/publish',
      children: [
        {
          path: '/publish/publish',
          name: 'publish',
          component: () => import('@/views/publish/publish/publish.vue')
        },
        {
          path: '/publish/person',
          name: 'person',
          component: () =>
            import('@/views/publish/personalCenter/personalCenter.vue')
        }
      ]
    },
    {
      path: '/videoDetail',
      name: 'videoDetail',
      component: () => import('@/views/videoDetail/videoDetail.vue')
    }
  ]
})

export default router
