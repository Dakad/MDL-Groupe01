<template>
    <div class="general">
      <div class="left">
        <div class="md-layout">
          <md-field>
            <label>Change your avatar</label>
            <md-input v-model="avatarimg"></md-input>
          </md-field>
          <md-field>
            <label>Change your University</label>
            <md-input v-model="university"></md-input>
          </md-field>
          <md-field>
            <label for="domain">Change your main research domain</label>
            <md-select v-model="domain" name="domain" id="domain">
              <md-option value="ComputerScience">Computer Science</md-option>
              <md-option value="Literature">Literature</md-option>
              <md-option value="Chemistry">Chemistry</md-option>
              <md-option value="Physics">Physics</md-option>
              <md-option value="Biology">Biology</md-option>
              <md-option value="Medicine">Medicine</md-option>
              <md-option value="Economics">Economics</md-option>
              <md-option value="Psychology">Psychology</md-option>
              <md-option value="Laws">Laws</md-option>
              <md-option value="Mathematics">Mathematics</md-option>
              <md-option value="Veterinary">Veterinary</md-option>
              <md-option value="History">History</md-option>
              <md-option value="Engineering">Engineering</md-option>
              <md-option value="Language">Language</md-option>
            </md-select>
          </md-field>
        </div>
      </div>
      <div class="right">
        <md-field>
          <label>Change your email address</label>
          <md-input v-model="emailAddress"></md-input>
        </md-field>
        <md-field>
          <label>Change your research groups</label>
          <md-input v-model="rGroup" md-autogrow></md-input>
        </md-field>
        <md-field>
          <label>Change your interest</label>
          <md-input v-model="interest" md-autogrow></md-input>
        </md-field>
      </div>
    </div>
</template>

<script>
    export default {
        name: "ProfilModif",
      data: () =>({
        university: 'Unamur',
        domain: 'ComputerScience',
        emailAddress: 'std@unamur.be',
        rGroup: 'Nadi, IRIDIA, BRUH',
        interest: 'Infovis, Bruh, IT',
        avatarimg: 'link to image',
      }),
      created() {
          //todo get the information from BD
      }
    }
</script>

<style scoped>
.left{
  float: left;
  width: 41%;
  margin: 2%;
}
  .right{
    float: left;
    width: 41%;
    margin: 2%;
  }
</style>
