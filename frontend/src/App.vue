<template>
  <div id="app">
    <header class="navbar">
      <!-- TODO mettre la barre supérieure avec login,... mais sans la barre de recherche -->
      <navbar/>
    </header>

    <router-view></router-view>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";

export default {
  name: "App",
  components: {
    NavBar
  }
};
</script>
