<template>
  <div class>
    <form
      class="md-layout md-gutter md-alignment-center-space-between"
      novalidate
      @submit.prevent="validateSota"
    >
      <div class="md-layout-item md-size-100 md-layout md-gutter">
        <div class="md-layout-item">
          <h2 class="create-sota-heading">Create a new SoTA via an upload</h2>
          <br>
          <md-field
            :class="[getValidationClass('title'), {'md-invalid': invalid['title'] != null}]"
          >
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

          <md-field>
            <md-icon>event</md-icon>
            <label>Main subject</label>
            <md-input v-model.lazy.trim="sota.subject"/>
            <!-- <span class="md-helper-text">Helper text</span> -->
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
            <md-file
              multiple
              accept=".bib, .bibtex"
              v-model="uploads"
              @md-change="onFileUpload($event)"
            />
          </md-field>

          <!-- Upload btn -->
          <div class="md-layout-item md-size-100" id="upload-btn-container">
            <md-button class="md-raised md-primary" type="submit" :md-ripple="false">Upload the SoTA</md-button>
          </div>
        </div>

        <!-- Import bibtex field -->
        <div class="md-layout-item md-size-45">
          <h3 class="md-title">Preview of processed .bibtex</h3>
          <md-card>
            <md-content id="bibtex-preview">
              <pre>{{preview.json}}</pre>
            </md-content>
          </md-card>
        </div>
      </div>
    </form>

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

export default {
  name: "SotaCreate",
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
      uploads: null,
      articlesUploaded: [],
      preview: {
        json: null,
        bibtex: null
      },
      showAcceptMessage: false,
      showCreatedMessage: false
    };
  },
  validations: {
    sota: {
      title: {
        required,
        minLength: minLength(5),
        maxLength: maxLength(255)
      },
      domain: {
        required,
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
      const reader = new FileReader();

      reader.onload = e => {
        // this.bibtex.push(e.target.result);
        this.preview["bibtex"] = e.target.result;
        const bib2Json = bibParser(e.target.result);
        this.preview["json"] = bib2Json;
        this.articlesUploaded = this.articlesUploaded.concat(bib2Json);
        console.log(this.articlesUploaded);
      };
      reader.readAsText(event.item(0));
    },

    sendSota() {
      let articlesArray = {};
      console.log(this.articlesUploaded);

      // TODO Create an api-article.js to create and get an article

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

          createSota(this.sota).then(console.error);
        })
        .catch(console.error);
    }
  }
};
</script>

<style scoped>
#upload-btn-container {
  text-align: center;
  margin: 40px;
}

#bibtex-preview {
  padding: 10px;
  height: 90%;
  max-height: 450px;
  white-space: pre-wrap;
  font-size: 13px;
  /* border: 3px solid gray; */
  overflow-y: hidden;
}

#bibtex-preview:hover {
  overflow-y: visible;
}
</style>
