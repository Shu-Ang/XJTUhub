import { createRouter, createWebHistory } from 'vue-router'
import AdminLayout from '../views/admin/LayOut/index.vue'
import store from '../stores'
import IndexLayout from '../views/layout/index.vue'
const router = createRouter({

  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/admin',
      name: 'admin',
      component: AdminLayout,
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
      redirect: "/home",
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: IndexLayout,
      children: [
        {
          path: '/home',
          name: 'home',
          // route level code-splitting
          // this generates a separate chunk (About.[hash].js) for this route
          // which is lazy-loaded when the route is visited.
          component: () => import('../views/home/HomeView.vue')
        },
        {
          path: '/user',
          name: 'user',
          redirect: '/user/myarticle',
          component: () => import('../views/user/user.vue'),
          children: [
            {
              path: '/user/myarticle',
              name: 'myarticle',
              component: () => import('../views/user/MyArticle.vue')
            },
            {
              path: '/user/mycollect',
              name: 'mycollect',
              component: () => import('../views/user/MyCollect.vue')
            },
            {
              path: '/user/mycollectlist',
              name: 'mycollectlist',
              component: () => import('../views/user/MyCollectList.vue')
            },
            {
              path: '/user/myfan',
              name: 'myfan',
              component: () => import('../views/user/MyFan.vue')
            },
            {
              path: '/user/myfollow',
              name: 'myfollow',
              component: () => import('../views/user/MyFollow.vue')
            },
            {
              path: '/user/mydraft',
              name: 'mydraft',
              component: () => import('../views/user/MyDraft.vue')
            },
            {
              path: '/user/myfootprint',
              name: 'myfootprint',
              component: () => import('../views/user/MyFootprint.vue')
            },
            {
              path: '/user/myquestion',
              name: 'myquestion',
              component: () => import('../views/user/MyQuestion.vue')
            },
          ]
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
          path: '/blog/:pathMatch(.*)*',
          name: 'blog',
          // route level code-splitting
          // this generates a separate chunk (About.[hash].js) for this route
          // which is lazy-loaded when the route is visited.
          component: () => import('../views/blog/BlogDetailView.vue')
        },
        {
          path: '/draft/:pathMatch(.*)*',
          name: 'draft',
          // route level code-splitting
          // this generates a separate chunk (About.[hash].js) for this route
          // which is lazy-loaded when the route is visited.
          component: () => import('../views/edit/DraftView.vue')
        },
        {
          path: '/qa/:pathMatch(.*)*',
          name: 'qa',
          // route level code-splitting
          // this generates a separate chunk (About.[hash].js) for this route
          // which is lazy-loaded when the route is visited.
          component: () => import('../views/blog/QADetailView.vue')
        },
      ]
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

    // 将匹配所有内容并将其放在 `$route.params.pathMatch` 下
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: () => import('../views/error/404View.vue')
    },

  ]
})



export default router
