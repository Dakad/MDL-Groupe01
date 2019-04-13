<template>
  <div>
    <h5>List of reference:</h5>
    <ul id="refList">
      <li v-for="(item, index) in refs" :key="index">{{ item }}</li>
    </ul>
    <h5>List of SoTA using it:</h5>
    <ul id="refList">
      <li v-for="(item,index) in sotas" :key="index">{{ item }}</li>
    </ul>
  </div>
</template>

<script>
import json from "@/assets/dummy-Article.json";
export default {
  name: "Ref",
  props: ["refs", "sotas"],
  data() {
    return {
      json,
      callRef: json.refs,
      callSota: json.sotas
    };
  }
};
</script>

<style>
</style>
