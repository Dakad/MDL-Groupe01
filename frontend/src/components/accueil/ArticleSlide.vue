<template>
  <md-card class="size">
    <md-card-header>
      <router-link
        class="md-title"
        :to="{ name: 'articleDetails', params: { reference: reference }}"
      >
        <h5>{{ title }}</h5>
      </router-link>
      <div class="md-subhead">
        <span v-for="(name,index) in authors" :key="name">
          {{ name }}&nbsp;
          <b v-if="index<authors.length-1">,</b>
        </span>
      </div>
    </md-card-header>

    <md-card-content>
      <h6>
        <md-chip :style="colorCategory" title="Category : ">{{category}}</md-chip>
        &nbsp;&nbsp;
        {{ year }}
        <b v-if="month">, {{ month }}</b>
      </h6>
      <br>
      <p>Views: {{ nbViews }}&nbsp;&nbsp;Quotes: {{ nbQuotes }}</p>
      <md-chip class="chip" v-for="(word) in keywords" :key="word.slug">{{ word.name }}</md-chip>
    </md-card-content>
  </md-card>
</template>

<script>
import { Slide } from "vue-carousel";
import { getColorHashOf } from "@/services/util";

export default {
  name: "ArticleSlide",
  props: [
    "title",
    "authors",
    "category",
    "year",
    "month",
    "nbViews",
    "nbQuotes",
    "keywords",
    "reference"
  ],
  computed: {
    colorCategory() {
      return {
        "background-color": getColorHashOf(this.category)
      };
    }
  }
};
</script>

<style scoped>
.chip {
  margin: 4px;
}

.size {
  min-height: 100%;
}
</style>
