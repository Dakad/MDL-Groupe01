<template>
  <div id="app">
    <!-- TODO mettre la barre supérieure avec login,... mais sans la barre de recherche-->
    <navbar id="app-navbar"/>

    <router-view id="app-content"></router-view>
    <md-snackbar
      class="md-layout md-alignment-center-center"
      md-position="center"
      :md-duration="flashMsg.duration"
      :md-active.sync="flashMsg.show"
      md-persistent
    >
      <span class="md-layout-item md-subheading">{{flashMsg.msg}}</span>
      <md-button class="md-icon-button md-accent" :md-ripple="false" @click="flashMsg.show = false">
        <md-icon>clear</md-icon>
      </md-button>
    </md-snackbar>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import { ping as sendPing } from "@/services/api";

import {
  EventBus,
  EVENT_USER_LOGGED,
  EVENT_USER_LOGOUT,
  EVENT_USER_SIGNIN,
  EVENT_BYE_REDIRECTION,
  EVENT_APP_MESSAGE
} from "@/services/event-bus.js";

const DEFAULT_FLASH_MSG_TIME = 5000;

export default {
  name: "App",
  components: {
    Navbar
  },
  data() {
    return {
      flashMsg: {
        show: false,
        type: null,
        msg: null,
        duration: DEFAULT_FLASH_MSG_TIME
      }
    };
  },
  created() {
    // Check if the API respond
    sendPing().catch(err => {
      EventBus.$emit(EVENT_APP_MESSAGE, {
        msg: "API Error - API doesn't respond"
      });
    });

    // Handle the display of flash msg on the snackbar
    EventBus.$on(EVENT_APP_MESSAGE, payload => {
      if (typeof payload == "string") {
        this.flashMsg.msg = payload;
      } else {
        this.flashMsg.type = payload["type"];
        this.flashMsg.msg = payload["message"] || payload["msg"];
      }
      this.flashMsg.duration = payload["duration"] || DEFAULT_FLASH_MSG_TIME;
      this.flashMsg.show = this.flashMsg.msg != undefined;
    });

    // Handle the user logged event
    EventBus.$on(EVENT_USER_LOGGED, ({ username }) => {
      if (!username) return;

      const msgs = ["Nice to see you", "Welcome back"];
      const choice = Math.floor(Math.random() * msgs.length);

      EventBus.$emit(
        EVENT_APP_MESSAGE,
        `Hello ${username} ! ${msgs[choice]} :-D!!`
      );

      if (this.$route.query["redirect"]) {
        this.$router.replace(this.$route.query["redirect"]);
      }
    });

    // Handle the user signin event
    EventBus.$on(EVENT_USER_SIGNIN, ({ username }) => {
      EventBus.$emit(
        EVENT_APP_MESSAGE,
        "Welcome on board !! Please login with your credentials !"
      );
    });

    // Handle the user signin event
    EventBus.$on(EVENT_USER_LOGOUT, _ =>
      EventBus.$emit(EVENT_APP_MESSAGE, "Bye, see you !")
    );

    // Handle the user logout event
    EventBus.$on(EVENT_BYE_REDIRECTION, _ => {
      EventBus.$emit(
        EVENT_APP_MESSAGE,
        "You have been logged out, please log back in !"
      );
    });
  },

  beforeDestroy() {
    EventBus.$off(EVENT_BYE_REDIRECTION);
    EventBus.$ff(EVENT_USER_LOGOUT);
    EventBus.$off(EVENT_USER_SIGNIN);
    EventBus.$off(EVENT_USER_LOGGED);
    EventBus.$on(EVENT_APP_MESSAGE);
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
</style>

