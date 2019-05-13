<template>
<div class="infoNav">
    <div v-if="info">
      <md-tabs md-alignment="fixed">
        <md-tab id="tab-info" md-label="Info" md-disabled>
          <Info :info="info"/>
        </md-tab>
      </md-tabs>
    </div>
    <div v-else>
      <md-tabs md-alignment="fixed">
        <md-tab id="tab-ref" md-label="References" md-disabled>
          <Ref :refs="refs"/>
        </md-tab>
      </md-tabs>
    </div>
  </div>
</template>

<script>
import Info from "@/components/article/Info";
import Ref from "@/components/article/Ref";

export default {
  name: "InfoNav",
  props: {
    info: Object,
    refs: Array
  },
  components: {
    Info,
    Ref
  }
};
</script>

<style scoped>
</style>
