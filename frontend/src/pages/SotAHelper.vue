<template>
  <div class="container">
    <div class="title"><h2>State of the Art Helper</h2></div>

    <div class="tabs">
      <md-tabs md-alignment="fixed" :md-active-tab="activeTab" @md-changed="activeTab = $event">
        <md-tab id="gestion" md-label="Gestion" md-icon="view_module">

        </md-tab>
        <md-tab id="visualisation" md-label="Visualisation" md-icon="view_module">

        </md-tab>
        <md-tab id="recommended" md-label="Recommended" md-icon="view_module">
          
        </md-tab>
        <md-tab id="uploadOne" md-label="Upload new SotA" md-icon="view_module">

        </md-tab>
      </md-tabs>
    </div>
  </div>
</template>

<script>

import dummy from "@/assets/dummy-Results.json";

import articleList from "@/components/resulat/ArticleList";
  
export default {
  name: "SotAHelper",
  // components: {
  //   articleList,
  // },
  data() {
    return {
      list: dummy.results.articles,
    };
  },
}
</script>

<style scoped>
  .container{
    position: absolute;
    width: 100%;
    height: 86%;
    max-width: 1900px;
    margin-top: 1%;
  }

  .title{
    position: relative;
    width: 100%;
    height: 10%;
  }
  h2{
    text-align: center;
  }

  .tabs{
    position: relative;
    width: 100%;
    height: 80%;
    margin-top: 3%;
  }

.recommend {
  width: 75%;
}

.slides {
  border: 1px solid gray;
  margin: 1%;
  padding: 1%;
}
</style>
