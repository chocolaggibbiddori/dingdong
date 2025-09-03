import DefaultLayout from '@/views/layout/DefaultLayout.vue';

export default [
  {
    path: '/',
    component: DefaultLayout,
    children: [
      {
        path: '',
        name: 'MainHomeView',
        component: () => import('@/views/home/MainHomeView.vue')
      },
      {
        path: 'login',
        name: 'LoginView',
        component: () => import('@/views/home/LoginView.vue')
      },
      {
        path: 'signup',
        name: 'SignupView',
        component: () => import('@/views/home/SignupView.vue')
      }
    ]
  }
];
