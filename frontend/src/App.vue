<template>
  <div id="app">
    <!-- TODO mettre la barre supérieure avec login,... mais sans la barre de recherche-->
    <navbar id="app-navbar"/>

    <router-view id="app-content"></router-view>
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

<style lang="scss">
#app {
  margin-bottom: 10px;

  #app-navbar {
    margin-bottom: 15px;
  }

  #app-content {
    width: 100%;
  }
}
</style>

