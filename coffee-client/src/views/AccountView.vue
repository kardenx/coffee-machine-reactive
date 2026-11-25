<template>
  <h2 class="mb-3">Мій рахунок</h2>
  <div v-if="message" class="alert alert-success">{{ message }}</div>

  <div class="row">
    <div class="col-md-7">
      <table class="table table-hover align-middle">
        <thead>
        <tr>
          <th>#</th>
          <th>Користувач</th>
          <th>Баланс, грн</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr
            v-for="u in users"
            :key="u.id"
            :class="{ 'table-primary': u.id === currentUserId }"
        >
          <td>{{ u.id }}</td>
          <td>{{ u.firstName }} {{ u.lastName }}</td>
          <td>{{ u.balance }}</td>
          <td class="text-end">
            <button class="btn btn-sm btn-outline-primary" @click="setCurrentUser(u.id)">
              Обрати
            </button>
          </td>
        </tr>
        <tr v-if="users.length === 0">
          <td colspan="4" class="text-muted">Користувачів ще немає</td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="col-md-5">
      <div class="card mb-3">
        <div class="card-body">
          <h5 class="card-title">Новий користувач</h5>
          <input v-model="form.firstName" class="form-control mb-2" placeholder="Ім'я" />
          <input v-model="form.lastName" class="form-control mb-2" placeholder="Прізвище" />
          <input
              v-model.number="form.balance"
              type="number"
              min="0"
              class="form-control mb-2"
              placeholder="Початковий баланс"
          />
          <button class="btn btn-primary" @click="createUser">Створити</button>
        </div>
      </div>

      <div v-if="currentUser" class="card">
        <div class="card-body">
          <h5 class="card-title">Поповнення рахунку</h5>
          <p>
            {{ currentUser.firstName }} {{ currentUser.lastName }}:
            {{ currentUser.balance }} грн
          </p>
          <div class="input-group">
            <input
                v-model.number="topUp"
                type="number"
                min="1"
                class="form-control"
                placeholder="Сума"
            />
            <button class="btn btn-success" @click="addMoney">Поповнити</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from 'vuex'
import http from '../api/http'

export default {
  name: 'AccountView',
  data() {
    return {
      form: { firstName: '', lastName: '', balance: null },
      topUp: null,
      message: ''
    }
  },
  computed: {
    ...mapState(['users', 'currentUserId']),
    ...mapGetters(['currentUser'])
  },
  methods: {
    ...mapMutations(['setCurrentUser']),
    ...mapActions(['loadUsers']),
    async createUser() {
      const f = this.form
      if (!f.firstName || !f.lastName) return
      await http.post('/users', {
        firstName: f.firstName,
        lastName: f.lastName,
        balance: f.balance || 0
      })
      await this.loadUsers()
      this.form = { firstName: '', lastName: '', balance: null }
      this.message = 'Користувача створено'
    },
    async addMoney() {
      if (!this.topUp || this.topUp <= 0) return
      const u = this.currentUser
      await http.put(`/users/${u.id}`, {
        firstName: u.firstName,
        lastName: u.lastName,
        balance: Math.round((u.balance + this.topUp) * 100) / 100
      })
      await this.loadUsers()
      this.message = `Рахунок поповнено на ${this.topUp} грн`
      this.topUp = null
    }
  },
  created() {
    this.loadUsers()
  }
}
</script>