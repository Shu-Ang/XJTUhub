import { createStore } from 'vuex'
import base from './modules/base'
import permission from './modules/permission'
import user from './modules/user'
export default createStore({
  modules: {
    base,
    permission,
    user
  }
})
