<template>
  <div class="menuArticle">
    <button class="fas fa-file-download" v-on:click="downloadRef()"></button>
    <button class="far fa-bookmark" v-on:click="bookmark()"></button>
    <button class="fas fa-star" v-on:click="addToSOTA()"></button>
  </div>
</template>

<script>
  export default {
    name: "MenuArticle",
    props: {
      articleReference: String
    },
    methods: {
      downloadRef() {
        // TODO add the download function
      },
      bookmark() {
        // TODO add to the bookmark function
      },
      addToSOTA() {
        // TODO add the add to SOTA function
      }
    }
  };
</script>

<style scoped>
</style>
