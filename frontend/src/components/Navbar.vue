<template>
  <header class="navappregister">
    <!--The main navigation bar-->
    <md-toolbar class="md=accent" md-elevation="1">
      <router-link to="/" :class="[!searchBar.show ? 'flex': '', 'md-title','app-name']">Froggosaur</router-link>
      <search
        v-show="searchBar.show"
        :mode="searchBar.mode"
        :term="searchBar.input"
        :min-length="2"
        @error="handleError('search', $event)"
        id="search"
        class="flex"
      ></search>
      <div class="buttons">
        <div v-if="isAuthenticated">
          <md-menu md-align-trigger v-if="avatar != null">
            <md-button class="md-icon-button" md-menu-trigger>
              <md-avatar>
                <img :src="avatar" alt="Avatar">
              </md-avatar>
            </md-button>

            <md-menu-content>
              <md-menu-item @click="$router.push({ name : 'myProfile' })">
                <md-icon>perm_identity</md-icon>
                <span>Profile</span>
              </md-menu-item>

              <md-menu-item @click="$router.push({ name : 'sotaHelper' })">
                <md-icon>view_module</md-icon>
                <span>SoTA Helper</span>
              </md-menu-item>

              <md-menu-item @click="$router.push({ name : 'updateProfile' })">
                <md-icon>info</md-icon>
                <span>Change my information's</span>
              </md-menu-item>

              <md-divider></md-divider>

              <md-menu-item @click="logout()">
                <md-icon>exit_to_app</md-icon>
                <span>Logout</span>
              </md-menu-item>
            </md-menu-content>
          </md-menu>
        </div>
        <div v-else>
          <!--Login button open the login dialog-->
          <b-button
            size="lg"
            variant="outline-info"
            @click="showLoginDialog = true; showRegisterDialog = false"
          >LOGIN</b-button>&nbsp; &nbsp;
          <!--Register button refer to the register page (RegisterVue)-->
          <b-button
            size="lg"
            variant="outline-primary"
            @click="showRegisterDialog = true; showLoginDialog = false;"
          >SIGN IN</b-button>
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
      <register @error="handleError('register', $event)" @success="handleSuccess('signin',$event)"/>
    </md-dialog>
  </header>
</template>

<script>
import Login from "./navbar/Login.vue";
import Register from "./navbar/Register.vue";
import Search, { MODE_NAVBAR } from "@/components/navbar/Search";
import { isLogged, logout, getProfileBase } from "@/services/api-user";
import {
  EventBus,
  EVENT_USER_LOGGED,
  EVENT_USER_LOGOUT,
  EVENT_USER_SIGNIN,
  EVENT_APP_MESSAGE,
  EVENT_BYE_REDIRECTION
} from "@/services/event-bus.js";

export default {
  name: "Navbar",
  components: { Login, Register, Search },
  data: function() {
    return {
      searchBar: {
        mode: MODE_NAVBAR,
        show: true,
        input: this.$route.query["search"] || null
      },
      showLoginDialog: false,
      showRegisterDialog: false,
      isAuthenticated: isLogged(),
      loginFailed: false,
      signinFailed: false,
      avatar: null
    };
  },
  watch: {
    "$route.name": function(route) {
      this.searchBar.show = route != "accueil";
      this.searchBar.input = this.$route.query["search"];
    },
    "$route.query": function(route) {}
  },
  created() {
    if (this.isAuthenticated) {
      this.getProfile();
    }
    EventBus.$emit(EVENT_USER_LOGGED, this.isAuthenticated);

    EventBus.$on(EVENT_USER_LOGGED, this.getProfile);

    // Disable the search in in the navbar on page 'accueil'
    this.searchBar.show = this.$route.name != "accueil";
    switch (this.$route.query["action"]) {
      case "login":
        this.showLoginDialog = true;
        break;

      default:
        break;
    }
  },
  mounted() {
    // Send a event to say this user is logged or not
  },

  methods: {
    handleError(component, error) {
      switch (component) {
        case "login":
          this.loginFailed = true;
          EventBus.$emit(EVENT_APP_MESSAGE, error);
          break;
        case "register":
        case "signin":
          this.signinFailed = true;
          EventBus.$emit(EVENT_APP_MESSAGE, error);

        default:
          break;
      }
    },
    handleSuccess(component, msg) {
      switch (component) {
        case "login":
          this.showLoginDialog = false;
          this.isAuthenticated = true;
          this.loginFailed = false;
          EventBus.$emit(EVENT_USER_LOGGED, { username: msg });
          break;
        case "register":
        case "signin":
          this.showRegisterDialog = false;
          this.loginFailed = false;
          EventBus.$emit(EVENT_USER_SIGNIN, true);
          break;
        default:
          break;
      }
    },
    logout() {
      logout();
      this.isAuthenticated = false;
      EventBus.$emit(EVENT_USER_LOGOUT, true);
    },
    getProfile() {
      getProfileBase().then(profile => {
        this.avatar = profile["avatar_url"];
      });
    }
  }
};
</script>

<style lang="css" scoped>
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
