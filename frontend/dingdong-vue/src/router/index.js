import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: () => import('@/views/layout/DefaultLayout.vue'),
      children: [
        {
          path: '',
          name: 'home',
          component: () => import('@/views/home/HomeMain.vue')
        }
      ]
    }
  ]
});

export default router;
