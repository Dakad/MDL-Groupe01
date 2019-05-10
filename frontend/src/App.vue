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

<style lang="css">
#app {
  margin-bottom: 10px;
}
#app-navbar {
  position: sticky;
  margin-bottom: 10px;
  width: 100%;
  z-index: 10;
  top: 0;
}

#app-content {
  width: 100%;
}

md-tabs {
  width: auto;
  height: auto;
}

/* body {
  min-height: 150%;
} */
</style>

