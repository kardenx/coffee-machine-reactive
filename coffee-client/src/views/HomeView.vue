<template>
  <div class="p-5 mb-4 bg-light rounded-3">
    <h1 class="display-6">Система «Кава-машина»</h1>
    <p class="lead">
      Користувач має рахунок і може купити один або кілька напоїв. Кава-машина
      містить напої із заданою кількістю порцій та додаткові інгредієнти.
      Адміністратор наповнює машину.
    </p>
    <router-link class="btn btn-primary" to="/drinks">Перейти до напоїв</router-link>
  </div>

  <div class="row row-cols-1 row-cols-md-3 g-3">
    <div class="col">
      <div class="card h-100">
        <div class="card-body">
          <h5 class="card-title">Напої</h5>
          <p class="card-text">Перегляд напоїв та покупка.</p>
          <router-link to="/drinks">Відкрити</router-link>
        </div>
      </div>
    </div>
    <div class="col">
      <div class="card h-100">
        <div class="card-body">
          <h5 class="card-title">Мій рахунок</h5>
          <p class="card-text">Вибір користувача та поповнення рахунку.</p>
          <router-link to="/account">Відкрити</router-link>
        </div>
      </div>
    </div>
    <div class="col">
      <div class="card h-100">
        <div class="card-body">
          <h5 class="card-title">Адміністратор</h5>
          <p class="card-text">Наповнення машини напоями та інгредієнтами.</p>
          <router-link to="/admin">Відкрити</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView'
}
</script>