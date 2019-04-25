<template>
    <div class="container">
      <div class="left">

      </div>
      <div class="right">

      </div>
      <div class="bottom">

      </div>
    </div>
</template>

<script>
    export default {
        name: "CreateSotA"
    }
</script>

<style scoped>
  .container{
    position: absolute;
    width: 100%;
    height: 100%;
    border: grey solid 3px;
  }

  .left{
    position: relative;
    float: left;
    width: 47%;
    height: 75%;
    margin: 1%;
    border: green solid 3px;
  }

  .right{
    position: relative;
    float: left;
    width: 47%;
    height: 75%;
    margin: 1%;
    border: red solid 3px;
  }

  .bottom{
    position: relative;
    width: 99%;
    height: 9%;
    float: bottom;
    border: blue solid 3px;
  }





</style>
