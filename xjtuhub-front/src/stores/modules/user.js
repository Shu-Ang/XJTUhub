export default {
  namespaced: true,
  state: { // 刷新之后判断缓存
    userInfo: localStorage.getItem('userInfo') ? JSON.parse(localStorage.getItem('userInfo')) : {},
    token: localStorage.getItem('token') || '',
    role: localStorage.getItem('role') || ''
  },
  mutations: {
    // 登录成功初始化 用户信息 接口三个属性
    INIT_USER (state, { userInfo, token, role }) {
      state.userInfo = userInfo
      state.token = token
      state.role = role
    },
    USER_LOGOUT (state) {
      // 退出登录 清除
      localStorage.removeItem('userInfo')
      localStorage.removeItem('token')
      localStorage.removeItem('role')
      localStorage.removeItem('nav')
      localStorage.removeItem('authRoutes')
      // 请求上次的navTab
      sessionStorage.removeItem('navTabs')
    }
  }
}
