/* eslint-disable import/first */
import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

import user from './modules/user'
import role from './modules/role'
import '../../node_modules/font-awesome/css/font-awesome.css'

export default new Vuex.Store({
  strict: debug,
  modules: {
    user,
    role
  }
})
