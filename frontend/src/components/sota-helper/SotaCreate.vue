<template>
  <div class="app-sota-create md-layout md-gutter md-alignment-top-space-between">
    <form class="md-layout-item md-size-45" novalidate @submit.prevent="validateSota">
      <h2 class="create-sota-heading">Create a new SoTA via upload</h2>
      <br>
      <md-field :class="[getValidationClass('title'), {'md-invalid': invalid['title'] != null}]">
        <md-icon>event</md-icon>
        <label for="title">
          Title of the SotA
          <span class="md-subheading">(REQUIRED)</span>
        </label>
        <md-input name="title" id="title" v-model.lazy.trim="sota.title" :disabled="sending"></md-input>
        <span class="md-helper-text"></span>
        <span class="md-error" v-if="!$v.sota.title.required">The title is required</span>
        <span class="md-error" v-if="!$v.sota.title.minLength">At least, 5 characters</span>
        <span class="md-error" v-if="!$v.sota.title.maxLength">250 characters should be enough</span>
        <span class="md-error" v-if="invalid['title'] != null">{{invalid['title']}}</span>
      </md-field>

      <md-field
        :class="[getValidationClass('subject'), {'md-invalid': invalid['subject'] != null}]"
      >
        <md-icon>event</md-icon>
        <label for="subject">Main subject</label>
        <md-input name="subject" id="subject" v-model.lazy.trim="sota.subject"/>
        <span class="md-error" v-if="!$v.sota.subject.required">The subject is required</span>
        <span class="md-error" v-if="!$v.sota.subject.minLength">At least, 5 characters</span>
        <span class="md-error" v-if="!$v.sota.subject.sameAsTitle">Cannot be the same as the title</span>
        <span class="md-error" v-if="invalid['subject'] != null">{{invalid['subject']}}</span>
      </md-field>

      <md-field>
        <md-icon>event</md-icon>
        <label>Keywords</label>
        <md-textarea v-model.lazy.trim="sota.keywords" md-autogrow></md-textarea>
        <span class="md-helper-text">
          Separate each tags with a coma
          <!-- <strong class="md-headline">,</strong> -->
        </span>
      </md-field>
      <br>
      <md-field>
        <label>Import bibtex file</label>
        <md-file multiple accept=".bib, .bibtex" @md-change="onFileUpload($event)"/>
      </md-field>

      <!-- Upload btn -->
      <div class id="upload-btn-container">
        <md-button class="md-raised md-primary" type="submit" :md-ripple="false">Upload the SoTA</md-button>
        <md-button class="md-raised md-accent" :md-ripple="false">Clear uploads</md-button>
      </div>

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
    </form>

    <!-- Import bibtex field -->
    <div class="md-layout-item md-size" id="bibtex-preview-container">
      <sota-upload-preview
        :preview="previewJson"
        @remove="onRemoveUpload()"
        @edit="showUploadEdit = true"
      ></sota-upload-preview>
    </div>

    <!-- Dialog box to confirm Sota creation -->
    <md-dialog-confirm
      class="sota-create-dialog"
      :md-active.sync="showAcceptMessage"
      md-title="Confirm this sota creation ?!"
      md-content="&nbsp;Do you really want to upload this SoTA ?"
      md-confirm-text="Agree"
      md-cancel-text="No, return"
      @md-cancel="showAcceptMessage = false"
      @md-confirm="sendSota"
    />
    <md-dialog-alert md-title="SoTA created!" md-content="Your SoTA has been uploaded."/>

    <!-- Dialog box for created SoTA -->
    <md-dialog :md-active.sync="showCreatedMessage">
      <md-dialog-title>SoTA created</md-dialog-title>
      <md-dialog-actions>
        <md-button class="md-primary" @click="showCreatedMessage = false">Okay</md-button>
      </md-dialog-actions>
    </md-dialog>
  </div>
</template>

<script>
import { validationMixin } from "vuelidate";
import {
  required,
  minLength,
  maxLength,
  not,
  sameAs
} from "vuelidate/lib/validators";

import { createSota } from "../../services/api-sota";
import { createArticle } from "@/services/api-article";
import { parse as bibParser } from "@/services/bibtex-parse";

import SotaUploadListItem from "@/components/sota-helper/SotaUploadListItem";
import SotaUploadPreview from "@/components/sota-helper/SotaUploadPreview";

export default {
  name: "SotaCreate",
  components: { SotaUploadListItem, SotaUploadPreview },
  mixins: [validationMixin],
  data() {
    return {
      sota: {
        title: "",
        keywords: "",
        subject: ""
      },
      sending: false,
      invalid: {},
      apiErrors: [],
      articlesUploaded: {},
      selected: {
        filename: null,
        upload: null
      },
      showAcceptMessage: false,
      showCreatedMessage: false,
      showUploadEdit: false
    };
  },
  validations: {
    sota: {
      title: {
        required,
        minLength: minLength(5),
        maxLength: maxLength(255)
      },
      subject: {
        required,
        minLength: minLength(5),
        sameAsTitle: not(sameAs("title"))
      }
    }
  },

  computed: {
    listAuthors() {
      return [
        { name: "Aqua", color: "#00ffff" },
        { name: "Olive", color: "#808000" },
        { name: "Orange", color: "#ffa500" },
        { name: "Orange Red", color: "#ff4500" },
        { name: "Pale Golden Rod", color: "#eee8aa" },
        { name: "Pale Green", color: "#98fb98" },
        { name: "Teal", color: "#008080" },
        { name: "Turquoise", color: "#40e0d0" },
        { name: "Violet", color: "#ee82ee" },
        { name: "White", color: "#ffffff" },
        { name: "Yellow", color: "#ffff00" }
      ];
    },
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
    getValidationClass(fieldName) {
      const field = this.$v.sota[fieldName];
      if (field) {
        return {
          "md-invalid": field.$invalid && field.$dirty
        };
      }
    },
    clearForm() {
      this.$v.$reset();
      this.sota.title = null;
      this.sota.domain = null;
    },
    validateSota() {
      this.$v.$touch();
      if (!this.$v.$invalid) {
        this.invalid = {};
        this.showAcceptMessage = true;
      }
    },

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
      this.selected["upload"] = this.articlesUploaded[filename]["bibtex"];
    },

    onRemoveUpload(filename) {
      if (!filename) {
        filename = this.selected["filename"];
      }
      if (this.selected["filename"] == filename) {
        this.selected = {
          filename: null,
          upload: null
        };
      }
      this.$delete(this.articlesUploaded, filename);
    },
    onEditUpload(filename) {
      this.selected.filename = filename;
      this.selected.upload = this.articlesUploaded[filename];
      this.showUploadEdit = true;
      window.alert("Edit me : " + filename);
    },

    sendSota() {
      this.apiErrors = [];
      const articleRefs = [];
      const createArticleRequests = this.articlesUploaded.map(article => {
        articleRefs.push(article["reference"]);
        // If the article category is missing, assign the one from the Sota
        if (!article["category"] || article["category"].length == 0) {
          article["category"] = this.sota["subject"];
        }
        return createArticle(article);
      });

      // Send all request to create an article, fail
      Promise.race(createArticleRequests)
        .then(values => {
          // Split the keywords, to get each keywords
          this.sota["keywords"] = this.sota.keywords
            .split(",")
            .map(k => k.trim())
            .filter(k => k.length > 0);

          this.sota["articles"] = articleRefs;

          return createSota(this.sota).then(data => {});
        })
        .catch(err => {
          console.log(err);
          this.apiErrors.push(err.body["message"]);
        });
    }
  }
};
</script>

<style scoped>
.app-sota-create {
  padding: 2%;
}

#upload-btn-container {
  text-align: center;
  margin: 15px;
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
</style>
