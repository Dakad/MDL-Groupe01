<template>
  <div class="menuArticle">
    <button class="fas fa-file-download" v-on:click="downloadRef()"></button>
    <button class="fas fa-star" v-on:click="addToSOTA()"></button>
  </div>
</template>

<script>
export default {
  name: "SotaMenu",
  methods: {
    downloadRef() {
      // TODO add the download function
    },
    addToSOTA() {
      // TODO add the add to SOTA function
    }
  }
};
</script>

<style scoped>
</style>
