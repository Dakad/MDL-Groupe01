<template>
  <header class="navappregister">
    <div>
      <!--The main navigation bar-->
      <md-toolbar class="md=accent" md-elevation="1">
        <router-link
          to="/"
          :class="[!searchBar.show ? 'flex': '', 'md-title','app-name']"
        >Froggosaur</router-link>
        <search
          v-show="searchBar.show"
          :mode="searchBar.mode"
          :term="searchBar.input"
          id="search"
          class="flex"
        ></search>

        <!-- <div class="search">
          <form class="form-inline my-2 my-lg-0 ml-auto">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
            <b-button size="lg" variant="outline-success" type="submit">Search</b-button>
          </form>
        </div>-->
        <div class="buttons" style="float: right">
          <div v-if="isAuthenticated">
            <md-button class="md-icon-button md-dense md-primary" @click="logout()">
              <md-icon>person</md-icon>
            </md-button>
          </div>
          <div v-else>
            <!--Login button open the login dialog-->
            <b-button size="lg" variant="outline-info" @click="showLoginDialog = true">LOGIN</b-button>&nbsp; &nbsp;
            <!--Register button refer to the register page (RegisterVue)-->
            <b-button size="lg" variant="outline-primary" @click="showRegisterDialog = true">SIGN IN</b-button>
          </div>
        </div>
      </md-toolbar>
      <!-- Create the login dialog -->
      <md-dialog class="login-dialog" :md-active.sync="showLoginDialog">
        <md-dialog-title>
          <md-icon :class="['md-size-2x', {'text-danger':loginFailed}]">security</md-icon>&nbsp; Login
        </md-dialog-title>
        <login @error="handleError('login', $event)" @success="handleSuccess('login',$event)"/>
      </md-dialog>

      <!--Create the register dialog-->
      <md-dialog class="signin-dialog" :md-active.sync="showRegisterDialog">
        <md-dialog-title>
          <md-icon :class="[ 'md-size-2x',{'text-danger':signinFailed }]">account_box</md-icon>&nbsp;
          <span>Create account</span>
        </md-dialog-title>
        <register
          @error="handleError('register', $event)"
          @success="handleSuccess('signin',$event)"
        />
      </md-dialog>
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
.app-name {
  text-decoration: none !important;
}
.flex {
  flex: 1;
}
.search {
  margin: 0 40px;
}
</style>


<script>
import Login from "./navbar/Login.vue";
import Register from "./navbar/Register.vue";
import { ping as sendPing } from "@/services/api";
import Search, { MODE_NAVBAR } from "@/components/navbar/Search";
import { isLogged, logout } from "@/services/api-user";

export default {
  name: "Navbar",
  components: { Login, Register, Search },
  data: function() {
    return {
      searchBar: {
        mode: MODE_NAVBAR,
        show: true,
        input: this.$route.query["search"]
      },
      showLoginDialog: false,
      showRegisterDialog: false,
      isAuthenticated: false,
      loginFailed: false,
      signinFailed: false,
      showSnackbar: false,
      snackbarMsg: null,
      snackbarTime: 5000
    };
  },
  watch: {
    "$route.name": function(route) {
      this.searchBar.show = route != "accueil";
      this.searchBar.input = this.$route.query["search"];
    },
    "$route.query": function(route) {}
  },
  created() {},

  mounted() {
    this.searchBar.show = this.$route.name != "accueil";
    switch (this.$route.query["action"]) {
      case "login":
        this.showLoginDialog = true;
        break;

      default:
        break;
    }

    sendPing().catch(err => {
      this.snackbarMsg = "API Error - API doesn't respond";
      this.showSnackbar = true;
    });
  },

  methods: {
    handleError(component, error) {
      switch (component) {
        case "login":
          this.loginFailed = true;
          break;
        case "register":
        case "signin":
          this.signinFailed = true;
        default:
          break;
      }
      this.showSnackbar = true;
      this.snackbarMsg = error;
    },

    handleSuccess(component, msg) {
      switch (component) {
        case "login":
          this.showLoginDialog = false;
          msg = `Hello ${msg} ! Welcome BACK :-D !!`;
          this.isAuthenticated = true;
          if (this.$route.query["redirect"]) {
            this.$router.replace(this.$route.query["redirect"]);
          }
          break;
        case "register":
        case "signin":
          this.showRegisterDialog = false;
          if (!msg) {
            msg = "Welcome on board !! Please login with your credentials !";
          }
        default:
          break;
      }
      this.showSnackbar = true;
      this.snackbarMsg = msg;
    },
    logout() {
      logout();
      this.isAuthenticated = false;
      this.snackbarMsg = "Bye, see you !";
      this.showSnackbar = true;
    }
  }
};
</script>
