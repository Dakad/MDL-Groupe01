<template>
  <div class="container">
    <div class="title">
      <h2>State of the Art Helper</h2>
    </div>

    <div class="tabs">
      <md-tabs md-alignment="fixed" md-active-tab="visu">
        <md-tab id="gestion" md-label="Gestion" md-icon="view_module">
          <!-- <SotaGestion @selected="selectedArticles = $event"/> -->
        </md-tab>
        <md-tab id="recommanded" md-label="Recommanded" md-icon="thumb_up">
          <article-list v-show="!loading" :list="articles"></article-list>
          <md-empty-state
            v-if="!articles || articles.length == 0"
            md-icon="description"
            md-label="No articles found"
            md-description="Creating project, you'll be able to upload your design and collaborate with people."
          ></md-empty-state>
        </md-tab>
        <md-tab id="visu" md-label="Visualisation" md-icon="share">
          <sota-graphic :articles="selectedArticles"/>
        </md-tab>
        <md-tab id="uploadOne" md-label="Create a new SotA" md-icon="plus_one">
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
import articleList from "@/components/resultat/ArticleList";

import dummyArticles from "@/services/dummy/articles.json";
import dummyResults from "@/services/dummy/results.json";

import {
  EventBus,
  EVENT_USER_LOGOUT,
  EVENT_BYE_REDIRECTION
} from "@/services/event-bus.js";

export default {
  name: "SotaHelper",
  components: { createSota: SotaCreate, SotaGraphic, SotaGestion, articleList },
  data() {
    return {
      articles: dummyArticles,
      selectedArticles: dummyResults.articles
    };
  },
  created() {
    EventBus.$on(EVENT_USER_LOGOUT, _ => {
      this.$router.replace({ name: "accueil" }, function onComplete() {
        EventBus.$emit(EVENT_BYE_REDIRECTION, true);
      });
    });
  }
};
</script>

<style scoped>
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

#uploadOne {
  width: 100%;
  /* height: 550px; */
}
</style>
