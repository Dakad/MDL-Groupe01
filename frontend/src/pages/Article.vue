<template>
  <div class="md-layout md-alignment-top-space-around">
    <div class="md-layout-item md-size-66 md-medium-size-66 md-small-size-100">
      <md-card md-with-hover id="article-container">
        <md-card-header>
          <h1 class="article-title md-display-2">{{ article.title }}</h1>
          <div class="article-authors md-subhead">
            <md-icon title="Authors">{{ article.authors.length > 1 ? 'people' : "person" }}</md-icon>&nbsp;
            <!-- <label >By</label>: -->
            <md-button
              class="md-primary"
              v-for="(author, i) in article.authors"
              :key="i"
              :md-ripple="false"
            >{{author}}</md-button>
          </div>

          <div class="article-journal md-subhead">
            <md-icon title="Journal">import_contacts</md-icon>&nbsp;
            <!-- <span class>Journal :</span>&nbsp; -->
            <span>{{article.journal}}</span> &nbsp;
            <span v-show="article.volume" title="Journal Volume">- {{article.volume}}</span>
          </div>

          <div class="article-keywords">
            <!-- <label>Keywords</label>: -->
            <md-chip class="chip-c" :style="colorCategory" title="Category : ">{{article.category}}</md-chip>
            <md-chip
              class="chip"
              v-for="keyword in article.keywords"
              :key="keyword.slug"
            >{{keyword.name}}</md-chip>
          </div>
        </md-card-header>
        <md-card-media-actions>
          <md-card-area>
            <md-content class="article-abstract">
              <!-- <p>{{ article.content}}</p> -->
              <h4 v-if="abstract.length==0">
                <md-icon class="md-size-2x">mood_bad</md-icon>&nbsp;Oops, abstract missing !
              </h4>
              <p
                v-else
                v-for="(paragraph, i) in abstract"
                :key="i"
                class="md-subheading"
              >{{ paragraph }}</p>
            </md-content>
          </md-card-area>

          <md-card-actions>
            <md-button
              v-if="isLogged"
              class="md-icon-button"
              title="Bookmark it"
              @click="setBookmark"
            >
              <md-icon>{{isBookmarked ? "bookmark" : "bookmark_border"}}</md-icon>
            </md-button>

            <md-button
              class="md-icon-button"
              title="Go the article source"
              @click="onClick"
              :disable="info.link==null"
            >
              <md-icon>launch</md-icon>
            </md-button>
          </md-card-actions>
        </md-card-media-actions>
      </md-card>
    </div>

    <div class="md-layout-item md-size-25 md-medium-size-25 md-small-size-100 md-layout">
      <div class="info-container md-layout-item md-size-90">
        <info-nav :info="info"></info-nav>
      </div>
      <div class="ref-container md-layout-item md-size-90">
        <info-nav :refs="article.refs"></info-nav>
      </div>
    </div>
  </div>
</template>

<script>
import { getColorHashOf } from "@/services/util";
import InfoNav from "@/components/article/InfoNav";
import MenuArticle from "@/components/article/MenuArticle";
import { getArticleByReference } from "@/services/api";
import { isLogged } from "@/services/api-user";
import {
  getBookmarked,
  postBookmark,
  deleteBookmark
} from "@/services/api-article";
import {
  EventBus,
  EVENT_USER_LOGOUT,
  EVENT_APP_MESSAGE
} from "@/services/event-bus.js";

export default {
  name: "Article",
  props: ["reference"],
  components: {
    InfoNav
    //MenuArticle
  },
  data() {
    return {
      article: {},
      isLogged: isLogged(),
      isBookmarked: false
    };
  },
  watch: {
    $route: "fetchArticle"
  },
  mounted() {
    EventBus.$on(EVENT_USER_LOGOUT, _ => (this.isLogged = false));

    // fetch the data when the view is created
    this.fetchArticle();
    getBookmarked(this.reference).then(data => (this.isBookmarked = data.done));
  },

  computed: {
    colorCategory() {
      return {
        "background-color": getColorHashOf(this.article.category)
      };
    },
    abstract() {
      if (
        !this.article.abstract ||
        this.article.abstract == this.article.title
      ) {
        return [];
      }
      return this.article.abstract.split("\n");
    },
    info() {
      const {
        created_at,
        category,
        nb_citations,
        nb_views,
        year,
        month,
        pages,
        url
      } = this.article;
      return Object.assign(
        {},
        {
          created_at,
          category,
          nb_citations,
          nb_views,
          year,
          month,
          pages,
          link: url
        }
      );
    }
  },

  methods: {
    fetchArticle() {
      return getArticleByReference(this.reference).then(
        data => (this.article = data)
      );
    },
    getBookmarkState() {
      getBookmarked(this.reference).then(
        data => (this.isBookmarked = data.done)
      );
    },

    setBookmark() {
      if (!this.isBookmarked) {
        postBookmark(this.reference)
          .then(x => (this.isBookmarked = true))
          .then(_ => EventBus.$emit(EVENT_APP_MESSAGE, "Article bookmarked"));
      } else if (this.isBookmarked) {
        deleteBookmark(this.reference)
          .then(x => (this.isBookmarked = false))
          .then(_ =>
            EventBus.$emit(EVENT_APP_MESSAGE, {
              type: "error",
              msg: "Article removed from bookmarks"
            })
          );
      }
    },

    onClick() {
      if (this.info.link != null) {
        window.open(this.info.link, "_blank");
      } else {
        EventBus.$emit(EVENT_APP_MESSAGE, {
          type: "error",
          msg: "Oops, link missing !"
        });
      }
    }
  }
};
</script>

<style  lang="css" scoped>
#article-container {
  margin-top: 15px;
  cursor: auto;
}

#article-container .article-title {
  margin: 3vh 0;
}

#article-container .article-authors button {
  cursor: default;
}

#article-container .article-keywords {
  margin: 1vh 0;
}

#article-container .article-abstract {
  margin: 15px 0;
}

.chip-c {
  margin-bottom: 4px;
  margin-left: 5px;
}

.chip {
  margin-bottom: 4px;
}
</style>
