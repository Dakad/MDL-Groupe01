<template>
    <div class="general">
      <div class="left">
        <div class="md-layout">
          <md-field>
            <md-icon class="material-icons">
              insert_photo
            </md-icon>
            <label>Change your avatar</label>
            <md-input v-model="avatarimg"></md-input>
          </md-field>
          <md-autocomplete v-model="university" :md-options="universities">
            <md-icon class="material-icons">
              home
            </md-icon>
            <label>Change your university</label>
          </md-autocomplete>
          <md-autocomplete v-model="domain" :md-options="domains" >
            <md-icon class="material-icons">
              keyboard
            </md-icon>
            <label>Change your domain</label>
          </md-autocomplete>
          <md-field>
            <label>Change your bio</label>
            <md-textarea v-model="bio"></md-textarea>
          </md-field>
        </div>
      </div>
      <div class="right">
        <md-field>
          <label>Change your email address</label>
          <md-icon class="material-icons">
            email
          </md-icon>
          <md-input v-model="emailAddress" ></md-input>
        </md-field>
        <md-field>
          <label>Change your research groups</label>
          <md-icon class="material-icons">
            group
          </md-icon>
          <md-input v-model="rGroup" md-autogrow></md-input>
        </md-field>
        <md-field>
          <label>Change your interest</label>
          <md-icon class="material-icons">
            location_searching
          </md-icon>
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
        domains: ["ComputerScience", "Literature", "Chemistry", "Physics",
          "Biology", "Medicine", "Economics", "Economics", "Psychology",
          "Laws", "Mathematics", "Veterinary", "History", "Engineering",
          "Language"],
        universities: ["UNamur", "ULB", "Ulg", "UMons", "Kul", "Oxford", "MIT"],
        bio: "BLELBLE",
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
