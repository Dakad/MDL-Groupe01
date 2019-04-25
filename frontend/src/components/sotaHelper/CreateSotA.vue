<template>
    <div class="container">
      <div class="left">
        <h4>Name of the SotA :</h4>
        <md-field>
          <label>Type here!</label>
          <md-input v-model="type"></md-input>
          <span class="md-helper-text">Helper text</span>
        </md-field>

        <h4>Main subject :</h4>
        <md-field>
          <label>Type here!</label>
          <md-input v-model="type"></md-input>
          <span class="md-helper-text">Helper text</span>
        </md-field>

        <h4>Date of the state of the art :</h4>
        <md-field>
          <label>Type here!</label>
          <md-input v-model="type"></md-input>
          <span class="md-helper-text">Helper text</span>
        </md-field>

        <h4>Authors :</h4>
        <md-field>
          <label>Type here!</label>
          <md-input v-model="type"></md-input>
          <span class="md-helper-text">Helper text</span>
        </md-field>

        <h4>Tags :</h4>
        <md-field>
          <label>Tags</label>
          <md-textarea v-model="autogrow" md-autogrow></md-textarea>
          <span class="md-helper-text">separate each tags with a coma</span>
        </md-field>
      </div>
      <div class="right">
        <h4>Import bibtex file</h4>
          <md-field>
            <label>Only .bibtext accepted</label>
            <md-file v-model="multiple" multiple accept=".bibtex" />
          </md-field>
      </div>
      <div class="bottom">

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
    border: grey solid 3px;
  }

  .left{
    position: relative;
    float: left;
    width: 47%;
    height: 75%;
    margin: 1%;
    border: green solid 3px;
  }

  .right{
    position: relative;
    float: left;
    width: 47%;
    height: 75%;
    margin: 1%;
    border: red solid 3px;
  }

  .bottom{
    position: relative;
    width: 99%;
    height: 9%;
    float: bottom;
    border: blue solid 3px;
  }





</style>
