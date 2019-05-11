<template>
  <div class="menuArticle">
    <md-card-actions>
      <md-button class="md-icon-button" :title="bookmarkBtnTitle" @click="bookmarkMe">
        <md-icon>{{isBookmarked ? "bookmark" : "bookmark_border"}}</md-icon>
      </md-button>

      <md-button class="md-icon-button" title="Download" @click="downloadRef">
        <md-icon>get_app</md-icon>
      </md-button>
    </md-card-actions>
  </div>
</template>

<script>
import {
  sotaDeleteBookmark,
  sotaGetBookmark,
  sotaPostBookmark
} from "@/services/api-sota";

export default {
  name: "SotaMenu",
  props: ["isBookmarked"],
  data() {
    return {};
  },
  computed: {
    bookmarkBtnTitle() {
      return !this.isBookmarked ? "Bookmark it" : "Remove from bookmarks";
    }
  },

  created() {},

  methods: {
    downloadRef() {
      this.$emit("download");
    },

    bookmarkMe() {
      this.$emit("bookmark");
    }
  }
};
</script>

<style scoped>
</style>
