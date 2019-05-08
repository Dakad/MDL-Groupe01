<template>
  <div class="Sota">
    <div class="leftContainer">
      <h3>{{ sota.title }}</h3>
      <div class="infoBaseLeft">
        <p>Main subject: {{sota.subject}}</p>
        <p>Date of creation: {{sota.created_at}}</p>
      </div>
      <div class="infoBaseRight">
        <p>Creator: {{sota.creator.lastname}} {{sota.creator.firstname}}</p>
        <p>Creator e-mail: {{sota.creator.email}}</p>
      </div>
      <div class="menuBo">
        <!-- TODO gerer les boutons pour telecharger le bibtex et bookmark -->
        <SotaMenu></SotaMenu>
      </div>
    </div>
    <md-tabs md-alignment="fixed" :md-active-tab="activeTab" class="tabSize">
      <md-tab id="articleList" md-label="List of articles" md-icon="view_module">
        <h5>List of article in the SOTA:</h5>

        <article-list v-show="!loading" :list="sota.articles"></article-list>

        <infoNav></infoNav>
      </md-tab>

      <md-tab id="visuSota" md-label="Articles-Tree" md-icon="view_module">
        <!-- TODO Mettre la visu de David -->
        <!-- <sota-graphic :articles="sota.articles"/> -->
      </md-tab>
    </md-tabs>
  </div>
</template>

<script>
import InfoNav from "@/components/article/InfoNav";
import SotaMenu from "@/components/sota-details/SotaMenu";
import SotaGraphic from "@/components/sota-helper/SotaGraphic";
import ArticleList from "@/components/resultat/ArticleList";
import { getSota } from "@/services/api-sota";

export default {
  name: "Sota",
  props: ["reference"],
  components: {
    InfoNav,
    SotaMenu,
    ArticleList,
    SotaGraphic
  },
  data() {
    return {
      articleTitle: {},
      abstract: {},
      sota: {}
    };
  },
  watch: {
    $route: "fetchSota"
  },

  created() {
    // fetch the data when the view is created
    this.fetchSota();
  },

  methods: {
    fetchSota() {
      return getSota(this.reference).then(data => (this.sota = data));
    }
  }
};
</script>

<style scoped>
.Sota {
  border: solid lightgrey 1px;
}
h1 {
  position: absolute;
  left: 10%;
  width: 70%;
  top: 10%;
}

.menuBo {
  float: left;
  margin-left: 100px;
}

.leftContainer {
  float: left;
  width: 60%;
  height: 80%;
  margin: 15px;
}

.rightContainer {
  margin-top: 15%;
  width: 30%;
  height: 90%;
  float: left;
}

.tabSize {
  float: left;
}

.infoBaseLeft {
  float: left;
}

.infoBaseRight {
  float: left;
  margin-left: 60px;
}
</style>

