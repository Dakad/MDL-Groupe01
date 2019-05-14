<template>
  <div class="md-layout md-gutter">
      <h4>Current articles in the SotA</h4>
      <Bookmarks :bookmarked="bookmarked"/>
  </div>
</template>


<script>
import ArticleList from "@/components/resultat/ArticleList";
import SortOptions from "@/components/resultat/SortOptions";
import Bookmarks from "@/components/profil/Bookmarks"

export default {
  name: "SotaOverview",
  components: {
    ArticleList,
    SortOptions,
    Bookmarks
  },
  props:["bookmarked"],
  data() {
    return {
      // data: {},
      articles: [],
    };
  },
};
</script>

<style scoped>
.sort-options {
  position: fixed;
  z-index: 75;
}

.md-button {
  position: relative;
  display: flex;
  margin: 1%;
}
</style>
