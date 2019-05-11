<template v-cloak>
  <div id="sota  ">
    <div class="sota-details md-layout md-gutter md-alignment-top-left">
      <h3 class="sota-title md-display-1 md-layout-item md-size-80">{{ sota.title }}</h3>
      <div class="md-layout-item md-size-25">
        <md-chip id="sota-subject" :style="subjectColor" title="Main subject">
          <span class="md-subheading">{{sota.subject}}</span>
        </md-chip>
        <p>
          <md-icon title="Date of creation">date_range</md-icon>&nbsp;
          <b>{{sota.created_at}}</b>
        </p>
      </div>
      <div class="md-layout-item md-size-20">
        <p>
          <router-link
            :to="{name:'userProfile', params: {username : sota.creator.username}}"
            id="sota-creator-name"
            :title="`Go to ${creatorFullname} profile`"
          >
            <md-icon title="Creator">person</md-icon>&nbsp;
            <b>{{creatorFullname}}</b>
          </router-link>
        </p>
        <p id="sota-creator-email">
          <md-icon title="Creator e-mail">mail</md-icon>&nbsp;
          <b>{{sota.creator.email}}</b>
        </p>
      </div>
      <div class="menuBo">
        <SotaMenu
          v-if="userIsLogged"
          :reference="sota.reference"
          :is-bookmarked="isBookmarked"
          @bookmark="bookmarkSota"
        ></SotaMenu>
      </div>
    </div>
    <md-tabs md-alignment="fixed" :md-active-tab="activeTab" class="sota-articles">
      <md-tab id="articles-list" md-label="List of articles" md-icon="view_day">
        <h5>List of article in the SOTA:</h5>

        <article-list :list="sota.articles"></article-list>
      </md-tab>

      <md-tab id="tree-visu" md-label="Articles-Tree" md-icon="view_module">
        <!-- TODO Mettre la visu de David -->
      </md-tab>
    </md-tabs>
  </div>
</template>

<script>
import ColorHash from "color-hash";

// import InfoNav from "@/components/article/InfoNav";
import SotaMenu from "@/components/sota-details/SotaMenu";
import ArticleList from "@/components/resultat/ArticleList";
import {
  getSota,
  sotaGetBookmark,
  sotaDeleteBookmark,
  sotaPostBookmark
} from "@/services/api-sota";
import { isLogged } from "@/services/api-user";

import {
  EventBus,
  EVENT_USER_LOGGED,
  EVENT_USER_LOGOUT,
  EVENT_APP_MESSAGE
} from "@/services/event-bus.js";

const colorHash = new ColorHash();

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
      userIsLogged: isLogged(),
      sota: {
        creator: {}
      },
      isBookmarked: false,
      activeTab: "articles-list"
    };
  },
  computed: {
    creatorFullname() {
      if (this.sota.hasOwnProperty("reference")) {
        return this.sota.creator.lastname + " " + this.sota.creator.firstname;
      } else {
        return null;
      }
    },
    subjectColor() {
      return {
        "background-color": colorHash.hex(this.sota.subject)
      };
    }
  },

  watch: {
    $route: "fetchSota"
  },

  created() {
    if (this.userIsLogged) {
      this.getBookmarkState();
    }

    EventBus.$on(EVENT_USER_LOGOUT, _ => (this.userIsLogged = false));

    // fetch the data when the view is created
    this.fetchSota();
  },

  methods: {
    fetchSota() {
      return getSota(this.reference).then(data => (this.sota = data));
    },
    bookmarkSota() {
      if (!this.userIsLogged) return;

      if (this.isBookmarked) {
        sotaDeleteBookmark(this.reference)
          .then(x => (this.isBookmarked = false))
          .then(_ =>
            EventBus.$emit(EVENT_APP_MESSAGE, "SoTA removed from bookmarks")
          );
      } else {
        sotaPostBookmark(this.reference)
          .then(x => (this.isBookmarked = true))
          .then(_ => EventBus.$emit(EVENT_APP_MESSAGE, "SoTA bookmarked"));
      }
    },
    getBookmarkState() {
      sotaGetBookmark(this.reference).then(
        data => (this.isBookmarked = data.done)
      );
    }
  }
};
</script>

<style scoped>
#sota {
  border: solid lightgrey 1px;
}

.sota-details {
  margin: 0;
  margin-bottom: 35px;
}

.sota-title {
  margin: 15px 0;
}

#sota-subject {
  margin-bottom: 10px;
}

#sota-creator-name {
  text-decoration: none;
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

