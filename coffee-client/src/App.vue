<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
      <router-link class="navbar-brand" to="/">☕ Кава-машина</router-link>
      <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#mainMenu"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div id="mainMenu" class="collapse navbar-collapse">
        <ul class="navbar-nav me-auto">
          <li class="nav-item">
            <router-link class="nav-link" to="/">Головна</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/drinks">Напої</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/account">Мій рахунок</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/admin">Адміністратор</router-link>
          </li>
        </ul>
        <span v-if="currentUser" class="navbar-text">
          {{ currentUser.firstName }} {{ currentUser.lastName }}:
          {{ currentUser.balance }} грн
        </span>
      </div>
    </div>
  </nav>

  <main class="container py-4">
    <router-view />
  </main>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'App',
  computed: {
    ...mapGetters(['currentUser'])
  },
  methods: {
    ...mapActions(['loadUsers'])
  },
  created() {
    this.loadUsers()
  }
}
</script>

<style>
.navbar-nav .nav-link.router-link-exact-active {
  color: #fff;
  font-weight: 600;
  border-bottom: 2px solid #fff;
}
</style>