<template>
  <div id="app">
    <!-- TODO mettre la barre supérieure avec login,... mais sans la barre de recherche-->
    <navbar/>

    <router-view></router-view>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";

export default {
  name: "App",
  components: {
    Navbar
  }
};
</script>
