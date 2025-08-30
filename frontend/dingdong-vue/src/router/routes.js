import DefaultLayout from '@/views/layout/DefaultLayout.vue';

export default [
  {
    path: '/',
    component: DefaultLayout,
    children: [
      {
        path: '',
        name: 'HomeMain',
        component: () => import('@/views/home/HomeMain.vue')
      }
    ]
  }
];
