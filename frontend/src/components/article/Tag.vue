<template>
  <div>
    <h5>List of tag:</h5>
    <ul id="tagList">
      <li v-for="item in tags">
        {{ item }}
      </li>
    </ul>
  </div>
</template>

<script>
  import json from '@/assets/dummy-Article.json';
    export default {
      name: "Tag",
      data() {
        return {
          json,
          tags: json.tags,
        };
      },
    }
</script>

<style>

</style>
