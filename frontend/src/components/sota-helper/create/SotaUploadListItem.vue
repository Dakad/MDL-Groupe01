<template>
  <md-list-item class="md-layout md-gutter">
    <!-- <md-checkbox v-model="selected" class="md-layout-item md-primary md-size-5" :value="item.name"/> -->
    <span class="md-layout-item upload-details" :class="{selected}" @click="onDetailsClick()">
      <span class="upload-name">{{item.name}}</span>
      &#x7C;
      <span class="upload-size" :title="item.size + ' Bytes'">{{item.size | sizeHuman }}</span>
    </span>

    <code class="md-layout-item md-size-15 upload-actions">
      <md-button
        class="md-icon-button md-dense md-primary"
        title="Add more info about this bibtex"
        @click="onEditBtnClick()"
      >
        <md-icon>create</md-icon>
      </md-button>
      <md-button
        class="md-icon-button md-dense md-accent"
        title="Remove this SoTA"
        @click="onRemoveBtnClick()"
      >
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
      required: true,
      default: () => {}
    },
    selected: {
      type: Boolean,
      default: () => true
    }
  },
  computed: {},
  methods: {
    onDetailsClick() {
      this.$emit("select", this.item.name);
    },
    onEditBtnClick() {
      this.$emit("edit", this.item.name);
    },
    onRemoveBtnClick() {
      this.$emit("remove", this.item.name);
    }
  }
};
</script>

<style scoped>
.upload-details {
  cursor: pointer;
  padding-left: 5%;
}

.upload-details.selected {
  font-weight: bold;
}
</style>
