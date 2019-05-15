<template>
  <div class="cont" v-if="!(this.username==null)">
    <md-button
      class="md-raised md-accent"
      :md-ripple="false"
      v-if="isFollowed"
      @click="follow"
    >Stop following</md-button>
    <md-button
      class="md-raised md-primary"
      :md-ripple="false"
      v-if="!isFollowed"
      @click="follow"
    >Follow</md-button>
  </div>
</template>

<script>
import { postFollow, postUnFollow, getFollow } from "@/services/api-user";
import { EventBus, EVENT_APP_MESSAGE } from "@/services/event-bus.js";

export default {
  data() {
    return {
      isFollowed: false,
      username: null
    };
  },

  methods: {
    fetchFollowed() {
      getFollow(this.username).then(data => {
        this.isFollowed = data["done"] || false;
      });
    },

    follow() {
      if (!this.isFollowed) {
        postFollow(this.username).then(x => {
          this.isFollowed = true;
          EventBus.$emit(EVENT_APP_MESSAGE, {
            type: "info",
            msg: "Your are now following " + this.username
          });
        });
      } else if (this.isFollowed) {
        postUnFollow(this.username).then(x => {
          console.log(x);
          this.isFollowed = false;
          EventBus.$emit(EVENT_APP_MESSAGE, {
            type: "info",
            msg: "Your are not following " + this.username + " anymore"
          });
        });
      }
    }
  },

  created() {
    this.username = this.$route.params["username"];

    this.fetchFollowed();
  }
};
</script>

<style scoped>
</style>
