<template>
    <div class="infoNav">
      <div>
        <md-tabs>
          <md-tab id="tab-info" md-label="Info"> <Info/> </md-tab>

          <md-tab id="tab-ref" md-label="Reference"> <tag/> </md-tab>

          <md-tab id="tab-tag" md-label="Tag"> <Ref/> </md-tab>
        </md-tabs>
      </div>
    </div>
</template>

<script>
  import Info from '@/components/article/Info';
  import Tag from '@/components/article/Ref';
  import Ref from '@/components/article/Tag';

    export default {
        name: "InfoNav",
      components: {
        Info,
        Tag,
        Ref
      }
    }
</script>

<style scoped>

</style>
