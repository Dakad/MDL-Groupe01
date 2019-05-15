<template>
  <div>
    <form novalidate @submit.prevent="validateSota">
      <h2 class="create-sota-heading">Create a new SoTA via upload</h2>
      <br>
      <md-field :class="[getValidationClass('title'), {'md-invalid': invalid['title'] != null}]">
        <md-icon>event</md-icon>
        <label for="title">
          Title of the SotA
          <span class="md-subheading">(REQUIRED)</span>
        </label>
        <md-input name="title" id="title" v-model.lazy.trim="sota.title" :disabled="disabled"></md-input>
        <span class="md-helper-text"></span>
        <span class="md-error" v-if="!$v.sota.title.required">The title is required</span>
        <span class="md-error" v-if="!$v.sota.title.minLength">At least, 5 characters</span>
        <!-- <span class="md-error" v-if="!$v.sota.title.maxLength">250 characters should be enough</span> -->
        <span class="md-error" v-if="invalid['title'] != null">{{invalid['title']}}</span>
      </md-field>

      <md-field
        :class="[getValidationClass('subject'), {'md-invalid': invalid['subject'] != null}]"
      >
        <md-icon>event</md-icon>
        <label for="subject">Main subject</label>
        <md-input name="subject" id="subject" v-model.lazy.trim="sota.subject"/>
        <span class="md-error" v-if="!$v.sota.subject.required">The subject is required</span>
        <span class="md-error" v-else-if="!$v.sota.subject.minLength">At least, 3 characters</span>
        <span
          class="md-error"
          v-else-if="!$v.sota.subject.sameAsTitle"
        >Cannot be the same as the title</span>
        <span class="md-error" v-else-if="invalid['subject'] != null">{{invalid['subject']}}</span>
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
        <md-file multiple accept=".bib, .bibtex" v-model="uploadList" @md-change="onUploadFiles"/>
      </md-field>

      <!-- Upload btn -->
      <div class id="upload-btn-container">
        <md-button class="md-raised md-primary" type="submit" :md-ripple="false">Upload the SoTA</md-button>
        <md-button class="md-raised md-accent" :md-ripple="false" @click="clearForm">Clear uploads</md-button>
      </div>

      <slot></slot>
    </form>
    <!-- Dialog box to confirm Sota creation -->
    <md-dialog-confirm
      class="sota-create-dialog"
      :md-active.sync="showConfirmDialog"
      md-title="Confirm this sota creation ?!"
      md-content="&nbsp;Do you really want to upload this SoTA ?"
      md-confirm-text="Agree"
      md-cancel-text="No, return"
      @md-cancel="showConfirmDialog = false"
      @md-confirm="onConfirmSubmit()"
    />
    <md-dialog-alert md-title="SoTA created!" md-content="Your SoTA has been uploaded."/>
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

export default {
  name: "SotaCreateForm",
  mixins: [validationMixin],

  props: {
    disabled: Boolean
  },
  data() {
    return {
      sota: {
        title: "",
        keywords: "",
        subject: ""
      },
      uploadList: null,
      invalid: {},
      showConfirmDialog: false
    };
  },
  validations: {
    sota: {
      title: {
        required,
        minLength: minLength(5)
        // maxLength: maxLength(255)
      },
      subject: {
        required,
        minLength: minLength(3),
        sameAsTitle: not(sameAs("title"))
      }
    }
  },
  computed: {},
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
      this.sota = {
        title: "",
        keywords: "",
        subject: ""
      };
      this.uploadList = null;
      this.$emit("clear");
    },
    validateSota() {
      this.$v.$touch();
      if (!this.$v.$invalid) {
        this.invalid = {};
        this.showConfirmDialog = true;
      }
    },
    onUploadFiles(files) {
      this.$emit("upload", files);
    },
    onConfirmSubmit() {
      this.$emit("submit", this.sota);
    }
  }
};
</script>

<style scoped>
#upload-btn-container {
  text-align: center;
  margin: 15px;
}
</style>
