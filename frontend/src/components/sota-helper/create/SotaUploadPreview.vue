<template>
  <md-card>
    <md-card-header>
      <md-card-header-text>
        <div class="md-title" title="Only the 3 first articles">Preview of processed .bibtex</div>
        <div class="md-subhead" v-if="preview">Contains {{ preview.length | pluralize('article')}}</div>
      </md-card-header-text>
      <md-menu md-size="big" md-direction="bottom-end">
        <md-button class="md-icon-button" md-menu-trigger>
          <md-icon>more_vert</md-icon>
        </md-button>

        <md-menu-content>
          <md-menu-item @click="onEditPreviewClick()">
            <span>Edit</span>
            <md-icon>create</md-icon>
          </md-menu-item>

          <md-menu-item @click="onRemovePreviewClick()">
            <span>Remove</span>
            <md-icon>clear</md-icon>
          </md-menu-item>
        </md-menu-content>
      </md-menu>
    </md-card-header>

    <md-card-content v-if="preview" class="md-scrollbar">
      <!-- id="bibtex-preview" -->
      <pre id="bibtex-preview">{{preview.slice(0,2)}}</pre>
      <b v-if="preview.length > 3" class="md-subhead">&hellip; {{preview.length - 3}} more</b>
    </md-card-content>

    <!-- <md-card-actions>
          <md-button :md-ripple="false" class="md-primary">Edit</md-button>
          <md-button :md-ripple="false" class="md-raised md-accent">Remove</md-button>
    </md-card-actions>-->
  </md-card>
</template>

<script>
export default {
  name: "SotaUploadPreview",
  props: {
    preview: {
      required: true,
      default: () => []
    }
  },
  methods: {
    onDetailsClick() {
      this.$emit("select");
    },
    onEditPreviewClick() {
      this.$emit("edit");
    },
    onRemovePreviewClick() {
      this.$emit("remove");
    }
  }
};
</script>

<style scoped>
#bibtex-preview {
  white-space: pre-wrap;
  width: auto;
  max-height: 375px;
  overflow-y: hidden;
}

#bibtex-preview:hover {
  overflow-y: visible;
}
</style>
