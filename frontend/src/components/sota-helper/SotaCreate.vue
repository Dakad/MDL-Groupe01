<template>
  <div class="app-sota-create md-layout md-gutter md-alignment-top-space-between">
    <sota-create-form
      class="md-layout-item md-size-45"
      @upload="onFileUpload"
      @submit="sendSota"
      @clear="onClearForm"
    >
      <hr v-if="uploads.length">

      <md-list class="upload-file-list md-scrollbar">
        <sota-upload-list-item
          v-for="upload in uploads"
          :key="upload"
          :item="articlesUploaded[upload]"
          :selected="upload == selected['filename']"
          @select="onSelectUpload"
          @remove="onRemoveUpload"
          @edit="onEditUpload"
        />
      </md-list>

      <div>
        <ul>
          <li v-for="(error, index) in apiErrors" :key="index">
            <span class="md-error">#{{index}} - {{error}}</span>
          </li>
        </ul>
      </div>
    </sota-create-form>

    <!-- Preview of the imported bibtex -->
    <div class="md-layout-item md-size" id="bibtex-preview-container">
      <sota-upload-preview
        :preview="previewJson"
        @remove="onRemoveUpload()"
        @edit="showUploadEdit = true"
      ></sota-upload-preview>
    </div>

    <!-- Dialog box to edit the parsed bibtex files -->
    <md-dialog :md-active.sync="showUploadEdit">
      <md-dialog-title>Editing {{selected['filename']}}</md-dialog-title>
      <md-dialog-content>
        <sota-upload-list-edit
          id="upload-list-edit"
          :filename="selected.filname"
          :list="selected.bibtex"
        />
      </md-dialog-content>
    </md-dialog>

    <!-- Dialog box for be redirect to created SoTA -->
    <md-dialog-confirm
      :md-active.sync="showRedirectDialog"
      md-title="SoTA created"
      md-content="Your <strong>SoTA</strong> has been created.\n Do you want to be redirect to it page"
      md-confirm-text="Okay"
      md-cancel-text="Disagree"
      @md-cancel="showRedirectDialog = false"
      @md-confirm="showRedirectDialog = false"
    />
  </div>
</template>

<script>
import { createArticle } from "@/services/api-article";
import { parse as bibParser } from "@/services/bibtex-parse";
import { EventBus, EVENT_APP_MESSAGE } from "@/services/event-bus";
import {
  SotaUploadListItem,
  SotaUploadPreview,
  SotaCreateForm,
  SotaUploadListEdit
} from "@/components/sota-helper/create";

export default {
  name: "SotaCreate",
  components: {
    SotaUploadListItem,
    SotaUploadPreview,
    SotaCreateForm,
    SotaUploadListEdit
  },
  data() {
    return {
      sending: false,
      apiErrors: [],
      articlesUploaded: {},
      selected: {
        filename: null,
        bibtex: null
      },
      showUploadEdit: false,
      showRedirectDialog: false
    };
  },

  computed: {
    uploads() {
      return Object.keys(this.articlesUploaded);
    },
    previewJson() {
      if (this.selected["filename"] == null) {
        return null;
      }
      const filename = this.selected["filename"];
      return this.articlesUploaded[filename]["bibtex"].slice(0, 3);
    }
  },

  methods: {
    onFileUpload(event) {
      for (let i = 0; i < event.length; i++) {
        const evt = event[i];

        const reader = new FileReader();
        reader.onload = e => {
          const bib2Json = bibParser(e.target.result);
          this.selected["filename"] = evt.name;
          this.$set(this.articlesUploaded, evt.name, {
            name: evt.name,
            size: evt.size,
            bibtex: bib2Json
          });
        };
        reader.readAsText(evt);
      }
    },

    onSelectUpload(filename) {
      this.selected["filename"] = filename;
      this.selected["bibtex"] = this.articlesUploaded[filename]["bibtex"];
    },

    onRemoveUpload(filename) {
      if (!filename) {
        filename = this.selected["filename"];
      }
      if (this.selected["filename"] == filename) {
        this.selected = {
          filename: null,
          bibtex: null
        };
      }
      this.$delete(this.articlesUploaded, filename);
    },
    onEditUpload(filename) {
      this.selected["filename"] = filename;
      this.selected["bibtex"] = this.articlesUploaded[filename]["bibtex"];
      this.showUploadEdit = true;
      // window.alert("Edit me : " + filename);
    },

    onClearForm() {
      // this.articlesUploaded = Object.assign({});
    },

    sendSota(sota) {
      this.apiErrors = [];
      const articleRefs = [];

      // if (this.uploads.length == 0) {
      //   EventBus.$emit(
      //     EVENT_APP_MESSAGE,
      //     "Import the bibtex files before create the SoTA."
      //   );
      //   return;
      // }
      const createArticleRequests = this.uploads.map(filename => {
        this.articlesUploaded[filename]["bibtex"].forEach(article => {
          // If the article category is missing, assign the one from the Sota
          if (!article["category"] || article["category"].length == 0) {
            article["category"] = sota["subject"];
          }
          return createArticle(article)
            .catch(e => this.apiErrors.push(err.body["message"]))
            .then(data => {
              console.log(data);
              articleRefs.push(data["reference"]);
            });
        });
      });

      // Send all request to create an article, fail
      Promise.all(createArticleRequests).then(values => {
        const newSota = Object.assign(sota, {
          articles: articleRefs,
          keywords: sota.keywords // Split the keywords, to get each keywords
            .split(",")
            .map(k => k.trim())
            .filter(k => k.length)
        });

        this.$emit("create", newSota);
      });
    }
  }
};
</script>

<style scoped>
.app-sota-create {
  padding: 2%;
}

.upload-file-list {
  max-height: 150px;
  /* max-width: 110%; */
  overflow: hidden;
}

.upload-file-list:hover {
  overflow-y: visible;
}

#bibtex-preview-container {
  max-width: 55%;
}

#upload-list-edit {
  width: 900px;
}
</style>
