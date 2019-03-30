<template>
  <div>
    <h5>List of reference:</h5>
    <ul id="refList">
      <li v-for="item in callRef">
        {{ item }}
      </li>
    </ul>
    <h5>List of SoTA using it:</h5>
    <ul id="refList">
      <li v-for="item in callSota">
        {{ item }}
      </li>
    </ul>
  </div>
</template>

<script>
  import json from '@/assets/dummy-Article.json';
    export default {
        name: "Ref",
      data() {
          return{
            json,
            callRef: json.refs,
            callSota: json.sotas
          };
      },
    }
</script>

<style>

</style>
