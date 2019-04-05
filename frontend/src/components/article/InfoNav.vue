<template>
  <div class="infoNav">
    <div>
      <md-tabs>
        <md-tab id="tab-info" md-label="Info">
          <Info :info="info"/>
        </md-tab>

        <md-tab id="tab-ref" md-label="Reference">
          <Tag :tags="tags"/>
        </md-tab>

        <md-tab id="tab-tag" md-label="Tag">
          <Ref :refs="refs"/>
        </md-tab>
      </md-tabs>
    </div>
  </div>
</template>

<script>
import Info from "@/components/article/Info";
import Tag from "@/components/article/Ref";
import Ref from "@/components/article/Tag";

export default {
  name: "InfoNav",
  props: {
    info: Array,
    tags: Array,
    refs: Array
  },
  components: {
    Info,
    Tag,
    Ref
  }
};
</script>

<style scoped>
</style>
