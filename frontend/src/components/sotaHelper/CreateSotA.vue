<template>
    <div class="container">
      <div class="left">
        <md-field>
          <label>Name of the SotA</label>
          <md-input v-model="sotAName"></md-input>
          <span class="md-helper-text">Helper text</span>
        </md-field>
        <md-field>
          <label>Main subject</label>
          <md-input v-model="domain"></md-input>
          <span class="md-helper-text">Helper text</span>
        </md-field>
        <md-field>
          <label>Date of the state of the art</label>
          <md-input v-model="year"></md-input>
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
      <div class="right">
          <md-field>
            <label>Import bibtex file</label>
            <md-file v-model="bibTex" multiple accept=".bibtex" />
          </md-field>
      </div>
      <div class="bottom">
        <b-button size="lg" variant="outline-info" @click="showAcceptMessage = true">UPLOAD THE SotA</b-button>
      </div>

      <md-dialog class="login-dialog" :md-active.sync="showAcceptMessage">
        <md-dialog-title>
          &nbsp;Are you sure you want to upload this SotA
        </md-dialog-title>
        <login @error="handleError('login', $event)" @success="handleSuccess('login',$event)"/>
      </md-dialog>

    </div>
</template>

<script>
    export default {
        name: "CreateSotA",
      data: () => ({
        initial: 'Initial Value',
        sotAName: null,
        withLabel: null,
        inline: null,
        number: null,
        textarea: null,
        autogrow: null,
        disabled: null,
        domain: null,
        year: null,
        author: null,
        tags: null,
        bibTex: null,
        showAcceptMessage: false
      })
    }
</script>

<style scoped>
  .container{
    position: absolute;
    width: 100%;
    height: 100%;
  }

  .left{
    position: relative;
    float: left;
    width: 47%;
    height: 75%;
    margin: 1%;
  }

  .right{
    position: relative;
    float: left;
    width: 47%;
    height: 75%;
    margin: 1%;

  }

  .bottom{
    position: relative;
    width: 99%;
    height: 9%;
    float: bottom;
    padding-left: 40%;
  }
</style>
