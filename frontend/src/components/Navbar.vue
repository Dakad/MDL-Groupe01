<template>
  <header class="navappregister">
    <div>
      <!-- Create the login dialog -->
      <md-dialog class="login-dialog" :md-active.sync="showLoginDialog">
        <md-dialog-title>Login</md-dialog-title>
        <login @close="showLoginDialog= false"/>
      </md-dialog>

      <!--Create the register dialog-->
      <md-dialog class="signin-dialog" :md-active.sync="showRegisterDialog">
        <md-dialog-title>Create Account</md-dialog-title>
        <register @close="showRegisterDialog = false"/>
      </md-dialog>

      <!--The main navigation bar-->
      <md-toolbar class="md=accent" md-elevation="1">
        <!--SiteName refer to AccueilVue-->
        <a class="md-title" style="flex: 1" href="/">SiteName</a>
        <!--Register button refer to the register page (RegisterVue)-->
        <md-button class="md-primary md-raised" @click="showRegisterDialog = true">Sign in</md-button>
        <!--Login button open the login dialog-->
        <md-button class="md-primary md-raised" @click="showLoginDialog = true">Sign up</md-button>
      </md-toolbar>
    </div>
  </header>
</template>


<style lang="scss" scoped>
.signin-dialog {
  width: 55%;
}
// .md-dialog {
//   widows: 100%;
// }
</style>


<script>
import Login from "./navbar/Login.vue";
import Register from "./navbar/Register.vue";

export default {
  name: "Navbar",
  components: { Login, Register },
  data: function() {
    return {
      showLoginDialog: false,
      showRegisterDialog: false
    };
  }
};
</script>