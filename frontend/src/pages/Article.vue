<template>
  <div class="md-layout md-gutter md-alignment-center-space-around">
    <div class="md-layout-item md-size-66 md-medium-size-66 md-small-size-100">
      <md-card md-with-hover class="article-container">
        <md-card-header>
          <h1 class="article-title md-display-2">{{ article.title }}</h1>
          <div class="article-authors md-subhead">
            <!-- <label >By</label>: -->
            <md-button class="md-primary" v-for="(author, i) in article.authors" :key="i">{{author}}</md-button>
          </div>
          <div class="article-keywords">
            <!-- <label>Keywords</label>: -->
            <md-chip class="md-accent" title="Category : ">{{article.category}}</md-chip>
            <md-chip v-for="keyword in article.keywords" :key="keyword.slug">{{keyword.name}}</md-chip>
          </div>
        </md-card-header>
        <md-card-media-actions>
          <md-card-area>
            <md-content class="article-abstract">
              <p v-for="(paragraph, i) in abstract" :key="i" class="md-subheading">{{ paragraph }}</p>
            </md-content>
          </md-card-area>

          <md-card-actions>
            <md-button class="md-icon-button" title="Add to SoTA">
              <md-icon>playlist_add</md-icon>
            </md-button>

            <md-button class="md-icon-button" title="Bookmark it">
              <md-icon>bookmark</md-icon>
            </md-button>

            <md-button class="md-icon-button" title="Go the article URL">
              <md-icon>get_app</md-icon>
            </md-button>
          </md-card-actions>
        </md-card-media-actions>
      </md-card>
    </div>

    <div class="md-layout-item md-size-25 md-medium-size-25 md-small-size-100">
      <div class="info-container">
        <info-nav :info="info" :tags="article.keywords" :refs="article.refs"></info-nav>
      </div>
    </div>
  </div>
</template>

<script>
import InfoNav from "@/components/article/InfoNav";
import MenuArticle from "@/components/article/MenuArticle";
import { getArticleByReference } from "@/services/api";

export default {
  name: "Article",
  props: ["reference"],
  components: {
    InfoNav,
    MenuArticle
  },
  data() {
    return {
      article: {}
    };
  },
  watch: {
    $route: "fetchArticle"
  },
  created() {
    // fetch the data when the view is created
    this.fetchArticle();
  },

  computed: {
    abstract() {
      if (!this.article.content) {
        return [];
      }
      return this.article.content.split("\n");
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
    }
  }
};
</script>

<style  lang="scss" scoped>
.article-container {
  margin-top: 15px;
  cursor: auto;

  .article-title {
    margin: 3vh 0;
  }

  .article-authors button {
    cursor: default;
  }

  .article-keywords {
    margin: 1vh 0;
  }

  .article-abstract {
    margin: 15px 0;
  }
}

.info-container {
}
</style>

