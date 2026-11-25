import { createStore } from 'vuex'
import http from '../api/http'

export default createStore({
  state: {
    drinks: [],
    users: [],
    currentUserId: null
  },
  getters: {
    currentUser: (state) =>
        state.users.find((u) => u.id === state.currentUserId) || null
  },
  mutations: {
    setDrinks(state, drinks) {
      state.drinks = drinks
    },
    setUsers(state, users) {
      state.users = users
    },
    setCurrentUser(state, id) {
      state.currentUserId = id
    }
  },
  actions: {
    async loadDrinks({ commit }) {
      const { data } = await http.get('/drinks')
      commit('setDrinks', data)
    },
    async loadUsers({ commit }) {
      const { data } = await http.get('/users')
      commit('setUsers', data)
    }
  }
})