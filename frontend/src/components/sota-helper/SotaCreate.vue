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
          @select="preview = articlesUploaded[upload].bibtex"
          @remove="onRemoveUpload"
          @edit="onEditUpload"
        />
      </md-list>

      <div>
        <ul>
          <li v-for="(error, index) in apiErrors" :key="index">
            <span class="md-error">{{error}}</span>
          </li>
        </ul>
      </div>
    </form>

    <!-- Import bibtex field -->
    <div class="md-layout-item md-size" id="bibtex-preview-container">
      <md-card>
        <md-card-header>
          <md-card-header-text>
            <div class="md-title" title="Only the 3 first articles">Preview of processed .bibtex</div>
            <div
              class="md-subhead"
              v-if="preview"
            >Contains {{ preview.length | pluralize('article')}}</div>
          </md-card-header-text>
          <md-menu md-size="big" md-direction="bottom-end">
            <md-button class="md-icon-button" md-menu-trigger>
              <md-icon>more_vert</md-icon>
            </md-button>

            <md-menu-content>
              <md-menu-item @click="onEditUpload">
                <span>Edit</span>
                <md-icon>create</md-icon>
              </md-menu-item>

              <md-menu-item @click="onRemoveUpload">
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

export default {
  name: "SotaCreate",
  components: { SotaUploadListItem },
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
      preview: null,
      selected: {
        filename: null,
        upload: null
      },
      showAcceptMessage: false,
      showCreatedMessage: false,
      showEditUpload: false
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
      return this.preview.slice(0, 3);
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
          this.preview = bib2Json;
          this.$set(this.articlesUploaded, evt.name, {
            name: evt.name,
            size: evt.size,
            bibtex: bib2Json
          });
        };
        reader.readAsText(evt);
      }
    },

    onRemoveUpload(filename) {
      delete this.articlesUploaded[filename];
    },
    onEditUpload(filename) {
      this.selected.filename = filename;
      this.selected.upload = this.articlesUploaded[filename];
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
