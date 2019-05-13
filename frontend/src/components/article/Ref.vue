<template>
  <div>
    <h6>List of references:</h6>
    <ul id="refList">
      <li v-for="(item, index) in refs" :key="index">{{ item }}</li>
    </ul>
    <h6>List of SoTA using it:</h6>
    <ul id="refList">
      <li v-for="(item,index) in sotas" :key="index">{{ item }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "Ref",
  props: ["refs", "sotas"],
  data() {
    return {
      data: {}
    };
  }
};
</script>

<style>
</style>
