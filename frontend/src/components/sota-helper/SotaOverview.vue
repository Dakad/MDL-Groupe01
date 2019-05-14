<template>
  <div class="md-layout md-gutter">
    <div class="md-layout-item md-size-75">
      <h4>Current articles in the SotA</h4>
      <!-- <ul id="articlesShow">
        <li v-for="article in sortedArticlesList" :key="article">
          <md-card md-with-hover>
            <md-card-header>
              <div class="md-title">{{article.title}}</div>
            </md-card-header>

            <md-card-content>
              <p>Author : {{article.user}}</p>
              <p>Published on the {{article.publicationDate}}</p>
            </md-card-content>
          </md-card>
        </li>
      </ul>-->

      <article-list :list="sortedArticlesList"></article-list>
    </div>
    <div class="md-layout-item">
      <!-- TODO Replace by The SortOption Component used on the Result page -->
      <sort-options
        class="sort-container"
        :sort="sortBy"
        :order="orderBy"
        active="articles"
        @change:sort="sortBy = $event"
        @change:order="orderBy = $event"
      >
        <md-button class="md-raised md-primary">Export all refs in bibTex</md-button>
      </sort-options>
    </div>
  </div>
</template>


<script>
import ArticleList from "@/components/resultat/ArticleList";
import SortOptions from "@/components/resultat/SortOptions";

export default {
  name: "SotaOverview",
  components: {
    ArticleList,
    SortOptions
  },
  props: {
    list: Array
  },
  data() {
    return {
      // data: {},
      articles: [],
      sortBy: "title",
      orderBy: "asc"
    };
  },
  computed: {
    sortedArticlesList() {
      const fnSortArticles = (a1, a2) => {
        if (!Object.keys(a1).includes(this.sortBy)) {
          return 0;
        }
        if (this.orderBy.localeCompare("asc")) {
          return a1[this.orderBy].localeCompare(a2[this.orderBy]);
        } else {
          return a2[this.orderBy].localeCompare(a1[this.orderBy]);
        }
      };
      return this.list.sort(fnSortArticles);
    }
  }
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
