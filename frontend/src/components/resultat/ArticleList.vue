<template>
  <div class="article" v-if="hasPagination">
    <md-card v-for="article in list" :key="article.reference">
      <md-card-header>
        <div class="title">
          <router-link :to="{ name: 'articleDetails', params: { reference: article.reference }}">
            <h4>{{article.title}}</h4>
          </router-link>
        </div>
      </md-card-header>
      <md-card-content>
        <div class="info">
          <p>Written By</p>
          <div class="author">
            <md-list v-for="author in article.authors" v-bind:key="author">
              <md-item href="/">{{author}}</md-item>
            </md-list>
          </div>
          <div class="keyword">

          </div>
          <div class="date">
            <p>Published on the {{article.year}}</p>
          </div>
        </div>
      </md-card-content>
    </md-card>
    <Pagination
      v-if="page['total_pages'] >= 1"
      v-model="currentPage"
      :page-count="page['total_pages']"
    />
  </div>
</template>

<script>
import { Pagination } from "@/components";

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
  methods: {}
};
</script>

<style scoped>
.md-list {
  margin: 10px;
  display: inline-block;
}

  .author{
    float: left;
    width: 30%;
    margin: 1.5%;
  }
  .keyword{
    float: left;
    width: 30%;
    margin: 1.5%;
  }
  .date{
    float: left;
    width: 17%;
    margin: 1.5%;
  }
</style>
