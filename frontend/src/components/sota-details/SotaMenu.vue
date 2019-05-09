<template>
  <div class="menuArticle">
    <md-card-actions>
      <md-button
        class="md-icon-button"
        title="Bookmark it"
        @click="isBookmarked = !isBookmarked"
      >
        <md-icon>{{isBookmarked ? "bookmark" : "bookmark_border"}}</md-icon>
      </md-button>

      <md-button class="md-icon-button" title="Download" @click="downloadRef">
        <md-icon>arrow_downward</md-icon>
      </md-button>
    </md-card-actions>
  </div>
</template>

<script>
export default {
  name: "SotaMenu",
  data(){
    return{
      isBookmarked: false
    }
  },
  methods: {
    downloadRef() {
      // TODO add the download function
    },
  }
};
</script>

<style scoped>
</style>
