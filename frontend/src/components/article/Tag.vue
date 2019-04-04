<template>
  <div>
    <h5>List of tag:</h5>
    <ul id="tagList">
      <li v-for="(item,index) in tags" :key="index">{{ item }}</li>
    </ul>
  </div>
</template>

<script>
import json from "@/assets/dummy-Article.json";
export default {
  name: "Tag",
  props: ["tags"],
  data() {
    return {
      json,
      tags: json.tags
    };
  }
};
</script>

<style>
</style>
