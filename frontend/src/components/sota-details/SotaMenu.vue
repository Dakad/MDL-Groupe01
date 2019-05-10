<template>
  <div class="menuArticle">
    <md-card-actions>
      <md-button
        class="md-icon-button"
        title="Bookmark it"
        @click="getBook"
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
 import {sotaDeleteBookmark, sotaGetBookmark, sotaPostBookmark} from "../../services/api-sota";

 export default {
  name: "SotaMenu",
  data(){
    return{
      isBookmarked: false
    }
  },

  created() {
    sotaGetBookmark(this.reference).then(
      data => (this.isBookmarked = data.done)
    );
  },
  methods: {
    downloadRef() {
      // TODO add the download function
    },

    getBook(){
      if (isBookmarked){
        sotaDeleteBookmark(this.reference).then(x => this.isBookmarked = false)
      } else if (!isBookmarked){
        sotaDeleteBookmark(this.reference).then(x => this.isBookmarked = true)
      }
    }
  }
};
</script>

<style scoped>
</style>
