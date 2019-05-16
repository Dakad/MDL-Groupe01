<template>
  <div class="article" v-if="hasPagination">
    <md-card md-with-hover v-for="article in list" :key="article.reference">
      <md-card-header>
        <div class="title">
          <router-link :to="{ name: 'articleDetails', params: { reference: article.reference }}">
            <h4>{{article.title}}</h4>
          </router-link>
        </div>
      </md-card-header>
      <md-card-content>
        <div class="info">
          <div class="article-authors md-subhead">
            <md-icon title="Authors">{{ article.authors.length > 1 ? 'people' : "person" }}</md-icon>
            <md-button
              class="md-primary"
              v-for="(author, i) in article.authors"
              :key="i"
              :md-ripple="false"
            >{{author}}</md-button>
          </div>
          <div class="journal">
            <p>Publisher:</p>
            <span class="md-body-2">{{article.journal}}</span>
          </div>
          <div class="date">
            <p>Domain:</p>
            <span
              class="md-body-2"
              :style="{'color': getCategoryColor(article.category)}"
            >{{article.category}}</span>
          </div>
          <div class="date">
            <p>Date:</p>
            <span class="md-body-2">{{article.year}}</span>
          </div>
          <div class="keyword"></div>
        </div>
      </md-card-content>
    </md-card>
    <Pagination
      class="md-layout md-alignment-center"
      id="article-pagination"
      v-if="page['total_size'] >= 1"
      v-model="currentPage"
      :page-count="page['total_pages']"
    />
  </div>
</template>

<script>
import { Pagination } from "@/components";
import { getColorHashOf } from "@/services/util";
export default {
  name: "ArticleList",
  components: {
    Pagination
  },
  props: {
    hasPagination: {
      type: Boolean,
      default: _ => true
    },
    list: {
      type: Array,
      default: _ => []
    },
    meta: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      currentPage: 1
    };
  },
  watch: {
    currentPage: function() {
      this.$emit("pagination", this.currentPage);
    }
  },
  computed: {
    articles() {
      return this.list;
    },
    page() {
      return this.meta || {};
    }
  },
  methods: {
    getCategoryColor(category) {
      return getColorHashOf(category)[0];
    }
  }
};
</script>

<style scoped>
.md-list {
  margin: 2px;
}
.md-list-item {
  margin: 0px;
}
.article-authors button {
  cursor: default;
}
.journal {
  display: flex;
}
.journal p {
  margin-right: 1%;
}
.date {
  display: flex;
}
.date p {
  margin-right: 1%;
}

#article-pagination {
  margin: 20px;
}
</style>
