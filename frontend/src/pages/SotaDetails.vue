<template>
  <div id="sota ">
    <div class="md-layout md-gutter">
      <h3 class="sota-title md-display-1 md-layout-item md-size-80">{{ sota.title }}</h3>
      <div class="md-layout-item md-size-25">
        <p>
          Main subject:
          <b>{{sota.subject}}</b>
        </p>
        <p>Date of creation: {{sota.created_at}}</p>
      </div>
      <div class="md-layout-item md-size-20">
        <p>Creator: {{creatorFullname}}</p>
        <p>Creator e-mail: {{sota.creator.email}}</p>
      </div>
      <div class="menuBo">
        <!-- TODO gerer les boutons pour telecharger le bibtex et bookmark -->
        <SotaMenu
          :reference="sota.reference"
          :is-bookmarked="isBookmarked"
          @bookmark="bookmarkSota"
        ></SotaMenu>
      </div>
    </div>
    <md-tabs md-alignment="fixed" :md-active-tab="activeTab" class="tabSize">
      <md-tab id="articleList" md-label="List of articles" md-icon="view_module">
        <h5>List of article in the SOTA:</h5>

        <article-list v-show="!loading" :list="sota.articles"></article-list>
      </md-tab>

      <md-tab id="visuSota" md-label="Articles-Tree" md-icon="view_module">
        <!-- TODO Mettre la visu de David -->
      </md-tab>
    </md-tabs>
  </div>
</template>

<script>
// import InfoNav from "@/components/article/InfoNav";
import SotaMenu from "@/components/sota-details/SotaMenu";
import ArticleList from "@/components/resultat/ArticleList";
import {
  getSota,
  sotaGetBookmark,
  sotaDeleteBookmark,
  sotaPostBookmark
} from "@/services/api-sota";

export default {
  name: "SotaDetails",
  props: ["reference"],
  components: {
    // InfoNav,
    SotaMenu,
    ArticleList
  },
  data() {
    return {
      sota: {},
      isBookmarked: false
    };
  },
  computed: {
    creatorFullname() {
      return this.sota.creator.lastname + " " + this.sota.creator.firstname;
    }
  },

  watch: {
    $route: "fetchSota"
  },

  created() {
    // fetch the data when the view is created
    this.fetchSota();
    sotaGetBookmark(this.reference).then(
      data => (this.isBookmarked = data.done)
    );
  },

  methods: {
    fetchSota() {
      return getSota(this.reference).then(data => (this.sota = data));
    },
    bookmarkSota() {
      if (this.isBookmarked) {
        sotaDeleteBookmark(this.reference).then(
          x => (this.isBookmarked = false)
        );
      } else {
        sotaPostBookmark(this.reference).then(x => (this.isBookmarked = true));
      }
    }
  }
};
</script>

<style scoped>
#sota {
  border: solid lightgrey 1px;
}

.sota-title {
  margin: 15px 0;
}

.menuBo {
  float: left;
  margin-left: 100px;
}

.infoBaseLeft {
  float: left;
}

.infoBaseRight {
  float: left;
  margin-left: 60px;
}
</style>

