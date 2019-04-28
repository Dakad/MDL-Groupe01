<template>
    <div class="container">
      <div class="left">
        <h5>Research Groups:</h5>
        <li v-for="item in infoPro.ResearchGroup">
          {{ item }}
        </li>
      </div>
      <div class="center">
        <h5>Interests:</h5>
        <li v-for="item in infoPro.Interests">
          {{ item }}
        </li>

        <h5>Previous universities</h5>
        <li v-for="item in infoPro.Universities">
          {{ item }}
        </li>
      </div>
      <div class="right">
        <h5>Publicated Articles:</h5>
        <li v-for="item in infoPro.Articles">
          {{ item.item }}
        </li>
        <h5>Publicated State of the art</h5>
        <li v-for="item in infoPro.Sotas">
          {{ item.item }}
        </li>
      </div>
    </div>
</template>

<script>
    export default {
        name: "MyProfile",
      props: ["infoPro"]
    }
</script>

<style scoped>

  .container{
    position: absolute;
    height: 100%;
    width: 100%;
    border: 3px solid grey;
  }
  .left{
    position: relative;
    float: left;
    margin-right: 5%;
    margin-top: 5%;
    height: 90%;
    width: 30%;
    border: 2px solid lightgrey;
  }

  .center{
    position: relative;
    float: left;
    margin-top: 5%;
    height: 90%;
    width: 30%;
    border: 2px solid lightgrey;
  }

  .right{
    position: relative;
    float: left;
    margin-top: 5%;
    margin-left: 5%;
    height: 90%;
    width: 30%;
    border: 2px solid lightgrey;
  }

</style>
