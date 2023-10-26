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
        },
        {
          path: '/userInfo',
          name: 'userInfo',
          component: () => import('@/views/userInfo/userInfo.vue'),
          redirect: '/userInfo/video',
          children: [
            {
              path: '/userInfo/video',
              component: () => import('@/views/userInfo/video/Video.vue')
            },
            {
              path: '/userInfo/follow',
              component: () => import('@/views/userInfo/action/Action.vue')
            },
            {
              path: '/userInfo/fan',
              component: () => import('@/views/userInfo/action/Action.vue')
            }
          ]
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
        },
        {
          path: '/publish/videoManage',
          name: 'videoManage',
          component: () => import('@/views/publish/videoManage/VideoManage.vue')
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
