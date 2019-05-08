<template>
  <div class="md-layout md-gutter">
    <div class="md-layout-item">
      <md-field>
        <label>Name of the SotA</label>
        <md-input v-model="sotaName"></md-input>
        <span class="md-helper-text">Helper text</span>
      </md-field>
      <md-field>
        <label>Main subject</label>
        <md-input v-model="domain"></md-input>
        <span class="md-helper-text">Helper text</span>
      </md-field>
      <md-field>
        <label>Authors</label>
        <md-input v-model="author"></md-input>
        <span class="md-helper-text">Helper text</span>
      </md-field>

      <md-field>
        <label>Tags</label>
        <md-textarea v-model="tags" md-autogrow></md-textarea>
        <span class="md-helper-text">separate each tags with a coma</span>
      </md-field>
    </div>
    <div class="md-layout-item">
      <md-field>
        <label>Import bibtex file</label>
        <md-file
          multiple
          accept=".bib, .bibtex"
          v-model="uploads"
          @md-change="onFileUpload($event)"
        />
      </md-field>
    </div>
    <div class="bottom">
      <b-button size="lg" variant="outline-info" @click="showAcceptMessage = true">UPLOAD THE SotA</b-button>
    </div>
    <!--
    <md-dialog class="login-dialog" :md-active.sync="showAcceptMessage">
      <md-dialog-title>
        &nbsp;Are you sure you want to upload this SotA
        <div class="bottom-acc">
          <b-button size="lg" variant="outline-info" @click="sendSota()">Yes</b-button>
          <b-button size="lg" variant="outline-info" @click="showAcceptMessage = false">No, return</b-button>
        </div>
      </md-dialog-title>
    </md-dialog>-->
  </div>
</template>

<script>
import { createSota } from "../../services/api-sota";
import { parse as bibParser } from "@/services/bibtex-parse";

export default {
  name: "CreateSota",
  data() {
    return {
      sotaName: null,
      domain: null,
      author: null,
      tags: null,
      uploads: null,
      articlesUploaded: [],
      showAcceptMessage: false
    };
  },

  methods: {
    onFileUpload(event) {
      const reader = new FileReader();

      reader.onload = e => {
        // this.bibtex.push(e.target.result);
        const bib2Json = bibParser(e.target.result);
        window.alert(bib2Json);
        this.articlesUploaded = this.articlesUploaded.concat(bib2Json);
      };
      reader.readAsText(event.item(0));
    },

    sendSota() {
      let articlesArray = {};
      console.log(this.articlesUploaded);

      // TODO Create an api-article.js to create and get an article

      const articeRefs = []
      const createArticleRequests = this.articlesUploaded.map(article => {
        articleRefs.push(article['reference']);
        return createArticle(article)
      });

      // Send all request to create an article, fail
      Promise.race(createArticleRequests).then(values => {
        console.log(values);

      }).catch(console.error);


      // TODO Send an API call to create the new SoTA
    }
  }
};
</script>

<style scoped>
.container {
  position: absolute;
  width: 100%;
  height: 100%;
}

.left {
  position: relative;
  float: left;
  width: 47%;
  height: 75%;
  margin: 1%;
}

.right {
  position: relative;
  float: left;
  width: 47%;
  height: 75%;
  margin: 1%;
}

.bottom {
  position: relative;
  width: 99%;
  height: 9%;
  float: bottom;
  padding-left: 40%;
}
</style>
