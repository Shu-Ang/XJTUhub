import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../views/admin/LayOut/index.vue'
import store from '../stores'
const router = createRouter({

  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/admin',
      name: 'admin',
      component: Layout,
      children: [
        {
          path: 'dashboard',
          name: 'Dashboard',
          component: () => import('../views/admin/BoardView.vue'),
          meta: {
            title: '仪表盘'
          }
        },
        {
          path: 'check',
          name: 'Check',
          component: () => import('../views/admin/CheckView.vue'),
          meta: {
            title: '稿件审核'
          }
        },
        {
          path: 'blog',
          name: 'Blog',
          component: () => import('../views/admin/BlogView.vue'),
          meta: {
            title: '稿件管理'
          }
        },
        {
          path: 'user',
          name: 'User',
          component: () => import('../views/admin/UserView.vue'),
          meta: {
            title: '用户管理'
          }
        },
        {
          path: 'comment',
          name: 'Comment',
          component: () => import('../views/admin/CommentView.vue'),
          meta: {
            title: '评论管理'
          }
        }
      ]
    },
    {
      path: '/',
      name: 'index',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/home',
      name: 'home',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/login',
      name: 'login',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/logup&in/LoginView.vue')
    },
    {
      path: '/register',
      name: 'register',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/logup&in/RegisterView.vue')
    },
    {
      path: '/activate/:pathMatch(.*)*',
      name: 'activate',
      component: () => import('../views/logup&in/ActivateView.vue')
    },
    {
      path: '/edit',
      name: 'edit',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/edit/EditorView.vue')
    },
    {
      path: '/info',
      name: 'info',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/info/InfoView.vue')
    },
    {
      path: '/blog/:pathMatch(.*)*',
      name: 'blog',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/blog/BlogDetailView.vue')
    },
    // 将匹配所有内容并将其放在 `$route.params.pathMatch` 下
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: () => import('../views/error/404View.vue')
    },

  ]
})



export default router
