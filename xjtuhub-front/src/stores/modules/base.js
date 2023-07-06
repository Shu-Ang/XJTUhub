import router from '../../router'
// 缓存navTabs
const persistNavTabs = (navTabs) => {
  sessionStorage.setItem('navTabs', JSON.stringify(navTabs))
}
export default {
  namespaced: true,
  state: {
    navCollapse: false, // 侧边导航折叠
    // 取缓存
    navTabs: sessionStorage.getItem('navTabs') ? JSON.parse(sessionStorage.getItem('navTabs')) : []
  },
  mutations: {
    // 切换折叠状态
    TOGGLE_MENU (state) {
      state.navCollapse = !state.navCollapse
    },
    // 添加 顶部 navTab
    ADD_NAV_TAB (state, tab) {
      /*
        1 navTabs如果已经有了 该 导航tab 不添加
        2 如果当前 跳转的是首页 仪表盘页 login页 无权限页 也不应该添加
      */
      const noAddPath = ['/dashBoard', '/login', '/noAuth']
      if (state.navTabs.find(_tab => _tab.path === tab.path) || noAddPath.includes(tab.path)) {
        // 当前  navTabs已经有了该导航 不添加
        return false
      }
      // 如果是dashBoard也不添加
      state.navTabs.push(tab)
      persistNavTabs(state.navTabs)
    },
    // 删除navtab
    DEL_TAB (state, index) {
      state.navTabs.splice(index, 1)
      // 如果 已经全部删除 跳转 仪表盘页
      if (state.navTabs.length === 0) {
        router.replace('/dashBoard')
        return false
      }
      // 删除tab路由跳转到 navTabs最后一个的路由
      router.replace(state.navTabs[state.navTabs.length - 1].path)
      persistNavTabs(state.navTabs)
    },
    // 删除其他 navTab
    DEL_OTHER_TAB (state, index) {
      // 保留当前  删除其他
      state.navTabs = state.navTabs.filter((tab, i) => i === index)
      router.replace(state.navTabs[0].path)// 只剩自身  所以下标为0
      persistNavTabs(state.navTabs)
    },
    // 删除所有
    DEL_ALL_TAB (state) {
      state.navTabs = []
      router.replace('/dashBoard')
      persistNavTabs(state.navTabs)
    }
  }
}
