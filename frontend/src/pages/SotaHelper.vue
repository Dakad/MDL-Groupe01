<template>
  <div class="container">
    <div class="title">
      <h2>State of the Art Helper</h2>
    </div>

    <div class="tabs">
      <md-tabs md-alignment="fixed" md-active-tab="visu">
        <md-tab id="gestion" md-label="Gestion" md-icon="view_module">
          <SotaGestion/>
        </md-tab>
        <md-tab id="visu" md-label="Visualisation" md-icon="share">
          <sota-graphic/>
        </md-tab>
        <md-tab id="recommanded" md-label="Recommanded" md-icon="thumb_up">
          <article-list v-show="!loading" :list="results.articles"></article-list>
          <md-empty-state
            v-if="!results.articles || results.articles.length == 0"
            md-icon="description"
            md-label="No articles found"
            md-description="Creating project, you'll be able to upload your design and collaborate with people."
          ></md-empty-state>
        </md-tab>
        <md-tab id="uploadOne" md-label="Upload new SotA" md-icon="plus_one">
          <create-sota></create-sota>
        </md-tab>
      </md-tabs>
    </div>
  </div>
</template>

<script>
import SotaGestion from "../components/sota-helper/SotaGestion";
import SotaCreate from "../components/sota-helper/SotaCreate";
import SotaGraphic from "@/components/sota-helper/SotaGraphic";
import articleList from "@/components/resulat/ArticleList";

export default {
  name: "SotaHelper",
  components: { createSota: SotaCreate, SotaGraphic, SotaGestion, articleList, },
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
};
</script>

<style scoped>
.container {
  /* position: absolute;
  width: 100%;
  height: 86%;
  max-width: 1900px;
  margin-top: 1%; */
}

.title {
  position: relative;
  width: 100%;
  height: 10%;
}
h2 {
  text-align: center;
}

.tabs {
  width: 99%;
  height: 85%;
  margin-top: 3%;
}

.recommend {
  width: 75%;
}

#uploadOne {
  width: 100%;
  height: 550px;
}
</style>
