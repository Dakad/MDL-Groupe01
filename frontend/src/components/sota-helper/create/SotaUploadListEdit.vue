<template>
  <md-card class="md-elevation-19">
    <md-card-header>
      <md-card-header-text>
        <div class="md-title">Edit bibtex #REFERENCE</div>
      </md-card-header-text>

      <md-menu md-size="big" md-direction="bottom-end">
        <md-button class="md-icon-button" md-menu-trigger>
          <md-icon>more_vert</md-icon>
        </md-button>

        <md-menu-content>
          <md-menu-item title="Multiple edits">
            <span>Bulk edit</span>
            <md-icon>ballot</md-icon>
          </md-menu-item>
        </md-menu-content>
      </md-menu>
    </md-card-header>

    <md-card-content>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Eligendi, quos. Dolore</md-card-content>

    <md-card-actions>
      <md-button>
        <md-icon>keyboard_arrow_left</md-icon>
      </md-button>
      <md-button>
        <md-icon>keyboard_arrow_right</md-icon>
      </md-button>
    </md-card-actions>
  </md-card>
</template>

<script>
export default {
  name: "SotaUploadListEdit",
  props: {
    list: {
      required: true,
      default: () => []
    }
  },
  data() {
    return {
      current: null
    };
  },
  computed: {},
  methods: {}
};
</script>

<style scoped>
</style>
