import TourView from '@/views/TourView.vue'
import TourenListeView from '@/views/TourenListeView.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:"/tour",
      name: "TourListeView",
      component: TourenListeView
    },
    {
      path: "/",
      name:"home",
      redirect: '/tour'
    },
    {
      path:"/tour/:tourid",
      component: TourView,
      props: true
    }
    
  ]
})

export default router
