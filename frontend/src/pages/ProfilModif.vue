<template>
  <div class="general">
    <div class="left">
      <div class="md-layout">
        <div class="leftIcon">
          <md-icon class="material-icons">
            insert_photo
          </md-icon>
        </div>
        <div class="zoneText">
          <md-field>
            <label>Change your avatar</label>
            <md-input v-model="avatarimg"></md-input>
          </md-field>
        </div>

        <div class="leftIcon">
          <md-icon class="material-icons">
            home
          </md-icon>
        </div>
        <div class="zoneText">
          <md-autocomplete v-model="university" :md-options="universities">
            <label>Change your university</label>
          </md-autocomplete>
        </div>

        <div class="leftIcon">
          <md-icon class="material-icons">
            keyboard
          </md-icon>
        </div>
        <div class="zoneText">
          <md-autocomplete v-model="domain" :md-options="domains" >
            <label>Change your domain</label>
          </md-autocomplete>
        </div>
      </div>
    </div>

    <div class="right">

      <div class="leftIcon">
        <md-icon class="material-icons">
          email
        </md-icon>
      </div>
      <div class="zoneText">
        <md-field>
          <label>Change your email address</label>
          <md-input v-model="emailAddress" ></md-input>
        </md-field>
      </div>

      <div class="leftIcon">
        <md-icon class="material-icons">
          group
        </md-icon>
      </div>
      <div class="zoneText">
        <md-field>
          <label>Change your research groups</label>
          <md-input v-model="rGroup" md-autogrow></md-input>
        </md-field>
      </div>

      <md-field>
        <label>Change your bio</label>
        <md-textarea v-model="bio"></md-textarea>
      </md-field>
    </div>

    <div class="centeredButton">
      <md-button class="md-raised md-primary" @click="wantToChange = true">Save changes</md-button>
    </div>

    <!--  ask if ok to change  -->

    <md-dialog :md-active.sync="wantToChange">
      <md-dialog-title>Are you sure to change your information</md-dialog-title>
      <md-content class="md-layout md-alignment-center-space-around">
        <md-button
          class="md-dense md-raised"
          @click="wantToChange = false"
        >No</md-button>

        <md-button
          class="md-dense md-raised md-primary"
          :md-ripple="false"
          @click="saveData()"
        >Yes</md-button>
      </md-content>
    </md-dialog>
  </div>
</template>

<script>
  import { postModificationProfile, getProfileBase, getProfileInfoPro, getProfileSocial} from '@/services/api-user'
  import { EventBus, EVENT_USER_LOGOUT, EVENT_BYE_REDIRECTION } from '@/services/event-bus.js';

  export default {
    name: "ProfilModif",
    data: () =>({
      wantToChange: false,
      university: null,
      domain: null,
      emailAddress: null,
      rGroup: 'Nadi, IRIDIA, BRUH',
      avatarimg: 'link to image',
      domains: ["ComputerScience", "Literature", "Chemistry", "Physics",
        "Biology", "Medicine", "Economics", "Economics", "Psychology",
        "Laws", "Mathematics", "Veterinary", "History", "Engineering",
        "Language"],
      universities: ["UNamur", "ULB", "Ulg", "UMons", "Kul", "Oxford", "MIT"],
      bio: "BLELBLE",
      username: null,
    }),

    methods: {
      fetchProfileBase() {
        getProfileBase(this.username).then(data => {
          this.emailAddress = data.email;
          this.domain = data.domain;
          this.university = data.university;
          this.avatarimg = data.avatar;
        });
      },

      fetchDataPro() {
        getProfileInfoPro(this.username).then(data => {
          this.rGroup = data.researchGroup;
        });
      },

      fetchSocial() {
        getProfileSocial(this.username).then(data => {
          this.bio = data.bio;
        });
      },

      postData(modifiedData){
        postModificationProfile(modifiedData).then(this.wantToChange=false)
      },

      saveData(){
        let dataToSend = {'profilePicURL':this.avatarimg, 'currentUniversity':this.university
          , 'domain':this.domain, 'researchGroups':[this.rGroup], 'email': this.emailAddress
          , 'description': this.bio};
        this.postData(dataToSend)
      }

    },

    computed: {

    },

    created() {
      //todo get the information from BD
      this.username = this.$route.params["username"];

      this.fetchProfileBase();
      this.fetchDataPro();
      this.fetchSocial();

      this.emailAddress = this.profil.email
      this.university = this.profil.university
      this.domain = this.profil.domain



      EventBus.$on(EVENT_USER_LOGOUT, _ => {
        this.$router.replace({ name : 'accueil' }, function onComplete() {
          EventBus.$emit(EVENT_BYE_REDIRECTION, true)
        })
      })

    },



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
  .leftIcon{
    float: left;
    padding-top: 4%;
    width: 10%;
    margin: 3%;
  }
  .zoneText{
    float: left;
    width: 83%;
  }

  .centeredButton{
    float: left;
    margin-left: 38%;
  }
</style>
