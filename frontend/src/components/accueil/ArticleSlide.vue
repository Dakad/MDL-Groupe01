<template>
  <md-card>
    <md-card-header>
      <router-link class="md-title" :to="{ name: 'articleDetails', params: { reference: reference }}">
        {{ title }}
      </router-link>
      <div class="md-subhead">
        <span v-for="(name,index) in authors" :key="name">
          {{ name }}&nbsp;<b v-if="index<authors.length-1">,</b>
        </span>
      </div>
    </md-card-header>
    <md-card-content>
      <h5><md-chip :style="colorCategory" title="Category : ">{{category}}</md-chip>&nbsp;{{ date }}</h5><br>
      <h6>Views: {{ nbViews }}     Quotes: {{ nbQuotes }}</h6>
      <md-chip class="chip" v-for="(word) in keywords" :key="word.slug">{{ word.name }}</md-chip>
    </md-card-content>
  </md-card>
</template>

<script>
  import { Slide } from "vue-carousel";
  import ColorHash from "color-hash";

  const colorHash = new ColorHash();

  export default {
    name: "ArticleSlide",
    props: ["title", "authors", "category", "date", "nbViews", "nbQuotes", "keywords", "reference"],
    computed: {
      colorCategory() {
        return {
          "background-color": colorHash.hex(this.category)
        };
      },
    },
  };
</script>

<style scoped>
  .chip {
    margin: 3px;
  }
</style>