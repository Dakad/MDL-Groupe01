<template>
  <section class="results">
    <div class="first">
      <h2>Sort by :</h2>
      <md-radio v-model="sortBy" value="name" @change="updateSearchURL('sort', $event)">Name</md-radio>
      <md-radio v-model="sortBy" value="title" @change="updateSearchURL('sort', $event)">Title</md-radio>
      <md-radio v-model="sortBy" value="date" @change="updateSearchURL('sort', $event)">Date</md-radio>

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
      <md-radio v-model="orderBy" value="asc" @change="updateSearchURL('order', $event)">Ascending</md-radio>
      <md-radio v-model="orderBy" value="desc" @change="updateSearchURL('order', $event)">Descending</md-radio>
      <small>{{ sortBy }} + {{ orderBy }}</small>
    </div>

    <div class="tabs">
      <div class="loading-search-results" v-if="loading">
        <md-progress-bar md-mode="indeterminate"/>
      </div>
      <md-tabs md-alignment="fixed" :md-active-tab="activeTab" @md-changed="activeTab = $event">
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
            md-icon="people"
            md-label="No states of the art found"
            md-description="Creating project, you'll be able to upload your design and collaborate with people."
          ></md-empty-state>
        </md-tab>
        <md-tab id="graphics" md-label="Graphics" md-icon="share" v-if="articlesTitles.length != 0">
          <md-empty-state
            v-if="articlesTitles.length == 0"
            md-icon="share"
            md-label="No graphics to display"
            md-description="Try another search"
          ></md-empty-state>
          <graphics v-else :articles-titles="articlesTitles" :linked-articles="relatedArticles"/>
        </md-tab>
        <md-tab id="wordcloud" md-label="WordCloud" md-icon="cloud">
          <md-empty-state
            v-if="articlesTitles.length == 0"
            md-icon="cloud"
            md-label="No word cloud to display"
            md-description="Creating project, you'll be able to upload your design and collaborate with people."
          ></md-empty-state>
          <word-cloud v-else :tags="articlesTags"></word-cloud>
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
import WordCloud from "@/components/resulat/WordCloud";

import { getSearchResults } from "@/services/api";
import { debug } from "util";

export default {
  name: "Resultat",
  components: {
    sotaList,
    authorList,
    articleList,
    graphics,
    WordCloud
  },
  data() {
    return {
      loading: false,
      searchTerm: null,
      sortBy: this.$route.query["sort"] || "name",
      orderBy: this.$route.query["order"] || "asc",
      activeTab: "articles",
      page: 0,
      results: {},
      articlesTags: {},
      articlesTitles: [],
      relatedArticles: []
    };
  },
  created() {
    // fetch the data when the view is created
    // and the data is already being observed
    this.fetchSearchResult();
  },
  watch: {
    // call it again the method if the route changes
    $route: "fetchSearchResult",
    sortBy: by => updateSearchURL("sort", by),
    orderBy: by => updateSearchURL("order", by)
  },
  methods: {
    updateSearchURL(type, by) {
      const query = { ...this.$route.query };
      query[type] = by;
      this.$router.push({ query });
    },
    fetchSearchResult() {
      this.loading = true;
      this.searchTerm = this.$route.query["search"];

      const searchQuery = {
        st: this.searchTerm,
        s: this.sortBy,
        o: this.orderBy,
        p: this.page
      };

      return getSearchResults(searchQuery)
        .then(res => {
          this.loading = false;

          this.$set(this.results, "articles", res["articles"]);
          this.$set(this.results, "authors", res["authors"]);
          this.$set(this.results, "sotas", res["sotas"]);
          this.$set(this.results, "users", res["users"]);

          this.articlesTitles = [];

          this.articlesTags = res["articles"].reduce((acc, article) => {
            // As the same time, push the article title
            this.articlesTitles.push(article.title);
            article.keywords.forEach(({ name, slug }) => {
              const nb = acc[slug] ? acc[slug]["occur"] : 0;
              acc[slug] = { name, occur: nb + 1 };
            });
            return acc;
          }, {});

          this.groupArticleByKeywords();
        })
        .catch(console.error);
    },
    /**
     * Group articles based on common keywords among them.
     */
    groupArticleByKeywords() {
      this.relatedArticles = [];
      const { articles } = this.results;
      for (let i = 0; i < articles.length; i++) {
        let keywords = articles[i].keywords;
        for (let j = i + 1; j < articles.length; j++) {
          let commonKeyword = "";
          let commonArticle = [];
          let alreadyIn = false;
          for (let k = 0; k < keywords.length; k++) {
            let keywordName = keywords[k].name;
            for (let l = 0; l < articles[j].keywords.length; l++) {
              if (keywordName === articles[j].keywords[l].name) {
                commonKeyword += keywordName;
                if (alreadyIn === false) {
                  alreadyIn = true;
                  commonArticle.push(articles[i].title);
                  commonArticle.push(articles[j].title);
                }
              }
            }
          }
          commonArticle.push(commonKeyword);
          if (commonArticle.length > 1) {
            this.relatedArticles.push(commonArticle);
          }
        }
      }
    },
    groupyTags() {},
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
.loading-search-results > md-progress-bar {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
}

.tabs {
  position: absolute;
  top: 15%;
  left: 25%;
  width: 75%;
}

.first {
  position: relative;
  top: 15%;
  left: 5%;
  width: 20%;
}

.second {
  position: relative;
  top: 60%;
  left: 5%;
  width: 20%;
}
.md-radio {
  display: flex;
}
</style>

