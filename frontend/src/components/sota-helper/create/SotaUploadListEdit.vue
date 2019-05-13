<template>
  <!-- <md-card class="md-elevation-19">
    <md-card-header>
      <md-card-header-text>
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
  </md-card-header>-->
  <article>
    <div class="md-title">{{current['type']}} - {{current['reference']}}</div>
    <mdcontent>
      <form class="md-layout md-gutter" novalidate @submit.prevent="validateArticle">
        <!-- Title -->
        <div class="md-layout-item md-size-100">
          <md-field>
            <label for="upload-title">Title</label>
            <md-input name="title" id="upload-title" v-model.lazy.trim="current.title"/>
          </md-field>
        </div>

        <!-- Journal && Volume -->
        <div class="md-layout-item md-size-100">
          <div class="md-layout md-gutter">
            <md-field class="md-layout-item md-size-60">
              <label for="upload-journal">Journal/Booktitle</label>
              <md-input name="journal" id="upload-journal" v-model.lazy.trim="current.journal"/>
            </md-field>
            <md-field class="md-layout-item md-size-10">
              <label for="upload-volume">Volume</label>
              <md-input name="volume" id="upload-volume" v-model.lazy.trim="current.volume"/>
            </md-field>

            <md-field class="md-layout-item md-size-10">
              <label for="upload-pages">{{current.pages.includes('-') ? "Pages" : "Nb Page"}}</label>
              <md-input name="pages" id="upload-pages" v-model.lazy.trim="current.pages"/>
            </md-field>
          </div>
        </div>

        <!-- Publisher && Year && Month -->
        <div
          class="md-layout-item md-size-100 md-layout md-gutter md-alignment-center-space-around"
        >
          <md-field class="md-layout-item md-size-45">
            <label for="upload-publisher">Publisher</label>
            <md-input name="publisher" id="upload-publisher" v-model.lazy.trim="current.publisher"/>
          </md-field>
          <md-field class="md-layout-item">
            <label for="upload-year">Year</label>
            <md-input name="year" id="upload-year" v-model.lazy.trim="current.year"/>
          </md-field>
          <md-field class="md-layout-item">
            <label for="upload-month">Month</label>
            <md-input name="month" id="upload-month" v-model.lazy.trim="current.month"/>
          </md-field>
        </div>

        <!-- Category && Source URL -->
        <div class="md-layout-item md-size-100 md-layout md-gutter">
          <md-field class="md-layout-item">
            <label for="upload-category">Category</label>
            <md-input name="category" id="upload-category" v-model.lazy.trim="current.category"/>
          </md-field>
          <md-field class="md-layout-item md-size-70">
            <label for="upload-url">Source url</label>
            <md-input name="url" id="upload-url" v-model.lazy.trim="current.url"/>
          </md-field>
        </div>

        <div class="md-layout-item md-size-100">
          <md-field>
            <label for="keywords">Keywords</label>
            <md-input name="keywords" id="upload-keywords" v-model.lazy.trim="current.keywords"/>
          </md-field>
        </div>
        <div class="md-layout-item md-size-100">
          <md-field>
            <label for="upload-abstract">Abstract</label>
            <md-textarea
              id="upload-abstract"
              name="abstract"
              v-model.lazy.trim="current.abstract"
              md-autogrow
            ></md-textarea>
          </md-field>
        </div>
      </form>
    </mdcontent>
  </article>

  <!-- <md-card-actions>
      <md-button>
        <md-icon>keyboard_arrow_left</md-icon>
      </md-button>
      <md-button>
        <md-icon>keyboard_arrow_right</md-icon>
      </md-button>
    </md-card-actions>
  </md-card>-->
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
      current: {}
    };
  },
  computed: {},
  created() {
    this.current = this.list[0];
  },
  methods: {}
};
</script>

<style scoped>
</style>
