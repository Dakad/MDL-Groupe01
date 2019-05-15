<template>
  <div class="app-sota-helper">
    <div class="title">
      <h2>State of the Art Helper</h2>
    </div>

    <div class="tabs">
      <md-tabs md-alignment="fixed" md-active-tab="upload-one">
        <md-tab id="overview" md-label="Overview" md-icon="view_module">
          <sota-overview :bookmarked="bookmarked"/>
        </md-tab>
        <md-tab id="recommanded" md-label="Recommended" md-icon="thumb_up">
          <article-list v-show="!loading" :list="recommended"></article-list>
          <md-empty-state
            v-if="!recommended || recommended.length == 0"
            md-icon="description"
            md-label="No articles found"
            md-description="Creating project, you'll be able to upload your design and collaborate with people."
          ></md-empty-state>
        </md-tab>
        <md-tab id="visu" md-label="Visualisation" md-icon="share">
          <sota-graphic v-if="!loading" :articles="recommended" :categories="articlesCategories"/>
        </md-tab>
        <md-tab id="upload-one" md-label="Create a new SotA" md-icon="plus_one">
          <sota-create @create="postNewSota"></sota-create>
        </md-tab>
      </md-tabs>
    </div>
  </div>
</template>

<script>
import SotaOverview from "@/components/sota-helper/SotaOverview";
import SotaCreate from "@/components/sota-helper/SotaCreate";
import SotaGraphic from "@/components/sota-helper/SotaGraphic";
import articleList from "@/components/resultat/ArticleList";
import { getRecommanded } from "@/services/api-article";
import { getBookmark } from "@/services/api-user";
import { createSota } from "@/services/api-sota";

import dummyResults from "@/services/dummy/results.json";

import {
  EventBus,
  EVENT_APP_MESSAGE,
  EVENT_USER_LOGOUT,
  EVENT_BYE_REDIRECTION
} from "@/services/event-bus.js";

export default {
  name: "SotaHelper",
  components: {
    SotaCreate,
    SotaGraphic,
    SotaOverview,
    articleList
  },
  data() {
    return {
      loading: false,
      recommended: [],
      bookmarked: []
    };
  },
  computed: {
    articlesCategories() {
      const setList = new Set(this.recommended.map(a => a["category"]));
      return [...setList].sort();
    },
    listBookmarkedRefs() {
      return this.bookmarked.map(bk => bk["article"]["reference"]);
    }
  },
  created() {
    EventBus.$on(EVENT_USER_LOGOUT, _ => {
      this.$router.replace({ name: "accueil" }, function onComplete() {
        EventBus.$emit(EVENT_BYE_REDIRECTION, true);
      });
    });

    this.fetchRecommanded();
    this.fetchBookmarks();
  },
  methods: {
    fetchRecommanded() {
      this.loading = true;
      return getRecommanded().then(data => {
        this.recommended = data;
        this.loading = false;
      });
    },

    fetchBookmarks() {
      this.loading = true;
      return getBookmark().then(data => {
        console.log(data);
        this.bookmarked = data;
        this.loading = false;
      });
    },

    postNewSota(newSota) {
      return createSota(newSota)
        .then(data => {
          this.$router.push({
            name: "sotaDetails",
            params: {
              reference: data["reference"]
            }
          });
          console.log(data);
          EventBus.$emit(EVENT_APP_MESSAGE, {
            type: "info",
            message: "New SoTA created"
          });
        })
        .catch(err => {
          // Send a flash error msg about theh sota creation
          EventBus.$emit(EVENT_APP_MESSAGE, {
            type: "error",
            message: err.body["message"] || "Operation failed : creation failed"
          });
        });
    }
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

#upload-one {
  width: 100%;
  /* height: 550px; */
}
</style>
