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
    this.isBookmarked =  sotaGetBookmark()
  },
  methods: {
    downloadRef() {
      // TODO add the download function
    },

    getBook(){
      if (isBookmarked){
        sotaDeleteBookmark()
      } else if (!isBookmarked){
        sotaPostBookmark()
      }
    }
  }
};
</script>

<style scoped>
</style>
