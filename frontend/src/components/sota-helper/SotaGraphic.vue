<template>
  <div class="graph-container">
    <tree class="tree" type="cluster" layout-type="circular" node-text="name" :data="data"/>
  </div>
</template>


<script>
import { tree } from "vued3tree";
import dummy from "@/services/dummy/sota-helper-visu.json";

export default {
  name: "SotaGraphic",
  components: { tree },
  props: [],
  data() {
    return {
      width: "100%",
      height: "auto",
      selected: null,
      data: null
    };
  },
  created() {
    this.data = dummy;
  },
  computed: {},
  methods: {
    select: function(node) {
      this.selected = node;
    }
  }
};
</script>

<style lang="scss" scoped>
.tree {
  height: 600px;
  width: 100%;
}
</style>
