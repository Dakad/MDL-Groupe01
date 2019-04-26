<template>
  <div class="md-layout md-gutter md-alignment-center-space-around">
    <div class="md-layout-item md-size-66 md-medium-size-66 md-small-size-100">
      <div class="article-container">
        <h1 class="article-title md-display-2">{{ article.title }}</h1>

        <div class="article-actions md-layout md-gutter md-alignment-center-right">
          <menu-article :article-reference="article.reference"></menu-article>
        </div>
        <div class="article-abstract">
          <p v-for="(paragraph, i) in abstract" :key="i" class="md-subheading">{{ paragraph }}</p>
        </div>
      </div>
    </div>

    <div class="md-layout-item md-size-25 md-medium-size-25 md-small-size-100">
      <div class="info-container">
        <info-nav :info="info" :tags="article.keywords" :refs="article.refs"></info-nav>
      </div>
    </div>
  </div>

  <!-- <div class="container">



  </div>-->
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
  .article-title {
    margin: 3vh 0;
  }

  .article-actions {
    // float: right;
  }

  .article-abstract {
    margin: 15px 0;
  }
}

.info-container {
}
</style>

