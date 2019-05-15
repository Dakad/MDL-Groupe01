<template>
  <div class="cont">
    <h4>Current articles in the SotA</h4>
    <Bookmarks :bookmarked="bookmarked"/>
  </div>
</template>


<script>
import Bookmarks from "@/components/profil/Bookmarks";

export default {
  name: "SotaOverview",
  components: {
    Bookmarks
  },
  props: ["bookmarked"],
  data() {
    return {
      articles: []
    };
  }
};
</script>

<style scoped>
/* .sort-options {
  position: fixed;
  z-index: 75;
} */

.md-button {
  position: relative;
  display: flex;
  margin: 1%;
}
</style>
