<template>
  <h2 class="mb-3">Адміністратор: наповнення кава-машини</h2>
  <div v-if="message" class="alert alert-success">{{ message }}</div>

  <h4>Напої</h4>
  <table class="table align-middle">
    <thead>
    <tr>
      <th>#</th>
      <th>Назва</th>
      <th>Ціна, грн</th>
      <th>Порцій</th>
      <th style="width: 260px">Наповнити</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="d in drinks" :key="d.id">
      <td>{{ d.id }}</td>
      <td>{{ d.name }}</td>
      <td>{{ d.price }}</td>
      <td>{{ d.portions }}</td>
      <td>
        <div class="input-group input-group-sm">
          <input
              v-model.number="drinkAdd[d.id]"
              type="number"
              min="1"
              class="form-control"
              placeholder="+ порцій"
          />
          <button class="btn btn-success" @click="refill('drinks', d, drinkAdd)">
            Додати
          </button>
        </div>
      </td>
    </tr>
    </tbody>
  </table>

  <h4 class="mt-4">Додаткові інгредієнти</h4>
  <table class="table align-middle">
    <thead>
    <tr>
      <th>#</th>
      <th>Назва</th>
      <th>Ціна, грн</th>
      <th>Порцій</th>
      <th style="width: 260px">Наповнити</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="i in ingredients" :key="i.id">
      <td>{{ i.id }}</td>
      <td>{{ i.name }}</td>
      <td>{{ i.price }}</td>
      <td>{{ i.portions }}</td>
      <td>
        <div class="input-group input-group-sm">
          <input
              v-model.number="ingredientAdd[i.id]"
              type="number"
              min="1"
              class="form-control"
              placeholder="+ порцій"
          />
          <button class="btn btn-success" @click="refill('ingredients', i, ingredientAdd)">
            Додати
          </button>
        </div>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="card mt-4">
    <div class="card-body">
      <h5 class="card-title">Нова позиція</h5>
      <div class="row g-2">
        <div class="col-md-3">
          <select v-model="form.kind" class="form-select">
            <option value="drinks">Напій</option>
            <option value="ingredients">Інгредієнт</option>
          </select>
        </div>
        <div class="col-md-3">
          <input v-model="form.name" class="form-control" placeholder="Назва" />
        </div>
        <div class="col-md-2">
          <input v-model.number="form.price" type="number" min="0" class="form-control" placeholder="Ціна" />
        </div>
        <div class="col-md-2">
          <input v-model.number="form.portions" type="number" min="0" class="form-control" placeholder="Порцій" />
        </div>
        <div class="col-md-2">
          <button class="btn btn-primary w-100" @click="addItem">Додати</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from '../api/http'

export default {
  name: 'AdminView',
  data() {
    return {
      drinks: [],
      ingredients: [],
      drinkAdd: {},
      ingredientAdd: {},
      form: { kind: 'drinks', name: '', price: null, portions: null },
      message: ''
    }
  },
  methods: {
    async loadAll() {
      const [d, i] = await Promise.all([http.get('/drinks'), http.get('/ingredients')])
      this.drinks = d.data
      this.ingredients = i.data
    },
    async refill(kind, item, addMap) {
      const add = addMap[item.id]
      if (!add || add <= 0) return
      await http.put(`/${kind}/${item.id}`, {
        name: item.name,
        price: item.price,
        portions: item.portions + add
      })
      addMap[item.id] = null
      await this.loadAll()
      this.message = `${item.name}: додано ${add} порцій`
    },
    async addItem() {
      const f = this.form
      if (!f.name || f.price == null || f.portions == null) return
      await http.post(`/${f.kind}`, {
        name: f.name,
        price: f.price,
        portions: f.portions
      })
      this.message = `Додано: ${f.name}`
      this.form = { kind: f.kind, name: '', price: null, portions: null }
      await this.loadAll()
    }
  },
  created() {
    this.loadAll()
  }
}
</script>