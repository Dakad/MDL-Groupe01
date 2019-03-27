<template>
  <header class="navappregister">
    <div>
      <!-- Create the login dialog -->
      <md-dialog class="login-dialog" :md-active.sync="showLoginDialog">
        <md-dialog-title>
          <md-icon :class="['md-size-2x', {'text-danger':loginFailed}]">security</md-icon>&nbsp; Login
        </md-dialog-title>
        <login @error="handleLoginError($event)" @close="showLoginDialog=false"/>
      </md-dialog>

      <!--Create the register dialog-->
      <md-dialog class="signin-dialog" :md-active.sync="showRegisterDialog">
        <md-dialog-title>
          <md-icon :class="[ 'md-size-2x',{'text-danger':signinFailed }]">account_box</md-icon>&nbsp;
          <span>Create account</span>
        </md-dialog-title>
        <register @close="showRegisterDialog = false"/>
      </md-dialog>

      <!--The main navigation bar-->
      <md-toolbar class="md=accent" md-elevation="1">
        <!--SiteName refer to AccueilVue-->
        <a class="md-title" style="flex: 1" href="/">SiteName</a>
        <!--Login button open the login dialog-->
        <b-button size="lg" variant="outline-info" @click="showLoginDialog = true">LOGIN</b-button>&nbsp; &nbsp;
        <!--Register button refer to the register page (RegisterVue)-->
        <b-button size="lg" variant="outline-primary" @click="showRegisterDialog = true">SIGN IN</b-button>
      </md-toolbar>
    </div>
    <md-snackbar
      md-position="center"
      :md-duration="snackbarTime"
      :md-active.sync="showSnackbar"
      md-persistent
    >
      <span>{{snackbarMsg}}</span>
      <!-- <md-button class="md-primary" @click="showSnackbar = false">Retry</md-button> -->
    </md-snackbar>
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
import { postLogin, postSignin } from "@/services/api-user";

export default {
  name: "Navbar",
  components: { Login, Register },
  data: function() {
    return {
      showLoginDialog: false,
      showRegisterDialog: false,
      loginFailed: false,
      signinFailed: false,
      showSnackbar: false,
      snackbarMsg: null,
      snackbarTime: 5000
    };
  },
  methods: {
    handleLoginError(error) {
      this.loginFailed = true;
      this.showSnackbar = true;
      this.snackbarMsg = error;
    },
    handleSigninError(error) {
      this.loginFailed = true;
    }
  }
};
</script>
