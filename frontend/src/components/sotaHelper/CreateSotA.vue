<template>
    <div class="container">
      <div class="left">
        <md-field>
          <label>Name of the SotA</label>
          <md-input v-model="type"></md-input>
          <span class="md-helper-text">Helper text</span>
        </md-field>
        <md-field>
          <label>Main subject</label>
          <md-input v-model="type"></md-input>
          <span class="md-helper-text">Helper text</span>
        </md-field>
        <md-field>
          <label>Date of the state of the art</label>
          <md-input v-model="type"></md-input>
          <span class="md-helper-text">Helper text</span>
        </md-field>
        <md-field>
          <label>Authors</label>
          <md-input v-model="type"></md-input>
          <span class="md-helper-text">Helper text</span>
        </md-field>

        <md-field>
          <label>Tags</label>
          <md-textarea v-model="autogrow" md-autogrow></md-textarea>
          <span class="md-helper-text">separate each tags with a coma</span>
        </md-field>
      </div>
      <div class="right">
          <md-field>
            <label>Import bibtex file</label>
            <md-file v-model="multiple" multiple accept=".bibtex" />
          </md-field>
      </div>
      <div class="bottom">
        <b-button size="lg" variant="outline-info" @click="showAcceptMessage = true">UPLOAD THE SotA</b-button>
      </div>
    </div>
</template>

<script>
    export default {
        name: "CreateSotA",
      data: () => ({
        initial: 'Initial Value',
        type: null,
        withLabel: null,
        inline: null,
        number: null,
        textarea: null,
        autogrow: null,
        disabled: null,
        multiple: null
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
