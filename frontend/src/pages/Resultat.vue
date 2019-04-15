<template>
  <section class="results">
    <div class="first">
      <h2>Sort by :</h2>
      <md-radio v-model="sortBy" value="name">Name</md-radio>
      <md-radio v-model="sortBy" value="title">Title</md-radio>
      <md-radio v-model="sortBy" value="date">Date</md-radio>

      <!--
      <md-radio v-model="sortBy" value="domain">Domain of Research</md-radio>
      <md-radio v-model="sortBy" value="date">Date</md-radio>
      <md-radio v-model="sortBy" value="views">Views</md-radio>
      <md-radio v-model="sortBy" value="ref">References</md-radio>
      -->
    </div>
    <hr>
    <div class="second">
      <h2>Order by :</h2>
      <md-radio v-model="orderBy" value="asc">Ascending</md-radio>
      <md-radio v-model="orderBy" value="desc">Descending</md-radio>
      <small>{{ sortBy }} + {{ orderBy }}</small>
    </div>

    <div class="tabs">
      <md-tabs md-alignment="fixed" md-active-tab="articles">
        <md-tab id="sotas" md-label="States Of The Art" md-icon="view_module">
          <sota-list v-show="!loading" :list="results.sotas"></sota-list>
          <md-empty-state
            v-if="!results.sotas || results.sotas.length == 0"
            md-icon="view_module"
            md-label="No states of the art found"
            md-description="Creating project, you'll be able to upload your design and collaborate with people."
          ></md-empty-state>
        </md-tab>
        <md-tab id="articles" md-label="Articles" md-icon="description">
          <article-list v-show="!loading" :list="results.articles"></article-list>
          <md-empty-state
            v-if="!results.articles || results.articles.length == 0"
            md-icon="description"
            md-label="No articles found"
            md-description="Creating project, you'll be able to upload your design and collaborate with people."
          ></md-empty-state>
        </md-tab>
        <md-tab id="authors" md-label="Authors/Users" md-icon="people">
          <author-list v-show="!loading" :list="results.authors"></author-list>
          <md-empty-state
            v-if="!results.authors || results.authors.length == 0"
            md-icon="view_module"
            md-label="No states of the art found"
            md-description="Creating project, you'll be able to upload your design and collaborate with people."
          ></md-empty-state>
        </md-tab>
        <md-tab id="graphics" md-label="Graphics" md-icon="timeline">
          <graphics v-show="!loading"/>
          <md-empty-state
            v-if="!results || results.length == 0"
            md-icon="view_module"
            md-label="No graphics to display"
            md-description="Creating project, you'll be able to upload your design and collaborate with people."
          ></md-empty-state>
        </md-tab>
      </md-tabs>
    </div>
  </section>
</template>

<script>
import sotaList from "@/components/resulat/SotaList";
import authorList from "@/components/resulat/AuthorList";
import articleList from "@/components/resulat/ArticleList";
import graphics from "@/components/resulat/Graphics";

import { getSearchResults } from "@/services/api";

export default {
  components: {
    sotaList,
    authorList,
    articleList,
    graphics
  },
  data() {
    return {
      loading: false,
      searchTerm: null,
      sortBy: "name",
      orderBy: "asc",
      page: 0,
      results: {}
    };
  },
  created() {
    // fetch the data when the view is created
    // and the data is already being observed
    this.fetchSearchResult();
  },
  watch: {
    // call it again the method if the route changes
    $route: "fetchSearchResult"
  },
  methods: {
    fetchSearchResult() {
      this.loading = true;
      this.searchTerm = this.$route.query["search"];

      const searchQuery = {
        st: this.searchTerm,
        s: this.sortBy,
        o: this.orderBy,
        p: this.page
      };

      setTimeout(() => {
        return getSearchResults(searchQuery)
          .then(res => {
            this.loading = false;
            this.$set(this.results, "articles", res["articles"]);
            this.$set(this.results, "authors", res["authors"]);
            this.$set(this.results, "sotas", res["sotas"]);
            this.$set(this.results, "users", res["users"]);
            // this.$set(this.$data, "results", res);
            // Object.keys(res).forEach(type => {
            //   this.$set(this.results, type, res[type]);
            // });
          })
          .catch(console.error);
      }, 3 * 1000);
    },
    getEmptyStateLabel(type) {},
    getEmptyStateDescription(type) {
      switch (type) {
        case "value":
          break;

        default:
          break;
      }
    }
  }
};
</script>

<style scoped>
.tabs {
  position: absolute;
  top: 15%;
  left: 25%;
}

.first {
  position: relative;
  top: 15%;
  left: 5%;
}

.second {
  position: relative;
  top: 60%;
  left: 5%;
}
.md-radio {
  display: flex;
}
</style>

