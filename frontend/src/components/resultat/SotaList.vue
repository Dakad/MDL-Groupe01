<template>
  <div class="sota">
    <md-card v-for="sota in list" :key="sota.reference">
      <md-card-header>
        <div class="title">
          <h1>
            <a href="/">{{title}}</a>
          </h1>
        </div>
      </md-card-header>
      <md-card-content>
        <div class="info">
          <p>Written By</p>
          <div class="author">
            <md-list v-for="author in authors" v-bind:key="author">
              <md-item href="/">{{author}}</md-item>
            </md-list>
          </div>
          <div class="keyword"></div>
          <div class="date">
            <p>published on the</p>
            <h4>{{date}}</h4>
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
  name: "SotaList",
  components: {
    Pagination
  },
  props: {
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
    sotas() {
      return this.list;
    },
    page() {
      return this.meta || {};
    }
  }
};
</script>

<style scoped>
.md-list {
  margin: 10px;
  display: inline-block;
}
</style>
