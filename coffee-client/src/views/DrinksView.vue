<template>
  <h2 class="mb-3">Напої</h2>

  <div v-if="!currentUser" class="alert alert-warning">
    Оберіть користувача на сторінці
    <router-link to="/account">«Мій рахунок»</router-link>, щоб купувати напої.
  </div>
  <div v-else class="alert alert-info">
    Покупець: <strong>{{ currentUser.firstName }} {{ currentUser.lastName }}</strong>,
    на рахунку: <strong>{{ currentUser.balance }} грн</strong>
  </div>

  <div v-if="message" class="alert" :class="isError ? 'alert-danger' : 'alert-success'">
    {{ message }}
  </div>

  <div class="row row-cols-1 row-cols-md-3 g-3">
    <div v-for="drink in drinks" :key="drink.id" class="col">
      <div class="card h-100">
        <div class="card-body">
          <h5 class="card-title">{{ drink.name }}</h5>
          <p class="card-text mb-1">Ціна: {{ drink.price }} грн</p>
          <p class="card-text">Порцій у машині: {{ drink.portions }}</p>
        </div>
        <div class="card-footer d-flex gap-2">
          <input
              v-model.number="quantities[drink.id]"
              type="number"
              min="1"
              :max="drink.portions"
              class="form-control"
              style="max-width: 90px"
              placeholder="1"
          />
          <button
              class="btn btn-success"
              :disabled="drink.portions === 0 || !currentUser"
              @click="buy(drink)"
          >
            Купити
          </button>
        </div>
      </div>
    </div>
  </div>
  <p v-if="drinks.length === 0" class="text-muted">Напоїв ще немає.</p>
</template>

<script>
import { mapState, mapGetters, mapActions } from 'vuex'
import http from '../api/http'

const round2 = (n) => Math.round(n * 100) / 100

export default {
  name: 'DrinksView',
  data() {
    return { quantities: {}, message: '', isError: false }
  },
  computed: {
    ...mapState(['drinks']),
    ...mapGetters(['currentUser'])
  },
  methods: {
    ...mapActions(['loadDrinks', 'loadUsers']),
    show(text, isError) {
      this.message = text
      this.isError = isError
    },
    async buy(drink) {
      const qty = this.quantities[drink.id] || 1
      const total = round2(drink.price * qty)
      const user = this.currentUser

      if (qty < 1 || qty > drink.portions) {
        this.show('Недостатньо порцій у машині', true)
        return
      }
      if (total > user.balance) {
        this.show('Недостатньо коштів на рахунку', true)
        return
      }
      try {
        await http.put(`/drinks/${drink.id}`, {
          name: drink.name,
          price: drink.price,
          portions: drink.portions - qty
        })
        await http.put(`/users/${user.id}`, {
          firstName: user.firstName,
          lastName: user.lastName,
          balance: round2(user.balance - total)
        })
        await Promise.all([this.loadDrinks(), this.loadUsers()])
        this.quantities[drink.id] = null
        this.show(`Куплено: ${drink.name} × ${qty}. Списано ${total} грн`, false)
      } catch (e) {
        this.show('Помилка запиту до сервера', true)
      }
    }
  },
  created() {
    this.loadDrinks()
  }
}
</script>