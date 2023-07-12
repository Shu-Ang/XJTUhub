import router from '../../router'
export default {
  namespaced: true,
  state: {
    // 侧边导航路由菜单
    asideNavs:
      localStorage.getItem('navs') ? JSON.parse(localStorage.getItem('navs')) : [
        {
          label: '仪表盘',
          path: '/admin/dashboard',
        },
        {
          label: '稿件审核',
          path: '/admin/check',
          icon: ''
        },
        {
          label: '稿件管理',
          path: '/admin/blog',
          icon: ''
        },
        {
          label: '用户管理',
          path: '/admin/user',
          icon: ''
        },
        {
          label: '评论管理',
          path: '/admin/comment',
          icon: ''
        }
      ],
    // 当前角色可以访问 权限路由数组
    authRoutes: []
  },
  mutations: {
    INIT_NAV (state, navs) {
      // 动态添加侧边导航
      state.asideNavs = navs
    },
    INIT_AUTH_ROUTES (state, authRoutes) {
      // 动态添加路由
      /*
        动态添加路由
        router.addRoute('admin', 动态路由)
      */
      authRoutes.forEach(route => {
        // 动态添加到路由上 作为admin二级路由
        router.addRoute('admin', route)
      })
    }
  }
}
