<template>
  <div class="Sota">
    <div class="leftContainer">
      <h3>{{ articles.title }}</h3>
      <div class="infoBaseLeft">
        <p>Main subject: {{articles.subject}}</p>
        <p>Date of creation: {{articles.created_at}}</p>
      </div>
      <div class="infoBaseRight">
        <p>Creator: {{articles.creator.lastname}} {{articles.creator.firstname}}</p>
        <p>Creator e-mail: {{articles.creator.email}}</p>
      </div>
      <div class="menuBo">
        <!-- TODO gerer les boutons pour telecharger le bibtex et bookmark -->
          <SotaMenu></SotaMenu>
      </div>
    </div>
      <md-tabs
        md-alignment="fixed"
        :md-active-tab="activeTab"
        class="tabSize">

        <md-tab id="articleList" md-label="List of articles" md-icon="view_module">
          <h5>List of article in the SOTA:</h5>

          <article-list
            v-show="!loading"
            :list="articles.articles"
          ></article-list>

          <infoNav></infoNav>
        </md-tab>

        <md-tab id="visuSota" md-label="Articles-Tree" md-icon="view_module">
          <!-- TODO Mettre la visu de David -->

        </md-tab>

      </md-tabs>


  </div>
</template>

<script>
import InfoNav from "@/components/article/InfoNav";
import SotaMenu from "@/components/sota-details/SotaMenu";
import ArticleList from "@/components/resultat/ArticleList";
import { getSota }  from "@/services/api-sota";

export default {
  name: "Sota",
  props: ["reference"],
  components: {
    InfoNav,
    SotaMenu,
    ArticleList
  },
  data() {
    return {
      articleTitle: {},
      abstract: {},
      articles: {}
    };
  },
  watch: {
    $route: "fetchSota"
  },

  created() {
    // fetch the data when the view is created
    this.fetchSota();
  },

  methods:{
    fetchSota() {
      return getSota(this.reference).then(
        data => (this.articles = data)
      );
    }
  }
};
</script>

<style scoped>
  .Sota{
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
  position: relative;
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

  .tabSize{
    float:left;
  }

  .infoBaseLeft{
    float: left;
  }

  .infoBaseRight{
    float: left;
    margin-left: 60px;
  }

</style>

