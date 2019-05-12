<template>
  <md-list-item class="md-layout md-gutter">
    <!-- <md-checkbox v-model="selected" class="md-layout-item md-primary md-size-5" :value="item.name"/> -->
    <span class="md-layout-item upload-details" @click="alert">
      <span class="upload-name">{{item.name}}</span>
      &#x7C;
      <span class="upload-size">{{item.size | sizeHuman }}</span>
    </span>

    <code class="md-layout-item md-size-15 upload-actions">
      <md-button class="md-icon-button md-dense md-primary" title="Add more info about this bibtex">
        <md-icon>create</md-icon>
      </md-button>
      <md-button class="md-icon-button md-dense md-accent" title="Remove this SoTA">
        <md-icon>clear</md-icon>
      </md-button>
    </code>
  </md-list-item>
</template>

<script>
export default {
  name: "SotaUploadListItem",
  props: {
    item: {
      type: Object,
      required: true,
      default: () => {}
    }
  },
  computed: {},
  methods: {
    alert() {
      window.alert("Yep");
    }
  }
};
</script>

<style scoped>
.upload-details {
  cursor: pointer;
}
</style>
