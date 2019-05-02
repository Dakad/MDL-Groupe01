<template>
  <div class="container">
    <div class="title"><h2>State of the Art Helper</h2></div>

    <div class="tabs">
      <md-tabs md-alignment="fixed" :md-active-tab="activeTab" @md-changed="activeTab = $event">
        <md-tab id="gestion" md-label="Gestion" md-icon="view_module">

        </md-tab>
        <md-tab id="visualisation" md-label="Visualisation" md-icon="view_module">

        </md-tab>
        <md-tab id="recommended" md-label="Recommended" md-icon="view_module">
          <article-list v-show="!loading" :list="results.articles"></article-list>
          <md-empty-state
            v-if="!results.articles || results.articles.length == 0"
            md-icon="description"
            md-label="No articles found"
            md-description="Creating project, you'll be able to upload your design and collaborate with people."
          ></md-empty-state>
        </md-tab>
        <md-tab id="uploadOne" md-label="Upload new SotA" md-icon="view_module">

        </md-tab>
      </md-tabs>
    </div>
  </div>
</template>

<script>

import dummy from "@/assets/dummy-Results.json";

import articleList from "@/components/resulat/ArticleList";
  
export default {
  name: "SotAHelper",
  components: {
    articleList,
  },
  data() {
    return {
      list: dummy.results.articles,
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
}
</script>

<style scoped>
  .container{
    position: absolute;
    width: 100%;
    height: 86%;
    max-width: 1900px;
    margin-top: 1%;
  }

  .title{
    position: relative;
    width: 100%;
    height: 10%;
  }
  h2{
    text-align: center;
  }

  .tabs{
    position: relative;
    width: 100%;
    height: 80%;
    margin-top: 3%;
  }

.recommend {
  width: 75%;
}

.slides {
  border: 1px solid gray;
  margin: 1%;
  padding: 1%;
}
</style>
