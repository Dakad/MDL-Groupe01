<template>
  <div class="container">
    <div class="left">
      <h5>Research Groups:</h5>
      <li v-for="(item, index) in infoPro.ResearchGroup" :key="index">{{ item }}</li>
      <p v-if="infoPro.ResearchGroup ==null">No Research Groups</p>
    </div>
    <div class="center">
      <h5>Interests:</h5>
      <li v-for="(item, index) in infoPro.Interests" :key="index">{{ item }}</li>
      <p v-if="infoPro.Interests ==null">No Interests registered</p>

      <h5>Previous universities</h5>
      <li v-for="(item, index) in infoPro.Universities" :key="index">{{ item }}</li>
      <p v-if="infoPro.Universities ==null">No Previous Universities</p>
    </div>
    <div class="right">
      <h5>Publicated Articles:</h5>
      <li v-for="(item, index) in infoPro.recentArticles" :key="index">{{ item }}</li>
      <h5>Publicated State of the art</h5>
      <li v-for="(item, index) in infoPro.recentSotAs" :key="index">{{ item }}</li>
    </div>
  </div>
</template>

<script>
  export default {
    name: "MyProfile",
    props: ["infoPro"]
  };
</script>

<style scoped>
  .container {
    position: absolute;
    height: 100%;
    width: 100%;
  }
  .left {
    position: relative;
    float: left;
    margin-right: 5%;
    height: 95%;
    width: 30%;
  }

  .center {
    position: relative;
    float: left;
    height: 95%;
    width: 30%;
  }

  .right {
    position: relative;
    float: left;
    margin-left: 5%;
    height: 95%;
    width: 30%;
  }
</style>
